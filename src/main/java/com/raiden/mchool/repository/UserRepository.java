package com.raiden.mchool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raiden.mchool.model.Role;
import com.raiden.mchool.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> getUserById(Long id);
	Optional<User> getUserByUsername(String username);
	List<User> getUserByRole(Role role);
	boolean existsByUsername(String username);
}
