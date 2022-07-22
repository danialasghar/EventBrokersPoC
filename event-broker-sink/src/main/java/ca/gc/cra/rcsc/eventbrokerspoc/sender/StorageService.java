package ca.gc.cra.rcsc.eventbrokerspoc.sender;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json")
@RegisterRestClient
public interface StorageService {
    
    @PUT
    String saveJsonRecord(Event event);
}
