package ca.gc.cra.rcsc.eventbrokerspoc;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sources.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.SolacePubSub;

@Path("/test")
public class TestService {

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
}