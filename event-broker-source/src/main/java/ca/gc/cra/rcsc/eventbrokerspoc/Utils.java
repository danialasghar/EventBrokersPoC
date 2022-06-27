package ca.gc.cra.rcsc.eventbrokerspoc;
import java.text.SimpleDateFormat;

public class Utils {

	public static final String MESSSAGE_FORMAT = "yyyy.MM.dd - HH.mm.ss.SSS";
	
	
	public static String buildMessage(int instanceID) {
		String timeStamp = new SimpleDateFormat(MESSSAGE_FORMAT).format(new java.util.Date());
        
		return "Instance Number: " + Integer.toString(instanceID) + " , Sent at: " + timeStamp;
	}
}
