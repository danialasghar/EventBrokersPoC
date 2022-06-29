import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Blocking;


@ApplicationScoped
public class AmqConsumer {

    @Incoming("requests")       // <1>
    @Blocking                   // <2>
    public void process(String incomingMessage) throws InterruptedException {
        System.out.println("Incoming message is " + incomingMessage);
    }
}