package ca.gc.cra.rcsc.eventbrokerspoc.sender;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

public class Storage {

    private static Storage instance;

    @Inject
    @RestClient
    private StorageService storageService;
    
    
    private Storage() {
    }

    public static Storage getInstance() {
        if (null == instance) {
            instance = new Storage();
        }

        return instance;
    }


    public void sendData(String data) {
        try {
            System.out.println("EventSender sending=" + data);
            
            //FIXE: This did not work! :(
            storageService.saveJsonRecord(buildEvent(data));
        
            //System.out.println("EventSender statusCode=" + statusCode);
        } catch (Exception e) {
            System.out.println("EventSender exception" + e);
        }
    }

    private Event buildEvent(String data) {
        Event event = new Event();
        event.event = data;

        return event;
    }
    
}
