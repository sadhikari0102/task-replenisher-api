package com.walmart.replenisher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmart.replenisher.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
	
}
