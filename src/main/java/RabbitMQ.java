import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;

public class RabbitMQ {

    private final static String QUEUE_NAME = "hello";
    ConnectionFactory factory;
    Connection connection;
    private int instanceID;

    public RabbitMQ(int instanceID){
        this.instanceID = instanceID;
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        try {
            this.connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void SendMessage(){
        try {
            Channel channel = this.connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            String message = "Instance Number: " + Integer.toString(this.instanceID) + " , Sent at: " + timeStamp;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
