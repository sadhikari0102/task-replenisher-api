package com.walmart.replenisher.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.walmart.replenisher.entity.Role;
import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.exception.DataConstraintViolationException;
import com.walmart.replenisher.exception.DuplicateCreationException;
import com.walmart.replenisher.exception.UserNotAnAdminException;
import com.walmart.replenisher.exception.UserNotFoundException;
import com.walmart.replenisher.repository.UserRepository;
import com.walmart.replenisher.utils.ApplicationUtilities;

@Service
public class UserService {

	static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationUtilities utilities;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User createUser(User user) {
		log.info("Creating user");
		Role role = user.getRole();
		if(role != null)
			utilities.validateRole(role);
		try {

			return userRepository.save(user);
	
		}
		catch(DataIntegrityViolationException ce) {
			throw new DuplicateCreationException("User with username " + user.getName() +" already exists");
		}
		catch(ConstraintViolationException e) {
			throw new DataConstraintViolationException(utilities.getConstaintsMessage(e));
		}
	}
	
	public User updateUser(String username, User user) {
		User fromDatabase = userRepository.findByName(username).get();
		user.setId(fromDatabase.getId());
		if(user.getName() == null)
			user.setName(fromDatabase.getName());
		if(user.getRole() == null)
			user.setRole(fromDatabase.getRole());
		else
			utilities.validateRole(user.getRole());
		if(user.getPassword() == null)
			user.setPassword(fromDatabase.getPassword());
		
		return userRepository.
				save(user);
		
	}
	
	public User getUser(String username) {
		
		User user = userRepository.findByName(username).get();
		if(user == null)
			throw new UsernameNotFoundException("User with " + username + " doesn't exist");
		return user;
		
	}
	
	public User createAdmin(User user) {
		
		log.info("Creating admin");

		if(!user.getRole().getRole().equals("ADMIN"))
			throw new UserNotAnAdminException("User roles contain roles other than ADMIN");
		
		try {
			
			return userRepository.save(user);
	
		}
		catch(DataIntegrityViolationException ce) {
			throw new DuplicateCreationException("User with username " + user.getName() +" already exists");
		}
	}
	
	public void userExists(String username) {
		if(!userRepository.existsByName(username))
			throw new UserNotFoundException("User with username " + username + " not found !");
	}
	
}
