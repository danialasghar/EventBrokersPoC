package ca.gc.cra.rcsc.eventbrokerspoc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sinks.SolacePubSub;

@Path("/connect")
public class TestService {

	private SolacePubSub solace = null;
	
	public TestService() {
		//Connect @ startup
		connectToSolace();
	}
	
	@GET
    @Path("/solace")
    public void connectToSolace() {
		//Create new instance and connect it
		solace = new SolacePubSub();
    	solace.connectToTopic();
    }

}