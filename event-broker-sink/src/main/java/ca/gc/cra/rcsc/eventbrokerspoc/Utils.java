package ca.gc.cra.rcsc.eventbrokerspoc;

public class Utils {
	
	public static final String CONNECTED_STRING = "connected";
	public static final String DISCONNECTED_STRING = "disconnected";

	public static String isConnectedNullCheck(Object obj) {
		String result = "";
		
		if (obj != null) {
			result += CONNECTED_STRING;
		} else {
			result += DISCONNECTED_STRING;
		}
		
		return result;
	}
}
