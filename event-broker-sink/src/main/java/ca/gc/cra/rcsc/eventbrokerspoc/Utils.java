package ca.gc.cra.rcsc.eventbrokerspoc;

import java.util.Optional;

import org.eclipse.microprofile.config.ConfigProvider;

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
