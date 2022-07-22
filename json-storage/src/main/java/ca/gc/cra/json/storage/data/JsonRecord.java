package ca.gc.cra.json.storage.data;

import org.json.JSONObject;

public class JsonRecord {

	private long id;
	private JSONObject json;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public JSONObject getJson() {
		return json;
	}
	
	public void setJson(JSONObject json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "{" + 
				"\"id\":" + id + "," +
				"\"json\":" + json.toString() + 
				"}";
	}
	
	
}
