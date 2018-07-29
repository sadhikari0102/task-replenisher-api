package com.walmart.replenisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.replenisher.entity.Task;
import com.walmart.replenisher.service.TaskService;
import com.walmart.replenisher.utils.ApplicationUtilities;

@RequestMapping("/authorized")
@RestController
public class TaskController {

	static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplicationUtilities utilities;
	
	
	@PreAuthorize("hasAnyRole('BUSINESS', 'INDIVIDUAL')")
	@RequestMapping(method = RequestMethod.POST, value = "/tasks")
	public Task createTask(@RequestHeader("Authorization") String authorization, @RequestBody Task task) {
		String username = utilities.getUsername(authorization);
		return taskService.createTask(username, task);
	}
	
	@PreAuthorize("hasAnyRole('BUSINESS', 'INDIVIDUAL')")
	@RequestMapping(method = RequestMethod.PUT, value = "/tasks/{taskId}")
	public Task updateTask(@RequestHeader("Authorization") String authorization, @RequestBody Task task, @PathVariable("taskId") long taskId) {
		String username = utilities.getUsername(authorization);
		return taskService.updateTask(username, taskId, task);
	}
	
	@PreAuthorize("hasAnyRole('BUSINESS', 'INDIVIDUAL')")
	@RequestMapping(method = RequestMethod.GET, value = "/tasks")
	public List<Task> getTasks(@RequestHeader("Authorization") String authorization) {
		String username = utilities.getUsername(authorization);
		return taskService.getTasks(username);
	}

}
