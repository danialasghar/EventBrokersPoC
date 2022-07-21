package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

import com.solacesystems.jcsmp.InvalidPropertiesException;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishCorrelatingEventHandler;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageProducer;


public class SolacePubSub {
	private int instanceID;

	private String solaceHost;
	private String solaceUserName;
	private String solacePassword;
	private String solaceVpnName;
	private String solaceTopicName;
	
	private JCSMPSession session;
	private XMLMessageProducer producer;
	
	
	public SolacePubSub(int instanceID) {
		this.instanceID = instanceID;

		loadConfiguration();
		connect();
		buildProducer();
	}
	
	public void sendMessage() {
		if (producer == null) {
			System.out.println("SOLACE-ERROR: No producer to sendMessage");
			return;
		}
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(solaceTopicName);
		TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);

		String messageText = Utils.buildMessage(instanceID);
		
		msg.setText(messageText);
				
		try {
			producer.send(msg, topic);

			System.out.println("SOLACE-MESSAGE SENT: " + messageText);
		} catch (JCSMPException e) {
			e.printStackTrace();
		}
		
	}
	
	private void connect() {
		JCSMPProperties properties = new JCSMPProperties();
		
		if (solaceHost != null && !solaceHost.trim().isEmpty()) {
			properties.setProperty(JCSMPProperties.HOST, solaceHost);
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
	
	private void buildProducer() {
		if (session == null) {
			System.out.println("SOLACE-ERROR: No session to buildProducer");
			return;
		}
		
		try {
			producer = session.getMessageProducer(new JCSMPStreamingPublishCorrelatingEventHandler() {

				@Override
				public void responseReceivedEx(Object key) {
					System.out.println("SOLACE: Producer received response for msg: " + key);
				}

				@Override
				public void handleErrorEx(Object key, JCSMPException e, long timestamp) {
					System.out.printf("SOLACE: Producer received error for msg: %s@%s - %s%n", key, timestamp, e);
				}
			});
		} catch (JCSMPException e) {
			e.printStackTrace();

			producer = null;
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
