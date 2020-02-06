package fr.epita.epitrello.datamodel;

import java.util.LinkedHashMap;

public class User {
	
	private String name;
	private LinkedHashMap<String, Task> tasks = new LinkedHashMap<>();
	
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void assignTask(Task task) {
		this.tasks.put(task.getName(), task);
	}
	
	public LinkedHashMap<String, Task> getTasks() {
		return tasks;
	}
	
	public void unassignTask(Task task) {
		this.tasks.remove(task.getName());
	}
	
	public Integer getTotalWorkload() {
	//duration of all tasks assigned to the user
		Integer totalWorkload = 0;
		for (Task task : this.tasks.values()) {
			totalWorkload += task.getDuration();
		}
		return totalWorkload;
	}
	
	public Integer getRemainingWorkload() {
	//duration of all tasks assigned to the user that has not been completed
		Integer remainingWorkload = 0;
		for (Task task : this.tasks.values()) {
			if (!task.getCompleted()) {
				remainingWorkload += task.getDuration();
			}
		}
		return remainingWorkload;
	}

	public Integer getPerformance() {
		//duration of all tasks assigned to the user that has been completed
		Integer performance = 0;
		for (Task task : this.tasks.values()) {
			if (task.getCompleted()) {
				performance += task.getDuration();
			}
		}
		return performance;
	}
	
}
