package ca.gc.cra.json.storage.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import ca.gc.cra.json.storage.Utils;

public class JsonDAO {
	
	private static final String SQL_SELECT_ALL = "SELECT * FROM records";
	private static final String SQL_INSERT_DATA = "INSERT INTO records (data) VALUES(\'";
	private static final String SQL_INSERT_DATA_END = "\')";
	
	private String dbType;
	private String dbHost;
	private int dbPort;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	
	private Connection connection;
	
	private Connection getConnection() {
		if (connection == null) {

			loadConfiguration();

			String dbUrl = dbType + "://" + dbHost + ":" + dbPort + "/" + dbName;

			System.out.println("Connecting: " + dbUrl + " using: " + dbUser);

			try {
				connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			} catch (SQLException e) {
				System.out.println("Failed to connect to DB");
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	private void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to close connection to DB");
				e.printStackTrace();
			}
			
			connection = null;
		}
	}
	
	public List<JsonRecord> getAllJsonRecords() {
		List<JsonRecord> result = new ArrayList<>();

		try {
			Statement statement = getConnection().createStatement();
			ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);

			while (rs.next()) {
				JsonRecord record = new JsonRecord();
				record.setId(rs.getLong(1));
				record.setJson(new JSONObject(rs.getString(2)));

				result.add(record);
			}

			closeConnection();
		} catch (SQLException e) {
			System.out.println("Failed to execute SELECT");
			e.printStackTrace();
			closeConnection();
		}

		return result;
	}

	public boolean saveJsonRecord(JSONObject data) {
		boolean result = false;
		
		try {
			Statement statement = getConnection().createStatement();
			int rows = statement.executeUpdate(
					SQL_INSERT_DATA + 
					data.toString() +
					SQL_INSERT_DATA_END);

			result = (rows == 1);
			
			getConnection();
		} catch (SQLException e) {
			System.out.println("Failed to execute INSERT");
			e.printStackTrace();
			getConnection();
		}
	
		return result;
	}
	
	private void loadConfiguration() {
		dbType = Utils.getStringProperty("json.database.type");
        dbHost = Utils.getStringProperty("json.database.host");
		dbPort = Utils.getIntProperty("json.database.port");
		dbName = Utils.getStringProperty("json.database.name");
		dbUser = Utils.getStringProperty("json.database.user");
		dbPassword = Utils.getStringProperty("json.database.password");

		printConfiguration();
	}

	private void printConfiguration() {
		System.out.println("DB Config");
		System.out.println("dbType=" + dbType);
		System.out.println("dbHost=" + dbHost);
		System.out.println("dbPort=" + dbPort);
		System.out.println("dbName=" + dbName);
		System.out.println("dbUser=" + dbUser);
		System.out.println("dbPassword=" + dbPassword);
	}
}
