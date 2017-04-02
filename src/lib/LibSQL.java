package lib;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class LibSQL {

	private static Properties GetProperties() {
		Properties properties = new Properties();
		
		/* MySQL */
		// properties.setProperty("driver",   "com.mysql.jdbc.Driver");
		
		/* PostgreSQL */
		properties.setProperty("driver",   "org.postgresql.Driver");
		properties.setProperty("url",      "jdbc:postgresql://localhost:5432/library");
		properties.setProperty("user",     "postgres");
		properties.setProperty("password", "passw");
		
		return properties;
	}

	
	private static Connection GetConnection(){
		Properties properties = GetProperties();
		Connection connection = null;

		try {
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;		
	}
	
	
	public static List<String[]> GetQueryRows(String query, Object[] prepParams) {
		Connection connection = GetConnection();
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		List<String[]> table = null;
		
		try {
			prepStatement = connection.prepareStatement(query);
			
			if (prepParams != null) {
				int i = 0;
				for(Object param : prepParams) {
				    if (param != null) {
				    	prepStatement.setObject(++i, param);
				    } else {
				    	// set null parameter if value type is null and type is unknown
				    	prepStatement.setNull(++i, Integer.MIN_VALUE);			    	
				    }
				}					
			}
			
			System.out.println("LibSQL GetRows: " + prepStatement.toString());
			
			resultSet = prepStatement.executeQuery();

			int colCount = resultSet.getMetaData().getColumnCount();
			table = new ArrayList<>();
			while(resultSet.next()) {
			    String[] row = new String[colCount];
			    for(int col = 1; col <= colCount; col++){
		            Object obj = resultSet.getObject(col);
		            row[col-1] = (obj == null) ? null : obj.toString();
			    }
			    table.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Disconnect(resultSet, prepStatement, connection);
		}

		return table;
	}

	
	public static void Execute(String query, Object[] prepParams) {
		Connection connection = GetConnection();
		PreparedStatement prepStatement = null;

		try {
			prepStatement = connection.prepareStatement(query);
			
			int i = 0;
			for(Object param : prepParams) {
			    if (param != null) {
			    	prepStatement.setObject(++i, param);
			    } else {
			    	// set null parameter if value type is null and type is unknown
			    	prepStatement.setNull(++i, Integer.MIN_VALUE);			    	
			    }
			}			
			
			System.out.println("LibSQL Exec: " + prepStatement.toString());
			
			prepStatement.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Disconnect(null, prepStatement, connection);
		}	
	}
	
	
	public static void Disconnect(ResultSet resultSet, PreparedStatement prepStatement, Connection connection) {
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
