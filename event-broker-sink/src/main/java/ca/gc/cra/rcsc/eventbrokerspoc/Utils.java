package ca.gc.cra.rcsc.eventbrokerspoc;

public class Utils {

	public static String isConnectedNullCheck(Object obj) {
		String result = "";
		
		if (obj != null) {
			result += "connected";
		} else {
			result += "disconnected";
		}
		
		return result;
	}
}
