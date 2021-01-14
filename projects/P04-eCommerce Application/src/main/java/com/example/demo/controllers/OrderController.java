package com.example.demo.controllers;

import java.util.List;

import com.example.demo.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	final Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {

		User user = userRepository.findByUsername(username);
		if(user == null) {
			String message = "Could not submit order! Username " + username + " not found.";
			UserNotFoundException userNotFoundException = new UserNotFoundException(message);
			log.error(userNotFoundException.toString());
			throw userNotFoundException;
		}

		UserOrder order = UserOrder.createFromCart(user.getCart());

		if(order.getTotal() == null) {
			String message = "Could not save order! Order does not exist.";
			OrderDoesNotExistException orderDoesNotExistException = new OrderDoesNotExistException(message);
			log.error(orderDoesNotExistException.toString());
			throw orderDoesNotExistException;
		}

		if(order.getItems().size() == 0) {
			String message = "Order contains no items! Cannot submit order.";
			EmptyOrderException emptyOrderException = new EmptyOrderException(message);
			log.error(emptyOrderException.toString());
			throw emptyOrderException;
		}

		try {
			orderRepository.save(order);
		}
		catch(Exception e) {
			String message = "Could not save order: ";
			log.error(message + e);
			throw new CouldNotSaveOrderException(message);
		}
		log.info("Order successfully submitted for user " + username);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			String message = "Could retrieve order history! Username " + username + " not found.";
			UserNotFoundException userNotFoundException = new UserNotFoundException(message);
			log.error(userNotFoundException.toString());
			throw userNotFoundException;
		}

		try {
			ResponseEntity<List<UserOrder>> responseEntity = ResponseEntity.ok(orderRepository.findByUser(user));
			log.info("Order history retrieved for " + username);
			return responseEntity;
		}
		catch (Exception e) {
			String message = "Could not retrieve order history.";
			log.error(message + e);
			throw new CouldNotRetrieveOrderHistoryException(message);
		}

	}
}
