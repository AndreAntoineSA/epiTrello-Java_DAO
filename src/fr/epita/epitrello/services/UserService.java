package fr.epita.epitrello.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import fr.epita.epitrello.dao.EpitrelloJDBCDAO;
import fr.epita.epitrello.datamodel.Task;
import fr.epita.epitrello.datamodel.User;
import fr.epita.epitrello.logger.LoggerFile;

public class UserService {

	private HashMap<String, User> users = new HashMap<>();
	private LoggerFile fileLogger;
	
	public UserService() {
		this.fileLogger = new LoggerFile();
		
		//recover all users from database
		EpitrelloJDBCDAO epitrelloJDBCDAO = new EpitrelloJDBCDAO();
		this.users = epitrelloJDBCDAO.getAllUsers();
	}
	
	public String addUser(String name)	{
		if (this.users.containsKey(name)) {
			fileLogger.log("User not created, user already exists: " + name);
			return "User already exists";
		}
		User user = new User(name);
		this.users.put(name, user);
		EpitrelloJDBCDAO epitrelloJDBCDAO = new EpitrelloJDBCDAO();
		int id = epitrelloJDBCDAO.insertUser(user);
		boolean success = id != 0;
		if(success) {
			fileLogger.log("User created and added to DB: " + name);
			return "Success";
		}else {
			fileLogger.log("User created BUT NOT ADDED to DB: " + name);
			return "ERROR: User created but not added to database";
		}

	}
	
	public User getUser(String name) {
		return this.users.get(name);
	}
	
	public void printAllUsers() {
		for (User user : this.users.values()) {
		    System.out.println(user.getName());
		}
	}
	
	public String printUserTasks(String name) {
		
		if(!this.userExists(name)) {
			this.fileLogger.log("User tasks not printed, user does not exit: " + name);
			return "User does not exist";
		}
		
		User user = this.users.get(name);
		
		String printLogger="User " + user.getName() + "\n";
		String printConsole="";
		for (Task task: user.getTasks().values()) {
			printLogger+= task.getPriority() + " | " + task.getName() + " | " + task.getUserAssigned() + " | " + task.getDuration() + "h\n";
			printConsole+= task.getPriority() + " | " + task.getName() + " | " + task.getUserAssigned() + " | " + task.getDuration() + "h\n";
		}
		this.fileLogger.log(printLogger);
		
		return printConsole;
		
	}
	
	public Boolean userExists(String name) {
		return this.users.containsKey(name);
	}
	
	//Print in descending order
	public String printUsersByPerformance() {
		String printLogger = "";
		String printConsole = "";
        ArrayList<User> sortedUsers = new ArrayList<User>(this.users.values()); 
        Collections.sort(sortedUsers, Comparator.comparing(User::getPerformance).reversed());
        for (User user : sortedUsers) {
        	printLogger += "User = " + user.getName()+ ", Performance= " + user.getPerformance() + "\n";   
        	printConsole += user.getName() + "\n";  
        }
        this.fileLogger.log(printLogger);
        return printConsole;
	}
	
	//Print in ascending order 
	public String printUsersByWorkload() {
		
		String printLogger = "";
		String printConsole = "";
        ArrayList<User> sortedUsers = new ArrayList<User>(this.users.values()); 
        Collections.sort(sortedUsers, Comparator.comparing(User::getTotalWorkload));
        for (User user : sortedUsers) {
        	printLogger += "User = " + user.getName()+ ", TotalWorkload= " + user.getTotalWorkload() + "\n";   
        	printConsole += user.getName() + "\n";
        }
        this.fileLogger.log(printLogger);
        return printConsole;
		
	}

}