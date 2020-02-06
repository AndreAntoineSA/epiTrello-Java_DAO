package fr.epita.epitrello.datamodel;

import java.util.LinkedHashMap;

public class List {
	
	private String title;
	private LinkedHashMap<String, Task> tasks = new LinkedHashMap<>();
	
	public List(String title) {	
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addTask(Task task) {
		this.tasks.put(task.getName(), task);
	}
	
	public void printAllTasks() {
		
		for (String key : this.tasks.keySet()) {
			System.out.println(key);
		}
		
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task.getName());
	}

	public LinkedHashMap<String, Task> getTasks() {
		return tasks;
	}

	public void setTasks(LinkedHashMap<String, Task> tasks) {
		this.tasks = tasks;
	}
	
}
