package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.eclipse.microprofile.config.ConfigProvider;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;
import io.nats.client.Connection;
import io.nats.client.Nats;

public class NatsBroker {
	private int instanceId;

	private String natsHost;
	private String natsSubject;

	private Connection connection;

	public NatsBroker(int instanceId) {
		this.instanceId = instanceId;

		loadConfiguration();
		connect();
	}

	public void sendMessage() {
		if (connection == null) {
			System.out.println("NATS-ERROR: No connection to sendMessage");
			return;
		}

		String message = Utils.buildMessage(instanceId);

		connection.publish(natsSubject, message.getBytes(StandardCharsets.UTF_8));

		System.out.println("NATS-MESSAGE SENT: " + message);
	}

	private void connect() {
		try {
			connection = Nats.connect(natsHost);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			connection = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			connection = null;
		}

	}

	private void loadConfiguration() {
		natsHost = Utils.getStringProperty("nats.host");
		natsSubject = Utils.getStringProperty("nats.subject");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("NATS Config");
		System.out.println("natsHost=" + natsHost);
		System.out.println("natsSubject=" + natsSubject);
	}
}
