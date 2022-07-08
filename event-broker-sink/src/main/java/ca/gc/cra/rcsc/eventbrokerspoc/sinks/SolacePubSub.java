package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

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

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

public class SolacePubSub {

	private String solaceHost = "";
	private String solaceUserName = "";
	private String solacePassword = "";
	private String solaceVpnName = "";
	private String solaceTopicName = "";
	
	private JCSMPSession session;
	private XMLMessageConsumer consumer;
	
	
	public SolacePubSub() {
		loadConfiguration();
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
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(solaceTopicName);
		try {
			session.addSubscription(topic);
			consumer.start();
		} catch (JCSMPException e) {
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
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(solaceTopicName);
		consumer.stop();
		
		try {
			session.removeSubscription(topic);
		} catch (JCSMPException e) {
			e.printStackTrace();
		}
		
	}
	
	private void connect() {
		JCSMPProperties properties = new JCSMPProperties();
		
		if (solaceHost != null && !solaceHost.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.HOST,solaceHost);
		}

		if (solaceUserName != null && !solaceUserName.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.USERNAME, solaceUserName);
		}
		
		if (solacePassword != null && !solacePassword.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.PASSWORD, solacePassword);
		}
		
		if (solaceVpnName != null && !solaceVpnName.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.VPN_NAME, solaceVpnName);
		}
		
		try {
			session = JCSMPFactory.onlyInstance().createSession(properties);
			
			session.connect();
			
		} catch (InvalidPropertiesException e) {
			e.printStackTrace();
			
			session = null;
		} catch (JCSMPException e) {
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
			            System.out.printf("SOLACE-TextMessage received: '%s'%n",
			                              ((TextMessage)msg).getText());
			        } else {
			            System.out.println("SOLACE-Message received.");
			        }
			        //System.out.printf("SOLACE-Message Dump:%n%s%n",msg.dump());
			    }

			    @Override
			    public void onException(JCSMPException e) {
			        System.out.printf("Consumer received exception: %s%n",e);
			    }
			});
		} catch (JCSMPException e) {
			e.printStackTrace();
			
			consumer = null;
		}
	}

	private void loadConfiguration() {
		solaceHost = Utils.getStringProperty("solace.host");
		solaceUserName = Utils.getStringProperty("solace.username");
		solacePassword = Utils.getStringProperty("solace.password");
		solaceVpnName = Utils.getStringProperty("solace.vpn");
		solaceTopicName = Utils.getStringProperty("solace.topic.name");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("Solace Config");
		System.out.println("solaceHost=" + solaceHost);
		System.out.println("solaceUserName=" + solaceUserName);
		System.out.println("solacePassword=" + solacePassword);
		System.out.println("solaceVpnName=" + solaceVpnName);
		System.out.println("solaceTopicName=" + solaceTopicName);
	}
	
}
