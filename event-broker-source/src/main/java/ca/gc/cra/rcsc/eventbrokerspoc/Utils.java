package ca.gc.cra.rcsc.eventbrokerspoc;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.eclipse.microprofile.config.ConfigProvider;

public class Utils {

	public static final String MESSSAGE_FORMAT = "yyyy.MM.dd - HH.mm.ss.SSS";
	
	
	public static String buildMessage(int instanceID) {
		String timeStamp = new SimpleDateFormat(MESSSAGE_FORMAT).format(new java.util.Date());
        
		return "Instance Number: " + Integer.toString(instanceID) + " , Sent at: " + timeStamp;
	}
	
	public static String getStringProperty(String propertyName) {
		Optional<String> optional = ConfigProvider.getConfig().getOptionalValue(propertyName, String.class);

		return optional.isPresent() ? optional.get() : "";
	}

	public static int getIntProperty(String propertyName) {
		int result = 0;

		String stringProperty = getStringProperty(propertyName);

		try {
			result = Integer.parseInt(stringProperty);
		} catch (NumberFormatException e) {
			System.out.println("Failed to parse int property: " + propertyName);
		}

		return result;
	}
}
