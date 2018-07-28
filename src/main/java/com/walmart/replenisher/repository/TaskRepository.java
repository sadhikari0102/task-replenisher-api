package com.walmart.replenisher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmart.replenisher.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

		public List<Task> findByAssignedToId(long user_id);
		public List<Task> findByCreatedById(long user_id);
	
}
