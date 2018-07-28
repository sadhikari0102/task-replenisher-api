package com.walmart.replenisher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.walmart.replenisher.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String username);
	boolean existsByName(String username);
}
