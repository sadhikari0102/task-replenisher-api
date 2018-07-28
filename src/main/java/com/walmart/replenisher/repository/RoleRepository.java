package com.walmart.replenisher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmart.replenisher.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
	
}
