package ca.gc.cra.rcsc.eventbrokerspoc;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sources.ActiveMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.NatsBroker;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.SolacePubSub;


@Path("/test")
public class TestService {

    private SolacePubSub solace;
    private NatsBroker nats;
    
    @Inject
    private ActiveMQ activeMQ;

	@GET
    @Path("/rabbitmq")
    public void testRabbitMq() {
    	RabbitMQ rabbitMQ = new RabbitMQ(1);

    	rabbitMQ.sendMessage();
    }

	@GET
    @Path("/solace")
    public void testSolace() {
        if (null == solace) {
            solace = new SolacePubSub(2);
        }

    	solace.sendMessage();
    }

    @GET
    @Path("/nats")
    public void testNats() {
        if (null == nats) {
            nats = new NatsBroker(3);
        }

    	nats.sendMessage();
    }

    @GET
    @Path("/ibm")
    public void testIbmMQPub(){
        IbmMQ ibmmq = new IbmMQ();
        ibmmq.connectSend();
    }


    @GET
    @Path("/artemis")
    public void createMessage() {
        activeMQ.sendMessage();
    }
}