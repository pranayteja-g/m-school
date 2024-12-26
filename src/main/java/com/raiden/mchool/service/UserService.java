package com.raiden.mchool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.raiden.mchool.custom_exceptions.UsernameAlreadyTakenException;
import com.raiden.mchool.model.Role;
import com.raiden.mchool.model.User;
import com.raiden.mchool.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public User createUser(User user) {
		Optional<User> existingUser = userRepository.getUserByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			throw new UsernameAlreadyTakenException("Username already taken. Choose another.");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	public String verify(User user) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			// Fetch user from the database based on the username
			User authenticatedUser = userRepository.getUserByUsername(user.getUsername())
					.orElseThrow(() -> new UsernameNotFoundException("User not found: " + user.getUsername()));

			// Generate JWT token using the authenticated user's role
			return jwtService.generateToken(authenticatedUser.getUsername());
		} else {
			return "fail";
		}
	}

	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	public ResponseEntity<User> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<User> getUserByUsername(String username) {
		Optional<User> user = userRepository.getUserByUsername(username);
		return user.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	public ResponseEntity<List<User>> getUsersByRole(Role role) {
		List<User> users = userRepository.getUserByRole(role);
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	public Optional<User> updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(userToUpdate -> {
			userToUpdate.setUsername(updatedUser.getUsername());
			userToUpdate.setPassword(updatedUser.getPassword());
			userToUpdate.setRole(updatedUser.getRole());

			return userRepository.save(userToUpdate);
		});
	}

	public boolean deleteUser(Long id) {
		Optional<User> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
