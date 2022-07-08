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
	private static String SOLACE_HOST;

	@ConfigProperty(name = "solace.username")
	public static String SOLACE_USERNAME;

	@ConfigProperty(name = "solace.password")
	public static String SOLACE_PASSWORD;

	@ConfigProperty(name = "solace.vpn")
	public static String SOLACE_VPN_NAME;
	
	@ConfigProperty(name = "solace.topic.name")
	public static String SOLACE_TOPIC_NAME;
	
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
		
		Topic topic = JCSMPFactory.onlyInstance().createTopic(SOLACE_TOPIC_NAME);
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
	
}
