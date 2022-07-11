package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;


public class ActiveMQ {
    private int instanceId;
    
    @Channel("new-artemis") Emitter<String> messageEmitter;

    public ActiveMQ(int instanceId) {
        this.instanceId = instanceId;
    }

    public void sendMessage() {
        String message = Utils.buildMessage(instanceId);
        messageEmitter.send(message);

        System.out.println("ActiveMQ-MESSAGE SENT: " + message);
    }
}
