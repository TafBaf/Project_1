package lib;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class LibPostgreSQL {

	private Properties GetProperties() {
		Properties properties = new Properties();
		properties.setProperty("url", "jdbc:postgresql://localhost:5432/library");
		properties.setProperty("user", "postgres");
		properties.setProperty("password", "passw");
		
		return properties;
	}

	
	private Connection GetConnection(){
		Properties properties = GetProperties();
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;		
	}
	
	
	public static ResultSet GetQueryResultSet(String query, String[] prepParams) {
		Connection connection = GetConnection();
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;

		try {
			prepStatement = connection.prepareStatement(query);
			if (prepParams != null) {
				for (int i = 0; i < prepParams.length; i++) {
					prepStatement.setString(i + 1, prepParams[i]);
 				}
			}
			resultSet = prepStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Disconnect(null, prepStatement, connection);
		
		return resultSet;
	}

	
	public void Execute(String query, String[] prepParams) {
		Connection connection = GetConnection();
		PreparedStatement prepStatement = null;

		try {
			prepStatement = connection.prepareStatement(query);
			if (prepParams != null) {
				for (int i = 0; i < prepParams.length; i++) {
					prepStatement.setString(i + 1, prepParams[i]);
 				}
			}
			prepStatement.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Disconnect(null, prepStatement, connection);
	}
	
	
	public void Disconnect(ResultSet resultSet, PreparedStatement prepStatement, Connection connection) {
        try {
            if (resultSet != null) {
            	resultSet.close();
            }
            if (prepStatement != null) {
            	prepStatement.close();
            }
            if (connection != null) {
            	connection.close();
            }
        } catch (SQLException e) {
			e.printStackTrace();
        }		
	}
}
