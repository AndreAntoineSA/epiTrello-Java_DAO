package fr.epita.epitrello.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import fr.epita.epitrello.dao.Configuration;
import fr.epita.epitrello.dao.EpitrelloJDBCDAO;
import fr.epita.epitrello.datamodel.User;

public class TestJDBC {

	private final static String URL = Configuration.getValueFromKey("url");
	private final static String USER = Configuration.getValueFromKey("user");
	private final static String PASSWORD = Configuration.getValueFromKey("password");
	
	public static void main(String[] args) throws SQLException {

		testConnection();
		testCreateTable();
		testInsertNewUser();
		testGetAllUsers();

	}

	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;
	}

	private static void testConnection() throws SQLException{
		
		// given
		Connection connection = getConnection();

		// when
		String schema = connection.getSchema();

		// then
		boolean success = "PUBLIC".equals(schema);

		System.out.println("Success ? " + success);
		connection.close();
		
	}
	
	private static void testCreateTable() throws SQLException {

		Connection connection = getConnection();
		
		String resetQuery="DROP TABLE IF EXISTS USERS;" +
		"CREATE TABLE USERS(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(50));";
		
		
		PreparedStatement prepStmt = connection.prepareStatement(resetQuery);
		int result = prepStmt.executeUpdate();
		
		System.out.println("Success ? " + String.valueOf(result==0) );
		
		prepStmt.close();
		

	}
	
	private static void testInsertNewUser() throws SQLException{

		User user = new User("AndreAndAlvaro");

		EpitrelloJDBCDAO epitrelloJDBCDAO = new EpitrelloJDBCDAO();

		int id = epitrelloJDBCDAO.insertUser(user);

		boolean success = id != 0;

		System.out.println("Success ? " + success);
		
	}

	private static void testGetAllUsers() throws SQLException{
		
		EpitrelloJDBCDAO epitrelloJDBCDAO = new EpitrelloJDBCDAO();
		
		HashMap<String, User> users = epitrelloJDBCDAO.getAllUsers();
		
		for (User user: users.values()) {
			System.out.println(user.getName());
		}
		
	}

}
