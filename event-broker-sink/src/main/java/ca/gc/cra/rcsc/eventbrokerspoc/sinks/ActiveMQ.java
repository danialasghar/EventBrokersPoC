package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import ca.gc.cra.rcsc.eventbrokerspoc.sender.Storage;


@ApplicationScoped
public class ActiveMQ {

    @Incoming("test-topic-in")
    public void process(String incomingMessage) throws InterruptedException {
        System.out.println("ActiveMQ-RECEIVED: " + incomingMessage);

        Storage.getInstance().sendData(incomingMessage);
    }

}