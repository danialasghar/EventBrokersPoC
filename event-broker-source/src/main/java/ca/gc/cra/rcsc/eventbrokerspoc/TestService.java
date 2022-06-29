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


    @Channel("new-artemis-message") Emitter<String> messageEmitter; // <1>


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
    @Path("/IBM/put")
    public void testIbmMQPub(){
        IbmMQ ibmMQ = new IbmMQ("PRODUCER_PUB");
        ibmMQ.send("Testing IBM Publish", 1);
    }

    @GET
    @Path("/IBM/put")
    public void testIbmMQPut(){
        IbmMQ ibmMQ = new IbmMQ("PRODUCER_PUT");
        ibmMQ.send("Testing IBM Put", 1);
    }

    @POST
    @Path("/artemis/generate")
    @Produces(MediaType.TEXT_PLAIN)
    public String createMessage() {
        UUID uuid = UUID.randomUUID();
        messageEmitter.send(uuid.toString()); // <2>
        return uuid.toString();
    }
}