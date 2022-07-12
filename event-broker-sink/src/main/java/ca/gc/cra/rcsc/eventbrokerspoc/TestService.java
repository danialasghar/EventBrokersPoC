package ca.gc.cra.rcsc.eventbrokerspoc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.gc.cra.rcsc.eventbrokerspoc.sinks.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.NatsBroker;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.SolacePubSub;

@Path("/connect")
public class TestService {

	private SolacePubSub solace;
	private NatsBroker nats;
	
	@GET
    @Path("/solace")
    public void connectToSolace() {
		if (solace == null) {
			solace = new SolacePubSub();
			solace.connectToTopic();
		}
    }

	@GET
    @Path("/nats")
    public void connectToNats() {
		if (nats == null) {
			nats = new NatsBroker();
			nats.connectToSubject();
		}
    }
	
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStatus() {
		String result = "Status:\n";
		
		result += "Solace: " + Utils.isConnectedNullCheck(solace) + "\n";
		result += "NATS: " + Utils.isConnectedNullCheck(nats) + "\n";
		
		return result;
	}

	@GET
	@Path("/rabbit")
	public void connectToRabbitMQ(){
		RabbitMQ rabbitMQ = new RabbitMQ();
		rabbitMQ.receiveMessage();
	}

	@GET
	@Path("/ibm")
	public void connectToIbmMQ(){
		IbmMQ ibm =  new IbmMQ();
		ibm.getMessage();
	}

}