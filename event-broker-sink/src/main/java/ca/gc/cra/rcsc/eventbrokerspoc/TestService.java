package ca.gc.cra.rcsc.eventbrokerspoc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.gc.cra.rcsc.eventbrokerspoc.sinks.IbmMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.RabbitMQ;
import ca.gc.cra.rcsc.eventbrokerspoc.sinks.SolacePubSub;

@Path("/connect")
public class TestService {

	@Inject
	SolacePubSub solace;
	
	/*
	@GET
    @Path("/solace")
    public void connectToSolace() {
		if (solace != null) {
			solace.diconnectFromTopic();
		}
		
		//Create new instance and connect it
		solace = new SolacePubSub();
    	solace.connectToTopic();
    }
	*/
	
	@GET
	@Path("/status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStatus() {
		String result = "Status:\n";
		
		//Solace
		result += "Solace: " + Utils.isConnectedNullCheck(solace) + "\n";
		
		return result;
	}

	@GET
	@Path("/rabbit")
	public void connectToRabbitMQ(){
		RabbitMQ rabbitMQ = new RabbitMQ(1);
		rabbitMQ.receiveMessage();
	}

	@GET
	@Path("/ibm")
	public void connectToIbmMQ(){
		IbmMQ ibmMQ = new IbmMQ();
	}

}