package ca.gc.cra.rcsc.eventbrokerspoc.sinks;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class QuotesProcessor {


    @Incoming("requests")
    public String process(String message) throws InterruptedException {
        System.out.println("Apache Kafka message received: " + incomingMessage);
    }
}