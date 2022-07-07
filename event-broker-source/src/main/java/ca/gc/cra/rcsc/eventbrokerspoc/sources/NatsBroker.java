package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;
import io.nats.client.Connection;
import io.nats.client.Nats;

public class NatsBroker {

	public static final String NATS_HOST = "nats://my-nats.nats-system.svc.cluster.local:4222";

	public static final String NATS_SUBJECT = "test/subject";

	private int instanceId;

	private Connection connection;

	public NatsBroker(int instanceId) {
		this.instanceId = instanceId;

		connect();
	}

	public void sendMessage() {
		if (connection == null) {
			System.out.println("NATS-ERROR: No connection to sendMessage");
			return;
		}

		String message = Utils.buildMessage(instanceId);

		connection.publish(NATS_SUBJECT, message.getBytes(StandardCharsets.UTF_8));

		System.out.println("NATS-MESSAGE SENT: " + message);
	}

	private void connect() {
		try {
			connection = Nats.connect(NATS_HOST);

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
}
