package fr.epita.epitrello.datamodel;

public class Task {

	private String listAssigned;
	private String name;
	private Integer duration;
	private Integer priority;
	private String description;
	private String userAssigned;
	private Boolean completed;
	
	public Task(String list, String name, Integer duration, Integer priority, String description) {
		this.listAssigned = list;
		this.name = name;
		this.duration = duration;
		this.priority = priority;
		this.description = description;
		this.completed = false;
		this.userAssigned = "Unassigned";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserAssigned() {
		return userAssigned;
	}

	public void setUserAssigned(String userAssigned) {
		this.userAssigned = userAssigned;
	}

	public String getListAssigned() {
		return listAssigned;
	}

	public void setListAssigned(String list) {
		this.listAssigned = list;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	public Boolean hasUserAssigned() {
		return !this.userAssigned.equals("Unassigned");
	}

	
}
