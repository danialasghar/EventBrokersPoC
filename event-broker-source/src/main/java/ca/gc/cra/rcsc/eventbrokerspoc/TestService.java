package ca.gc.cra.rcsc.eventbrokerspoc;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sources.ActiveMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.NatsBroker;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.SolacePubSub;
import ca.gc.cra.rcsc.eventbrokerspoc.sources.ApacheKafka;



@Path("/test")
public class TestService {

    @Inject
    private ActiveMQ activeMq;
    @Inject
    private ApacheKafka kakfa;

    private RabbitMQ rabbitMq;
    private SolacePubSub solace;
    private NatsBroker nats;
    private IbmMQ ibmMq;


	@GET
    @Path("/rabbitmq")
    public void testRabbitMq() {
        if (null == rabbitMq) {
            rabbitMq = new RabbitMQ(1);
        }

    	rabbitMq.sendMessage();
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
    @Path("/activemq")
    public void testActiveMq() {
        // Injected instanceId = 4
        activeMq.sendMessage();
    }

    @GET
    @Path("/kafka")
    public void testKafka() {
        // Injected instanceId = 5
        kakfa.sendMessage();
    }

    @GET
    @Path("/ibm")
    public void testIbmMQPub(){
        if (null == ibmMq) {
            ibmMq = new IbmMQ(6);
        }
        ibmMq.connectSend();
    }

}