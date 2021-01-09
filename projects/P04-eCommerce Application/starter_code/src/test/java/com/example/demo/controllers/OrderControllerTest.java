package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    private static Item item;
    private static Long ITEM_ID = 1L;
    private static String ITEM_NAME = "testItem";
    private static String DESCRIPTION = "Test item description.";
    private static BigDecimal PRICE = new BigDecimal(10.00);
    private static int QUANTITY = 2;

    private static User user;
    private static String USER_NAME = "testUser";
    private static Long USER_ID = 1L;

    private static Cart cart;

    private static UserOrder userOrder;

    @Before
    public void setup () {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

        item = new Item();
        item.setId(ITEM_ID);
        item.setName(USER_NAME);
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);

        cart = new Cart();
        cart.setTotal(new BigDecimal(0.0));

        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        cart.setItems(itemList);

        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setCart(cart);

        userOrder = UserOrder.createFromCart(user.getCart());

        List<UserOrder> orderList = new ArrayList<UserOrder>();
        orderList.add(userOrder);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(orderList);
    }

    @Test
    public void submit() {
        final ResponseEntity<UserOrder> response = orderController.submit(USER_NAME);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserOrder returnedUserOrder = response.getBody();
        assertEquals(userOrder.getUser(), returnedUserOrder.getUser());
    }

    @Test
    public void get_orders_for_user() {
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(USER_NAME);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserOrder> returnedOrderList = response.getBody();
        assertEquals(userOrder.getId(), returnedOrderList.get(0).getId());
    }
}
