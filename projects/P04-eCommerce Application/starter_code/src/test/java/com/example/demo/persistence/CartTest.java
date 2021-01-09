package com.example.demo.persistence;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CartTest {

    private static Cart cart;
    private static BigDecimal TOTAL = new BigDecimal(10.0);

    private static User user;
    private static String USER_NAME = "testUser";
    private static Long USER_ID = 1L;

    private static Item item;
    private static Long ITEM_ID = 1L;
    private static String ITEM_NAME = "testItem";
    private static String DESCRIPTION = "Test item description.";
    private static BigDecimal PRICE = new BigDecimal(10.00);

    private static List<Item> itemList;

    private static Long CART_ID = 2L;

    @BeforeClass
    public static void setup () {
        cart = new Cart();

        user = new User();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setCart(cart);

        item = new Item();
        item.setId(ITEM_ID);
        item.setName(USER_NAME);
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);

        itemList = new ArrayList<Item>();
        itemList.add(item);
    }

    @Test
    public void cart_constructor() {
        Cart testCart = new Cart();
        assertNotNull(testCart);
    }

    @Test
    public void set_get_total() {
        cart.setTotal(TOTAL);
        assertEquals(TOTAL, cart.getTotal());
    }

    @Test
    public void set_get_user() {
        cart.setUser(user);
        assertEquals(user.getUsername(), cart.getUser().getUsername());
    }

    @Test
    public void set_get_cart_id() {
        cart.setId(CART_ID);
        assertEquals(CART_ID, cart.getId());
    }

    @Test
    public void set_get_items() {
        cart.setItems(itemList);
        assertEquals(itemList.size(), cart.getItems().size());
    }

    @Test
    public void add_item() {
        cart.addItem(item);
        assertEquals(item.getId(), cart.getItems().get(0).getId());
    }
}
