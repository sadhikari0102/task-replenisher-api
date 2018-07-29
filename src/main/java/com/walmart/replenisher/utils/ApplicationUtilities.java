package com.walmart.replenisher.utils;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.replenisher.entity.Role;
import com.walmart.replenisher.exception.InvalidRoleException;
import com.walmart.replenisher.repository.RoleRepository;

@Service
public class ApplicationUtilities {
	@Autowired
	private RoleRepository roleRepository;
	
	public String getUsername(String authorization) {
		
		String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                Charset.forName("UTF-8"));
        return credentials.split(":")[0];
	}
	
	public void validateRole(Role role) {
		
		if(!roleRepository.existsById(role.getRole()))
			throw new InvalidRoleException("Role " + role.getRole() + " doesn't exist!");

	}
	
	public String getConstaintsMessage(ConstraintViolationException e) {
		
		StringBuilder message = new StringBuilder();
		Set<ConstraintViolation<?>> constraints = e.getConstraintViolations();
		for(ConstraintViolation<?> constraint : constraints) {
			message.append(constraint.getPropertyPath() + " : " + constraint.getMessage() + "\n");
		}
		return message.toString();
	}
}
