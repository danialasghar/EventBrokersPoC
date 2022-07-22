package ca.gc.cra.json.storage.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.gc.cra.json.storage.service.JsonService;

@Path("/json")
public class JsonResource {

    @Inject
    JsonService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonRecords() {
    	System.out.println("*** getJsonRecords() ***");
        return service.getJsonRecords();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String saveJsonRecord(String data) {
    	System.out.println("*** saveJsonRecord(" + data + ") ***");
        return service.saveJsonRecord(data);
    }
}