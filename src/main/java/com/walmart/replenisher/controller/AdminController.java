package com.walmart.replenisher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.service.UserService;

@RequestMapping("/admin")
@RestController
public class AdminController {

	static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public User createAdmin(@RequestBody User user) {
		User tempUser = userService.createAdmin(user);
		return tempUser;
		
	}
}
