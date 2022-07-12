package ca.gc.cra.rcsc.eventbrokerspoc.sources;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;

@ApplicationScoped
public class ApacheKafka {

    @Channel("kafka-c")
    Emitter<String> messageEmitter;

    private int instanceId;
    
    public ApacheKafka() {
        this(5);
    }

    public ApacheKafka(int instanceId) {
        this.instanceId = instanceId;
    }

    public void sendMessage() {
        String message = Utils.buildMessage(instanceId);

        messageEmitter.send(message);

        System.out.println("ApacheKafka-MESSAGE SENT: " + message);
    }

    
}