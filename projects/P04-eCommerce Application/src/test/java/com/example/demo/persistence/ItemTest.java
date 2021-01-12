package com.example.demo.persistence;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemTest {
    private static Item item;
    private static Long ITEM_ID = 1L;
    private static String ITEM_NAME = "testItem";
    private static String DESCRIPTION = "Test item description.";
    private static BigDecimal PRICE = new BigDecimal(10.00);
    private static int QUANTITY = 2;

    private static String USER_NAME = "testUser";

    @BeforeClass
    public static void setup () {
        item = new Item();
    }

    @Test
    public void item_constructor() {
        Item newItem = new Item();
        assertNotNull(newItem);
    }

    @Test
    public void set_get_id() {
        item.setId(ITEM_ID);
        assertEquals(ITEM_ID, item.getId());
    }

    @Test
    public void set_get_name() {
        item.setName(ITEM_NAME);
        assertEquals(ITEM_NAME, item.getName());
    }

    @Test
    public void set_get_price() {
        item.setPrice(PRICE);
        assertEquals(PRICE, item.getPrice());
    }

    @Test
    public void set_get_description() {
        item.setDescription(DESCRIPTION);
        assertEquals(DESCRIPTION, item.getDescription());

    }
}
