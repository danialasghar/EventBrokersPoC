package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Nats;

public class NatsBroker {
    
    private String natsHost;
	private String natsSubject;

	private Connection connection;

    public NatsBroker() {
        loadConfiguration();
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

        dispatcher.subscribe(natsSubject);
    }

    private void connect() {
		try {
			connection = Nats.connect(natsHost);

		} catch (IOException e) {
			e.printStackTrace();

			connection = null;
		} catch (InterruptedException e) {
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
