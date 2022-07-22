package ca.gc.cra.rcsc.eventbrokerspoc.sender;

import ca.gc.cra.rcsc.eventbrokerspoc.Utils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EventSender {

    private static EventSender instance;
    private String storageUrl;

    private EventSender() {
        loadConfiguration();
    }

    public static EventSender getInstance() {
        if (null == instance) {
            instance = new EventSender();
        }

        return instance;
    }


    public void sendData(String data) {
        try {
            System.out.println("EventSender sending=" + data);

            RestAssured.baseURI = storageUrl;
            RequestSpecification request = RestAssured.given(); 

            // Add a header stating the Request body is a JSON
            request.header("Content-Type", "application/json");

            // Add the Json to the body of the request
            request.body(convertToJson(data));

            Response response = request.post("/json");
            int statusCode = response.getStatusCode();
        
            System.out.println("EventSender statusCode=" + statusCode);
        } catch (Exception e) {
            System.out.println("EventSender exception" + e);
        }
    }

    private String convertToJson(String data) {
        return "{ event: " + data + " }";
    }

    private void loadConfiguration() {
		storageUrl = Utils.getStringProperty("event.storage.service.url");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("EventSender Config");
		System.out.println("storageUrl=" + storageUrl);
	}
    
}
