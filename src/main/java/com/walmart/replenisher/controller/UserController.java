package com.walmart.replenisher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.service.UserService;

@CrossOrigin
@RequestMapping("/authorized")
@RestController
public class UserController {

	static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
	public User updateUser(@RequestBody User user, @PathVariable("username") String username) {
		return userService.updateUser(username, user);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS', 'INDIVIDUAL')")
	@RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
	public User getUser(@PathVariable("username") String username) {
		return userService.getUser(username);
	}

	
	
}
