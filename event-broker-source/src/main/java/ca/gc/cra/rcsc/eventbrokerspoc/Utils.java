package ca.gc.cra.rcsc.eventbrokerspoc;
import java.text.SimpleDateFormat;

public class Utils {

	public static String buildMessage(int instanceID) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new java.util.Date());
        
		return "Instance Number: " + Integer.toString(instanceID) + " , Sent at: " + timeStamp;
	}
}
