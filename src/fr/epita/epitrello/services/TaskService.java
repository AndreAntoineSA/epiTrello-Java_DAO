package fr.epita.epitrello.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import fr.epita.epitrello.datamodel.List;
import fr.epita.epitrello.datamodel.Task;
import fr.epita.epitrello.datamodel.User;
import fr.epita.epitrello.logger.LoggerFile;

public class TaskService {

	private LinkedHashMap<String, Task> tasks = new LinkedHashMap<>(); 
	private ListService listService;
	private UserService userService;
	private LoggerFile loggerFile;
	
	public TaskService(ListService listService, UserService userService){
		this.listService = listService;
		this.userService = userService;
		this.loggerFile = new LoggerFile();
		
	}
	
	public String addTask(String list, String name, Integer duration, Integer priority, String description) {
		
		if (this.tasks.containsKey(name)) {
			loggerFile.log("Task not created, task already exists: " + name);
			return "Task already exists";
		}
		if (!this.listService.listExists(list)) {
			loggerFile.log("Task not created, list does not exist: " + name);
			return "List does not exist";
		}

		Task task = new Task(list, name, duration, priority, description);
		this.tasks.put(name, task);
		this.listService.getList(list).addTask(task);
		loggerFile.log("Task added: " + task.getName());
		return "Success";
		
	
	}

	public String editTask(String name, Integer newDuration, Integer newPriority, String newDescription) {
		
		if(!this.taskExists(name)) {
			this.loggerFile.log("Task not edited, task does not exit: " + name);
			return "Task does not exist";
		}
		this.tasks.get(name).setDuration(newDuration);
		this.tasks.get(name).setPriority(newPriority);
		this.tasks.get(name).setDescription(newDescription);
		this.loggerFile.log("Task edited: " + name);
		return "Success";
		
	}
	
	public String printTask(String name) {
		
		if(!this.taskExists(name)) {
			this.loggerFile.log("Task not printed, task does not exit: " + name);
			return "Task does not exist";
		}
		
		Task task = this.tasks.get(name);
		
		String printLog = "Print Task:\n"+
		"Name:" + task.getName() +"\n" + 
		"Descr :" + task.getDescription() + "\n" + 
		"Prior " + task.getPriority() + "\n" + 
		"Dur : " + task.getDuration() + "\n" + 
		"UserAssigned: " + task.getUserAssigned() + "\n" +
		"ListAssigned:" + task.getListAssigned();
		this.loggerFile.log(printLog);
		
		String printConsole = "" +
		task.getName() +"\n" + 
		task.getDescription() + "\n" + 
		"Priority: " + task.getPriority() + "\n" + 
		"Estimated Time: " + task.getDuration() + "\n";
		
		if(task.hasUserAssigned()) {
			printConsole+="Assigned to " + task.getUserAssigned() + "\n";
		}else {
			printConsole+="Unassigned" + "\n";
		}
		return printConsole;
		
	}
	
	public String printAllTasks() {
		String print="";
		for (Task task: this.tasks.values()) {
			print += "Task: '" + task.getName() + "', Assigned To: " + task.getUserAssigned() + "\n";
		}
		return print;
	}
	
	public String assignTask(String taskname,String username) {
		if(!this.userService.userExists(username)) {
			this.loggerFile.log("List not assigned, user does not exit: " + taskname);
			return "User does not exist";
		}
		Task task=this.tasks.get(taskname);
		task.setUserAssigned(username);
		this.userService.getUser(username).assignTask(task);
		this.loggerFile.log("Task assigned to: " + username);
		return "Success";
		
	}
	
	public String completeTask(String name) {
		if(!this.taskExists(name)) {
			this.loggerFile.log("Task not deleted, task does not exist : " + name);
			return "Task does not exist";
		}
		this.tasks.get(name).setCompleted(true);
		this.loggerFile.log("Task completed: " + name);
		return "Success";
	}
	
	public Boolean taskExists(String name) {
		return this.tasks.containsKey(name);
	}
	
	public String deleteTask(String name) {
		if(!this.taskExists(name)) {
			this.loggerFile.log("Task not deleted, task does not exit: " + name);
			return "Task does not exist";
		}
		Task task = this.tasks.get(name);
		//delete task from user.tasks
		if (task.hasUserAssigned()) {
			User userAssigned = this.userService.getUser(task.getUserAssigned());
			userAssigned.unassignTask(task);
		}
		this.listService.getList(task.getListAssigned()).removeTask(task);
		this.tasks.remove(name);
		this.loggerFile.log("Task deleted: " + name);
		return "Success";
	}
	
	public String deleteList(String title) {
		
		if(!this.listService.listExists(title)) {
			this.loggerFile.log("List not deleted, list does not exit: " + title);
			return "List does not exist";
		}
		for (Task task: this.listService.getList(title).getTasks().values()) {
			//delete task from user.tasks
			if (task.hasUserAssigned()) {
				User userAssigned = this.userService.getUser(task.getUserAssigned());
				userAssigned.unassignTask(task);
			}
			this.tasks.remove(task.getName());
		}
		this.listService.removeList(title);
		this.loggerFile.log("List deleted, and all tasks from that list deleted.");
		return "Success";
	}
	
	public String printUnassignedTasksByPriority() {
		
		ArrayList<Task> unassignedTasks = new ArrayList<Task>(); 
		for (Task task: this.tasks.values()) {
			if (!task.hasUserAssigned()) {
				unassignedTasks.add(task);
			}
		}
		String printLogger="Task Unassigned: \n";
		String printConsole="";
		Collections.sort(unassignedTasks, Comparator.comparing(Task::getPriority));
		for (Task task: unassignedTasks) {
			printLogger += "Prior: " + task.getPriority() + " | Name: " + task.getName() + " | User: " + task.getUserAssigned() + " | Dur: " + task.getDuration() + "h\n";
			printConsole += task.getPriority() + " | " + task.getName() + " | " + task.getUserAssigned() + " | " + task.getDuration() + "h\n";
		}
		this.loggerFile.log(printLogger);
		return printConsole;
		
	}

	public String printUnfinishedTasksByPriority() {
		
		ArrayList<Task> unfinishedTasks = new ArrayList<Task>(); 
		for (Task task: this.tasks.values()) {
			if (!task.getCompleted()) {
				unfinishedTasks.add(task);
			}
		}
		String printLogger="Task Unfinished: \n";
		String printConsole="";
		Collections.sort(unfinishedTasks, Comparator.comparing(Task::getPriority));
		for (Task task: unfinishedTasks) {
			printLogger += "Prior: " + task.getPriority() + " | Name: " + task.getName() + " | User: " + task.getUserAssigned() + " | Dur: " + task.getDuration() + "h\n";
			printConsole += task.getPriority() + " | " + task.getName() + " | " + task.getUserAssigned() + " | " + task.getDuration() + "h\n";
		}
		this.loggerFile.log(printLogger);
		return printConsole;
		
	}

	public String moveTask(String name, String list) {
		
		if(!this.taskExists(name)) {
			this.loggerFile.log("Task not moved, task does not exit: " + name);
			return "Task does not exist";
		}
		if (!this.listService.listExists(list)) {
			loggerFile.log("Task not moved, list does not exist: " + name);
			return "List does not exist";
		}
		
		Task task = this.tasks.get(name);
		List oldList = this.listService.getList(task.getListAssigned());
		oldList.removeTask(task);
		task.setListAssigned(list);
		List newList = this.listService.getList(list);
		newList.addTask(task);
		this.loggerFile.log("List moved: " + "'"+ oldList.getTitle() +"' -> " + "'" + newList.getTitle() + "'");
		return "Success";
	
	}

	}
