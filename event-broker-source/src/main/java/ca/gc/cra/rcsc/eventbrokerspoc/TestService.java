package ca.gc.cra.rcsc.eventbrokerspoc;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sources.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.SolacePubSub;


// ActiveMQ Artemis imports

import java.util.UUID;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.mutiny.Multi;


@Path("/test")
public class TestService {


    @Channel("new-artemis") Emitter<String> messageEmitter; // <1>


	@GET
    @Path("/rabbitmq")
    public void testRabbitMq() {
    	RabbitMQ rabbitMQ = new RabbitMQ(1);

    	rabbitMQ.sendMessage();
    }

	@GET
    @Path("/solace")
    public void testSolace() {
    	SolacePubSub solace = new SolacePubSub(2);

    	solace.sendMessage();
    }

    @GET
    @Path("/ibm")
    public void testIbmMQPub(){
        IbmMQ ibmMQ = new IbmMQ();
    }


    @POST
    @Path("/artemis/generate")
    @Produces(MediaType.TEXT_PLAIN)
    public String createMessage(String newMessage) {
        UUID uuid = UUID.randomUUID();
        messageEmitter.send(newMessage + uuid.toString()); // <2>
        return uuid.toString();
    }
}