package com.walmart.replenisher.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "TASK")
public class Task {

	@Id
	@Column(name = "TASK_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taskId;
	
	@NotNull
	@Column(name = "DESCRIPTION")
	private String description;
	
	@NotNull
	@Column(name = "STATUS")
	private String status;
	
	@NotNull
	@Column(name = "PRIORITY")
	private String priority;
	
	@Column(name = "START_TIME")
	private Date startTime;

	@Column(name = "END_TIME")
	private Date endTime;

	@ManyToOne
	private User createdBy;

	@ManyToOne
	private User assignedTo;
	
	public Task() {
		
	}

	public long getTaskId() {
		return taskId;
	}


	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public User getAssignedUser() {
		return assignedTo;
	}


	public void setAssignedUser(User assignedUser) {
		this.assignedTo = assignedUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}	
	
	public User getCreatorUser() {
		return createdBy;
	}

	public void setCreatorUser(User creatorUser) {
		this.createdBy = creatorUser;
	}
	
}
