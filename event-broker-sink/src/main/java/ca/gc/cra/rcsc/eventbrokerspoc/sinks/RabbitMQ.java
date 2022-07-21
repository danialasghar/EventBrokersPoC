package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQ {

    private ConnectionFactory factory;
    private Connection connection;
    
    private String rabbitMqHost;
    private int rabbitMqPort;
    private String rabbitMqQueue;
    private String rabbitMqUsername;
    private String rabbitMqPassword;

    public RabbitMQ(){
        loadConfiguration();
        connect();
    }

    public void receiveMessage(){
        //FIXME: Should we be listening for incomming messages all the time?
        try{
            Channel channel = connection.createChannel();
            channel.queueDeclare(rabbitMqQueue, false, false, false, null);

            System.out.println("RabbitMQ: Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("RabbitMQ: Received: " + message);
            };

            channel.basicConsume(rabbitMqQueue, true, deliverCallback, consumerTag -> { });

            TimeUnit.MINUTES.sleep(10);
            
            channel.abort();
            connection.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect() {
        factory = new ConnectionFactory();
        factory.setHost(rabbitMqHost);
        factory.setPort(rabbitMqPort);
        factory.setUsername(rabbitMqUsername);
        factory.setPassword(rabbitMqPassword);

        try {
            connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadConfiguration() {
		rabbitMqHost = Utils.getStringProperty("rabbitmq.host");
		rabbitMqPort = Utils.getIntProperty("rabbitmq.port");
        rabbitMqQueue = Utils.getStringProperty("rabbitmq.queue");
		rabbitMqUsername = Utils.getStringProperty("rabbitmq.username");
		rabbitMqPassword = Utils.getStringProperty("rabbitmq.password");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("RabbitMQ Config");
		System.out.println("rabbitMqHost=" + rabbitMqHost);
		System.out.println("rabbitMqPort=" + rabbitMqPort);
        System.out.println("rabbitMqQueue=" + rabbitMqQueue);
		System.out.println("rabbitMqUsername=" + rabbitMqUsername);
		System.out.println("rabbitMqPassword=" + rabbitMqPassword);
	}
}
