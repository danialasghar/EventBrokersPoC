package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

public class ActiveMqDirect {
    private int instanceId;

    private static final String HOST_PREFIX = "vm://";

    private String activeMqHost = "";
    private String activeMqTopicName = "";

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public ActiveMqDirect(int instanceId) {
        this.instanceId = instanceId;

        loadConfiguration();
        connect();
    }
    
    public void sendMessage() {
        if (null == producer) {
			System.out.println("ActiveMQ-ERROR: No producer to sendMessage");
			return;
		}
        try {
            // Create a messages
            String messageText = Utils.buildMessage(instanceId);
            TextMessage message = session.createTextMessage(messageText);
            
            // Tell the producer to send the message
            producer.send(message);

            System.out.println("ActiveMQ-MESSAGE SENT: " + messageText);
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void connect() {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(HOST_PREFIX + activeMqHost);

        try {
            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(activeMqTopicName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            producer = null;
        }

    }

    private void loadConfiguration() {
		activeMqHost = Utils.getStringProperty("activemq.host");
		activeMqTopicName = Utils.getStringProperty("activemq.topic");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("ActiveMQ Config");
		System.out.println("activeMqHost=" + activeMqHost);
		System.out.println("activeMqTopicName=" + activeMqTopicName);
	}
}
