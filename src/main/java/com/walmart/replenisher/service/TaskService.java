package com.walmart.replenisher.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.walmart.replenisher.entity.Task;
import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.exception.DataConstraintViolationException;
import com.walmart.replenisher.exception.GenericDataException;
import com.walmart.replenisher.exception.InvalidRoleException;
import com.walmart.replenisher.exception.InvalidStatusException;
import com.walmart.replenisher.exception.UserNotABusinessException;
import com.walmart.replenisher.exception.UserNotFoundException;
import com.walmart.replenisher.repository.TaskRepository;
import com.walmart.replenisher.repository.UserRepository;
import com.walmart.replenisher.utils.ApplicationUtilities;
import com.walmart.replenisher.utils.TaskComparator;

@Service
public class TaskService {

	static final Logger log = LoggerFactory.getLogger(TaskService.class);
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationUtilities utilities;
	
	public Task createTask(String username, Task task) {
		log.warn("Inside TaskService createTask");
		User user = task.getAssignedTo();
		User creator = userRepository.findByName(username).get();
		
		if(user != null) {
			if(!userRepository.existsByName(user.getName()))
				throw new UserNotFoundException("Task assignee doesn't exist as a user in the system!");
			else {
				if(userRepository.findByName(user.getName()).get().getRole().getRole().equals("ADMIN"))
					throw new InvalidRoleException("ADMIN cannot be assigned with a task!");
				user = userRepository.findByName(user.getName()).get();
			}
		}
		
		if(user != null && creator.getRole().getRole().equals("INDIVIDUAL") && !user.getName().equals(username))
			throw new UserNotABusinessException("Individuals can only assign tasks to self");
		
		task.setAssignedTo(user);
		task.setCreatedBy(creator); 
		
		trackStatus(task);
		trackTime(task);
		
		try {
			
			return taskRepository.save(task);

		}
		catch(ConstraintViolationException e) {

			throw new DataConstraintViolationException(utilities.getConstaintsMessage(e));
		}
	}
	
	public Task updateTask(String username, long taskId, Task task) {
		Task fromDatabase = taskRepository.findById(taskId).get();
		
		task.setTaskId(taskId);
		task.setCreatedBy(fromDatabase.getCreatedBy());
		
		User assignedTo = task.getAssignedTo();
		if(assignedTo != null) {
			if(!userRepository.existsByName(assignedTo.getName()))
				throw new UserNotFoundException("Task assignee doesn't exist as a user in the system!");
			else{
				if(userRepository.findByName(assignedTo.getName()).get().getRole().getRole().equals("ADMIN"))
					throw new InvalidRoleException("ADMIN cannot be assigned with a task!");
				task.setAssignedTo(userRepository.findByName(assignedTo.getName()).get());
			}
		}
		else
			task.setAssignedTo(fromDatabase.getAssignedTo());
		
		if(task.getDescription() == null)
			task.setDescription(fromDatabase.getDescription());
		
		if(task.getPriority() == 0)
			task.setPriority(fromDatabase.getPriority());
		if(task.getStatus() == null)
			task.setStatus(fromDatabase.getStatus());
		else
			trackTime(task);
		
		return taskRepository.save(task);
	}
	
	public void trackStatus(Task task) {
		if(task.getAssignedTo() == null)
			task.setStatus("CREATED");
		else
			task.setStatus("ASSIGNED");
	}

	public void trackTime(Task task) {
		Task fromDatabase = taskRepository.getOne(task.getTaskId());
		if(task.getTaskId() > 0) {
			
			if(fromDatabase.getStatus().equals(task.getStatus()))
				return;
			else
				task.setStartTime(fromDatabase.getStartTime());
		}
		switch(task.getStatus()) {
		case "CREATED" : 
			task.setStartTime(null);
			task.setEndTime(null);
			break;
		
		case "ASSIGNED" : 
			task.setStartTime(null);
			task.setEndTime(null);
			break;
		
		case "STARTED" : 
			task.setStartTime(new Date());
			task.setEndTime(null);
			break;
		
		case "FINISHED" : 
			if(fromDatabase != null && !fromDatabase.getStatus().equals("STARTED"))
				throw new GenericDataException("Task not yet started!");
			task.setEndTime(new Date());
			break;
		
		default :
			throw new InvalidStatusException("Invalid status value (possible values : CREATED/ASSIGNED/STARTED/FINISHED) !");
		}
	}
	
	public List<Task> getTasks(String username) {
		User user = userRepository.findByName(username).get();
		List<Task> result = null;
		switch (user.getRole().getRole()) {
		
		case "BUSINESS":
			result = taskRepository.findAll();
			break;
		case "INDIVIDUAL":
			result = taskRepository.findByAssignedToId(user.getId());
			break;
		}
		
		Collections.sort(result, new TaskComparator());
		
		return result;
	}
	
}
