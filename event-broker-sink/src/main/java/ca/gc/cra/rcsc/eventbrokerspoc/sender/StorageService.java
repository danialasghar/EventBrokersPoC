package ca.gc.cra.rcsc.eventbrokerspoc.sender;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/json")
@RegisterRestClient
public interface StorageService {
    
    @PUT
    ResponseBuilder saveJsonRecord(Event event);
}
