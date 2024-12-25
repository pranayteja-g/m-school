package com.raiden.mchool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raiden.mchool.model.Role;
import com.raiden.mchool.model.User;
import com.raiden.mchool.service.UserService;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		return new ResponseEntity<>(userService.verify(user), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}

	@GetMapping("/role")
	public ResponseEntity<List<User>> getUsersByRole(@RequestParam Role role) {
		return userService.getUsersByRole(role);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		boolean isDeleted = userService.deleteUser(id);
		if (isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
