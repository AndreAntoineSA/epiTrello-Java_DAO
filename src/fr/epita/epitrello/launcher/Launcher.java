package fr.epita.epitrello.launcher;

import fr.epita.epitrello.dao.EpitrelloJDBCDAO;
import fr.epita.epitrello.logger.LoggerFile;
import fr.epita.epitrello.services.ListService;
import fr.epita.epitrello.services.TaskService;
import fr.epita.epitrello.services.UserService;

public class Launcher {

	public static void main(String[] args) {

		//NOTE: LOGGER
		//All outputs below are also printed in a logger file in a more representative way
		//change the logger filename: fr.epita.epitrello.logger.LoggerFile 
	    
		//NOTE: DATABASE
		//users added to the system are automatically inserted into the database
		//change database configuration: fr.epita.epitrello.dao.config
		//change filename of database configuration file: fr.epita.epitrello.dao.Configuration
		
		//Drop and Create table in database (comment it for permanent storage of added users into db)
		//EpitrelloJDBCDAO dao = new EpitrelloJDBCDAO();
		//dao.resetTable();
		
		//Clean the log file from previous executions
		LoggerFile lf = new LoggerFile();
		lf.clear();
		
		UserService userService = new UserService();
		System.out.println( userService.addUser("Thomas") );
		System.out.println( userService.addUser("AmirAli") );
		System.out.println( userService.addUser("Rabih") );

		ListService listService = new ListService();
		System.out.println( listService.addList("Code") );
		System.out.println( listService.addList("Description") );
		System.out.println( listService.addList("Misc") );
        
		TaskService taskService = new TaskService(listService, userService);
	    System.out.println( taskService.addTask("Code", "Do Everything", 12, 1, "Write the whole code") ); 
	    System.out.println( taskService.editTask("Do Everything", 12, 10, "Write the whole code") );

	    System.out.println( taskService.assignTask("Do Everything", "Rabih") );
	    System.out.println( taskService.printTask("Do Everything") );
        
	    System.out.println( taskService.addTask("Code", "Destroy code formatting", 1, 2, "Rewrite the whole code in a worse format") );
		System.out.println( taskService.assignTask("Destroy code formatting", "Thomas") );
        
		System.out.println( taskService.addTask("Description", "Write Description", 3, 1, "Write the damn description") );
		System.out.println( taskService.assignTask("Write Description", "AmirAli") );
		System.out.println( taskService.addTask("Misc", "Upload Assignment", 1, 1, "Upload it") );
        
		System.out.println( taskService.completeTask("Do Everything") );
		System.out.println( userService.printUsersByPerformance() );
		System.out.println( userService.printUsersByWorkload() );
        
	    System.out.println( taskService.printUnassignedTasksByPriority() );
		System.out.println( taskService.deleteTask("Upload Assignment") );
		System.out.println( taskService.printUnfinishedTasksByPriority() );
        
		System.out.println( taskService.addTask("Misc", "Have fun", 10, 2, "Just do it") );
		System.out.println( taskService.moveTask("Have fun", "Code") );
		System.out.println( taskService.printTask("Have fun") );
        
	    System.out.println( listService.printList("Code") );
        
	    System.out.println( listService.printAllLists() );
	    
	    System.out.println( userService.printUserTasks("AmirAli") );
        
	    System.out.println( taskService.printUnassignedTasksByPriority() );
        
	    System.out.println( taskService.printUnfinishedTasksByPriority() );
	    
		
	}
}
