package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class ApacheKafka {


    @Incoming("kafka-requests")
    public void process(String message) throws InterruptedException {
        System.out.println("ApacheKafka received: " + message);
    }
}