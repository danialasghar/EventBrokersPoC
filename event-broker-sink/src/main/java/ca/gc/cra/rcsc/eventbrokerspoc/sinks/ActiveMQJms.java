package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

public class ActiveMQJms {
    private String activeMqHost = "";
    private String activeMqTopicName = "";

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private MessageConsumer consumer2;

    public ActiveMQJms() {

        loadConfiguration();
        connect();
    }

    public void connectToTopic() {
        if (null == session) {
			System.out.println("ActiveMQ-ERROR: No session to connectToTopic");
			return;
		}
        try {
            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(activeMqTopicName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);

            // Set message listener
            consumer.setMessageListener(new MessageListener() {

                @Override
                public void onMessage(Message message) {
                    System.out.println("ActiveMQ-Received 1: " + message);

                    try {

                        if (message instanceof TextMessage) {
                            TextMessage textMessage = (TextMessage) message;
                            String text = textMessage.getText();
                            System.out.println("ActiveMQ-Received: " + text);

                            session.commit();
                        } else {
                            System.out.println("ActiveMQ-Received: " + message);
                        }
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
               
            });

            connection.start();

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public void receiveMessage() {
        try {
            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(activeMqTopicName);

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer2 = session.createConsumer(destination);

            // Wait for a message
            Message message = consumer2.receive(0);

            System.out.println("ActiveMQ-Received 2: " + message);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void connect() {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMqHost);

        try {
            // Create a Connection
            connection = connectionFactory.createConnection();
            //connection.start();
            
            connection.setExceptionListener(new ExceptionListener() {
                public synchronized void onException(JMSException ex) {
                    System.out.println("ActiveMQ-ERROR: JMS Exception" + ex);
                }
            });

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            session = null;
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
