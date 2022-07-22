package ca.gc.cra.json.storage.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.json.JSONException;
import org.json.JSONObject;

import ca.gc.cra.json.storage.data.JsonDAO;
import ca.gc.cra.json.storage.data.JsonRecord;

@ApplicationScoped
public class JsonService {

	private JsonDAO dao = new JsonDAO();
	
    public String getJsonRecords() {
    	List<JsonRecord> jsonRecords = dao.getAllJsonRecords();
    	
    	System.out.println("Retrieved: " + jsonRecords);
    	
        return jsonRecords.toString();
    }
    
    public String saveJsonRecord(String data) {
    	String resultText = "Failed to save";
    	try {
    		JSONObject json = new JSONObject(data);
    		
    		boolean saved = dao.saveJsonRecord(json);
        	resultText = saved ? "Saved" : "Failed to save";
            
        	System.out.println(resultText + ": " + json);
        	
    	} catch (JSONException e) {
    		System.out.println("Failed to convert to JSON: " + data);
    		e.printStackTrace();
    	} catch (Exception e) {
    		System.out.println("Unknown exception: " + e);
    		e.printStackTrace();
    	}
    	
        return resultText;
    }

}
