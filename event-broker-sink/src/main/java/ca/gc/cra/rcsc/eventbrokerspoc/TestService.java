package ca.gc.cra.rcsc.eventbrokerspoc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ca.gc.cra.rcsc.eventbrokerspoc.sinks.SolacePubSub;

@Path("/test")
public class TestService {

	private SolacePubSub solace;
	
	@GET
    @Path("/solace")
    public void connectToSolace() {
    	solace = new SolacePubSub();

    	solace.connectToTopic();
    }

}