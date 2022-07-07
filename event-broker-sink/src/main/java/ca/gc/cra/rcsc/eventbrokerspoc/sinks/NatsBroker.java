package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Nats;

public class NatsBroker {
    
    public static final String NATS_HOST = "nats://my-nats.nats-system.svc.cluster.local:4222";

	public static final String NATS_SUBJECT = "test/subject";

	private Connection connection;

    public NatsBroker() {

        connect();
    }

    public void connectToSubject() {
        if (null == connection) {
            System.out.println("NATS-ERROR: No connection to connectToSubject");
			return;
        }

        Dispatcher dispatcher = connection.createDispatcher(new MessageHandler() {

            @Override
            public void onMessage(Message msg) throws InterruptedException {
                String response = new String(msg.getData(), StandardCharsets.UTF_8);
                System.out.println("NATS-Message received: " + response);
                
            }
            
        });

        dispatcher.subscribe(NATS_SUBJECT);
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
