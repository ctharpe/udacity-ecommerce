package com.example.demo.controllers;

import java.util.Optional;

import com.example.demo.exceptions.PasswordConfirmationException;
import com.example.demo.exceptions.PasswordTooShortException;
import com.example.demo.exceptions.UserNotCreatedException;
import com.example.demo.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	final Logger log = LoggerFactory.getLogger(UserController.class);

	final private int requiredPasswordLength = 7;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return ResponseEntity.of(optionalUser);
		}
		else {
			String message = "Could not find user with id: " + id;
			UserNotFoundException userNotFoundException = new UserNotFoundException(message);
			log.error(userNotFoundException.toString());
			throw userNotFoundException;
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);

		if(user == null) {
			String message = "Could not find username: " + username;
			UserNotFoundException userNotFoundException = new UserNotFoundException(message);
			log.error(userNotFoundException.toString());
			throw userNotFoundException;
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);

		if(createUserRequest.getPassword().length() < requiredPasswordLength) {
			String message = "Password too short! Password must be at least " + requiredPasswordLength + " characters long.";
			PasswordTooShortException passwordTooShort = new PasswordTooShortException(message);
			log.error(passwordTooShort.toString());
			throw passwordTooShort;
		}

		if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			String message = "Password and Confirmation of Password do not match!";
			PasswordConfirmationException passwordConfirmationException = new PasswordConfirmationException(message);
			log.error(passwordConfirmationException.toString());
			throw passwordConfirmationException;
		}

		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

		try {
			userRepository.save(user);
			log.info("User created: " + user.getUsername());
		}
		catch (Exception e) {
			String message = "Failed to create user " + user.getUsername() + " :";
			log.error(message + e);
			throw(new UserNotCreatedException(message));
		}
		return ResponseEntity.ok(user);
	}
	
}
