package ca.gc.cra.rcsc.eventbrokerspoc.sinks;


import javax.enterprise.context.ApplicationScoped;

import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.InvalidPropertiesException;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;

@ApplicationScoped
public class SolacePubSub {

	public static final String SOLACE_HOST = "pubsubplus-openshift-pubsubplus-openshift.solace-system.svc.cluster.local";
	public static final String SOLACE_USERNAME = "test";
	public static final String SOLACE_PASSWORD = "";
	public static final String SOLACE_VPN_NAME = "";
	
	public static final String SOLACE_TOPIC_NAME = "test/topic";
	
	private JCSMPSession session;
	private XMLMessageConsumer consumer;
	
	
	public SolacePubSub() {
		connect();
		
		buildConsumer();
		
		connectToTopic();
	}
	
	public void connectToTopic() {
		if (session == null) {
			System.out.println("ERROR: No session to connectToTopic");
			return;
		}
		if (consumer == null) {
			System.out.println("ERROR: No consumer to connectToTopic");
			return;
		}
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(SOLACE_TOPIC_NAME);
		try {
			session.addSubscription(topic);
			consumer.start();
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void diconnectFromTopic() {
		if (session == null) {
			System.out.println("ERROR: No session to diconnectFromTopic");
			return;
		}
		if (consumer == null) {
			System.out.println("ERROR: No consumer to diconnectFromTopic");
			return;
		}
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(SOLACE_TOPIC_NAME);
		consumer.stop();
		
		try {
			session.removeSubscription(topic);
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void connect() {
		JCSMPProperties properties = new JCSMPProperties();
		
		if (SOLACE_HOST != null && !SOLACE_HOST.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.HOST,SOLACE_HOST);
		}

		if (SOLACE_USERNAME != null && !SOLACE_USERNAME.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.USERNAME, SOLACE_USERNAME);
		}
		
		if (SOLACE_PASSWORD != null && !SOLACE_PASSWORD.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.PASSWORD, SOLACE_PASSWORD);
		}
		
		if (SOLACE_VPN_NAME != null && !SOLACE_VPN_NAME.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.VPN_NAME, SOLACE_VPN_NAME);
		}
		
		try {
			session = JCSMPFactory.onlyInstance().createSession(properties);
			
			session.connect();
			
		} catch (InvalidPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			session = null;
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			session = null;
		}

	}
	
	private void buildConsumer() {
		if (session == null) {
			System.out.println("ERROR: No session to buildConsumer");
			return;
		}
		
		try {
			consumer = session.getMessageConsumer(new XMLMessageListener() {

			    @Override
			    public void onReceive(BytesXMLMessage msg) {
			        if (msg instanceof TextMessage) {
			            System.out.printf("TextMessage received: '%s'%n",
			                              ((TextMessage)msg).getText());
			        } else {
			            System.out.println("Message received.");
			        }
			        System.out.printf("Message Dump:%n%s%n",msg.dump());
			    }

			    @Override
			    public void onException(JCSMPException e) {
			        System.out.printf("Consumer received exception: %s%n",e);
			    }
			});
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			consumer = null;
		}
	}
	
}
