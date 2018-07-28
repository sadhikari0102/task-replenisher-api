package com.walmart.replenisher.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.walmart.replenisher.entity.Details;
import com.walmart.replenisher.entity.User;
import com.walmart.replenisher.repository.UserRepository;

@Service
public class DetailsService implements UserDetailsService {


	static final Logger log = LoggerFactory.getLogger(DetailsService.class);
	
	@Autowired
	private UserRepository userRepository; 	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Username entered : " + username);
		Optional<User> user = userRepository.findByName(username);
		log.info(user.get().getName() + " " + user.get().getPassword());
		user.orElseThrow(() -> new UsernameNotFoundException("Username not found !!"));
		return new Details(user.get());
	}

}
 