package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

@ApplicationScoped
public class ActiveMQ {

    @Channel("new-artemis")
    Emitter<String> messageEmitter;

    private int instanceId;
    
    public ActiveMQ() {
        this(4);
    }

    public ActiveMQ(int instanceId) {
        this.instanceId = instanceId;
    }

    public void sendMessage() {
        String message = Utils.buildMessage(instanceId);

        messageEmitter.send(message);

        System.out.println("ActiveMQ-MESSAGE SENT: " + message);
    }

}
