package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private static User user;
    private static String USER_NAME = "testUser";
    private static Long USER_ID = 1L;

    @Before
    public void setup () {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder );
    }

    @Test
    public void create_user_happy_path() {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("test");
        userRequest.setPassword("testPassword");
        userRequest.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());
    }

    @Test
    public void find_by_user_name() {
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);

        final ResponseEntity<User> response = userController.findByUserName(USER_NAME);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User returnedUser = response.getBody();
        assertEquals(user.getUsername(), returnedUser.getUsername());
    }

    @Test
    public void find_by_id() {
        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        final ResponseEntity<User> response = userController.findById(USER_ID);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User returnedUser = response.getBody();
        assertEquals(user.getUsername(), returnedUser.getUsername());

    }
}
