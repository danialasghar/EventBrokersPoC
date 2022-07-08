package ca.gc.cra.rcsc.eventbrokerspoc.sources;
import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

import org.eclipse.microprofile.config.inject.ConfigProperty;

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

	@ConfigProperty(name = "solace.host")
	String solaceHost;

	@ConfigProperty(name = "solace.username")
	String solaceUserName;

	@ConfigProperty(name = "solace.password")
	String solacePassword;

	@ConfigProperty(name = "solace.vpn")
	String solaceVpnName;
	
	@ConfigProperty(name = "solace.topic.name")
	String solaceTopicName;
	
	private int instanceID;
	
	private JCSMPSession session;
	private XMLMessageProducer producer;
	
	
	public SolacePubSub(int instanceID) {
		this.instanceID = instanceID;
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void connect() {

		printConfiguration();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			session = null;
		} catch (JCSMPException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();

			producer = null;
		}

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
