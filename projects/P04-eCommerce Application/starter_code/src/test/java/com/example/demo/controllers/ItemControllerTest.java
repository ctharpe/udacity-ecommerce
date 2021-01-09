package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private static ItemController itemController;
    private static ItemRepository itemRepository = mock(ItemRepository.class);

    private static Item item;
    private static Long ITEM_ID = 1L;
    private static String ITEM_NAME = "testItem";
    private static String DESCRIPTION = "Test item description.";
    private static BigDecimal PRICE = new BigDecimal(10.00);
    private static int QUANTITY = 2;

    private static String USER_NAME = "testUser";

    private static List<Item> itemList;


    @BeforeClass
    public static void setup () {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);

        item = new Item();
        item.setId(ITEM_ID);
        item.setName(USER_NAME);
        item.setDescription(DESCRIPTION);
        item.setPrice(PRICE);

        when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(item));

        itemList = new ArrayList<Item>();
        itemList.add(item);

        when(itemRepository.findByName(ITEM_NAME)).thenReturn(itemList);
    }

    @Test
    public void get_item_by_id() {
        final ResponseEntity<Item> response = itemController.getItemById(ITEM_ID);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item foundItem = response.getBody();
        assertEquals(foundItem.getId(), item.getId());
    }

    @Test
    public void get_items_by_name() {
        final ResponseEntity<List<Item>> response = itemController.getItemsByName(ITEM_NAME);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Item> foundItemList = response.getBody();

        assertEquals(foundItemList.size(), itemList.size());

        Item foundItem = foundItemList.get(0);
        assertEquals(item.getName(), foundItem.getName());

    }
}
