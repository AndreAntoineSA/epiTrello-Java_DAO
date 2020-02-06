package fr.epita.epitrello.services;

import java.util.TreeMap;

import fr.epita.epitrello.datamodel.List;
import fr.epita.epitrello.datamodel.Task;
import fr.epita.epitrello.logger.LoggerFile;


public class ListService {
	
	private TreeMap<String, List> lists = new TreeMap<>();  
	LoggerFile fileLogger;
	
	public ListService() {
		this.fileLogger = new LoggerFile();
	}
	
	public String addList(String title) {
		if (this.lists.containsKey(title)) {
			fileLogger.log("List not created, list already exists: " + title);
			return "List string already exists";
		}
		List list  = new List(title);
		this.lists.put(title, list);
		this.fileLogger.log("List created: " + title);
		return "Success";
	}
	
	public List getList(String title) {
		
		return this.lists.get(title);
		
	}
	
	public Boolean listExists(String title) {
		return this.lists.containsKey(title);
	}
	
	public String printAllListsTitles() {
		
		String print="------All lists-------\n";
		for (String key : this.lists.keySet()) {
			print+= key + "\n";
		}
		print+="-------------------";
		return print;
	}
	
	//print list with tasks
	public String printList(String title) {
		
		if(!this.listExists(title)) {
			this.fileLogger.log("List not printed, list does not exit: " + title);
			return "List does not exist";
		}
		
		List list = this.lists.get(title);
		
		String print="List " + list.getTitle() + "\n";
		for (Task task: list.getTasks().values()) {
			print+= task.getPriority() + " | " + task.getName() + " | " + task.getUserAssigned() + " | " + task.getDuration() + "h\n";
		}
		print+="\n";
		this.fileLogger.log(print);
		
		return print;
	}
	
	//print all lists in order with their tasks
	public String printAllLists() {
		
		String printLogger="All lists: \n";
		String print="";
		for (List list: this.lists.values()) {
			print+= this.printList(list.getTitle());
		}
		print+="";
		printLogger+=print;
		this.fileLogger.log(printLogger);
		return print;
	}

	public void removeList(String title) {
		this.lists.remove(title);
	}
}
