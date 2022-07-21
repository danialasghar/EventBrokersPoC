package ca.gc.cra.rcsc.eventbrokerspoc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.gc.cra.rcsc.eventbrokerspoc.sinks.ActiveMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.ApacheKafka;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.NatsBroker;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.SolacePubSub;

@Path("/connect")
public class TestService {

	@Inject
	private ActiveMQ activeMq;
	@Inject
	private ApacheKafka kafka;

	private SolacePubSub solace;
	private NatsBroker nats;
	private RabbitMQ rabbitMq;
	private IbmMQ ibmMq;
	
	
	@GET
    @Path("/solace")
    public void connectToSolace() {
		if (null == solace) {
			solace = new SolacePubSub();
			solace.connectToTopic();
		}
    }

	@GET
    @Path("/nats")
    public void connectToNats() {
		if (null == nats) {
			nats = new NatsBroker();
			nats.connectToSubject();
		}
    }

	@GET
	@Path("/rabbit")
	public void connectToRabbitMQ(){
		//FIXME: Should we be listening for incomming messages all the time?
		//if (null == rabbitMq) {
			rabbitMq = new RabbitMQ();
			rabbitMq.receiveMessage();
		//}
	}

	@GET
	@Path("/ibm")
	public void connectToIbmMQ(){
		//FIXME: Should we be listening for incomming messages all the time?
		//if (null == ibmMq) {
			ibmMq =  new IbmMQ();
			ibmMq.getMessage();
		//}
	}

	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStatus() {
		String result = "Status:\n";
		
		result += "ActiveMQ: " + Utils.isConnectedNullCheck(activeMq) + "\n";
		result += "ApacheKafka: " + Utils.isConnectedNullCheck(kafka) + "\n";
		result += "Solace: " + Utils.isConnectedNullCheck(solace) + "\n";
		result += "NATS: " + Utils.isConnectedNullCheck(nats) + "\n";
		result += "RabbitMQ: " + Utils.isConnectedNullCheck(rabbitMq) + "\n";
		result += "ActiveMQ: " + Utils.isConnectedNullCheck(activeMq) + "\n";
		
		return result;
	}

}