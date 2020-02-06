package fr.epita.epitrello.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import fr.epita.epitrello.datamodel.User;

public class EpitrelloJDBCDAO {
	private final static String URL = Configuration.getValueFromKey("url");
	private final static String USER = Configuration.getValueFromKey("user");
	private final static String PASSWORD = Configuration.getValueFromKey("password");
	
	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}

	public void resetTable() {

		Connection connection;
		try {
			
			connection = getConnection();

			String resetQuery="DROP TABLE IF EXISTS USERS;" +
					"CREATE TABLE USERS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50));";
		
			PreparedStatement prepStmt = connection.prepareStatement(resetQuery);
			prepStmt.executeUpdate();

			prepStmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public int insertUser(User user) {

		PreparedStatement preparedStatement;
		try (Connection connection = getConnection()) {
			preparedStatement = connection
					.prepareStatement("INSERT INTO USERS (NAME) VALUES (?)");
			preparedStatement.setString(1, user.getName());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public HashMap<String, User>  getAllUsers(){

		try (Connection connection = getConnection()) {
			
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT NAME FROM USERS");
			ResultSet result = prepareStatement.executeQuery();
			HashMap<String, User> resultHash = new HashMap<String, User>();
			while (result.next()) {
				String name = result.getString("NAME");
				User user = new User(name);
				resultHash.put(user.getName(), user);
			}
			return resultHash;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new HashMap<>();

	}

	
}