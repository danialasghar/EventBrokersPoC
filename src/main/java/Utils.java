import java.text.SimpleDateFormat;

public class Utils {

	public static String buildMessage(int instanceID) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        
		return "Instance Number: " + Integer.toString(instanceID) + " , Sent at: " + timeStamp;
	}
}
