package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

@ApplicationScoped
public class ActiveMQ {

    @Channel("test-topic-out")
    Emitter<String> emitter;

    private int instanceId;
    
    public ActiveMQ() {
        this(4);
    }

    public ActiveMQ(int instanceId) {
        this.instanceId = instanceId;
    }

    public void sendMessage() {
        String message = Utils.buildMessage(instanceId);

        emitter.send(message);

        System.out.println("ActiveMQ-MESSAGE SENT: " + message);
    }

}
