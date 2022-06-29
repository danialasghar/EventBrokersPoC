package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitMQ {

    private final static String QUEUE_NAME = "POC";
    ConnectionFactory factory;
    Connection connection;
    private int instanceID;

    public RabbitMQ(int instanceID){
        this.instanceID = instanceID;
        this.factory = new ConnectionFactory();
        this.factory.setHost("rabbitmq-demo-clusterip.rabbitmq-system.svc");
        this.factory.setPort(5672);
        this.factory.setUsername("default_user_VSFzxVHbwelOyeyHmSZ");
        this.factory.setPassword("u0845E5Wl-AepkvZP1vObVLFnEvopyK6");
        try {
            this.connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage(){
        try{
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

            TimeUnit.MINUTES.sleep(10);
            channel.abort();
            connection.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
