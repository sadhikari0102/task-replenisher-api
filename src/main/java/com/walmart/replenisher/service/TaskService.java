package com.walmart.replenisher.service;

import java.util.List;

import org.aspectj.weaver.ast.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.walmart.replenisher.entity.Task;
import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.exception.UserNotABusinessException;
import com.walmart.replenisher.exception.UserNotFoundException;
import com.walmart.replenisher.repository.TaskRepository;
import com.walmart.replenisher.repository.UserRepository;

@Service
public class TaskService {

	static final Logger log = LoggerFactory.getLogger(TaskService.class);
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;
	
	public Task createTask(String username, Task task) {
		log.warn("Inside TaskService createTask");
		User user = task.getAssignedUser();
		User creater = userRepository.findByName(username).get();
		
		if(!userRepository.existsByName(user.getName()))
			throw new UserNotFoundException("Task assignee doesn't exist as a user in the system!");
		else
			user = userRepository.findByName(user.getName()).get();
		
		if(creater.getRole().getRole().equals("INDIVIDUAL") && !user.getName().equals(username))
			throw new UserNotABusinessException("Individuals can only assign tasks to self");
		
		task.setAssignedUser(user);
		task.setCreatorUser(creater);
		
		
		return taskRepository.save(task);
	}

	public List<Task> getTasks(String username) {
		User user = userRepository.findByName(username).get();
		switch (user.getRole().getRole()) {
		case "BUSINESS":
			return taskRepository.findAll();
			
		case "INDIVIDUAL":
			return taskRepository.findByAssignedToId(user.getId());
		}
		return null;
	}
	
}
