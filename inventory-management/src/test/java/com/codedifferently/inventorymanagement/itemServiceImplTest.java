package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.models.item;
import com.codedifferently.inventorymanagement.repos.itemRepo;
import com.codedifferently.inventorymanagement.services.itemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class itemServiceImplTest {
    @Autowired
    private itemServiceImpl itemService;

    @MockBean
    private itemRepo itemRepo;

    @Test
    public void addItemTest() {
        item newItem = new item();
        newItem.setUnitsPerItem(25);
        newItem.setPurchaseDate();

        when(itemRepo.save(newItem)).thenReturn(newItem);

        item addedItem = itemService.addItem(newItem);

        assertNotNull(addedItem);
        assertEquals("unitsPerItem", addedItem.getUnitsPerItem());
        assertEquals("purchaseDate", addedItem.getPurchaseDate());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        item item = new item();
        item.setId(id);
        when(itemRepo.findById(id)).thenReturn(Optional.of(item));

        Optional<item> result = itemService.getById(id);

        assertTrue(result.isPresent());
        item foundItem = result.get();
        assertEquals(id, foundItem.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(itemRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(Exception.class, () -> {
            itemService.getById(1);
        });
    }

    @Test
    public void updateItemTest() throws Exception {
        Integer id = 1;

        item existingItem = new item();
        existingItem.setId(id);
        existingItem.setUnitsPerItem(25);
        existingItem.setPurchaseDate();

        item updatedItem = new item();
        updatedItem.setUnitsPerItem(50);
        updatedItem.setPurchaseDate();

        when(itemRepo.existsById(id)).thenReturn(true);
        when(itemRepo.save(updatedItem)).thenReturn(updatedItem);

        Optional<item> result = itemService.updateItem(id, updatedItem);

        assertTrue(result.isPresent());
        item updated = result.get();
        assertEquals(50, updated.getUnitsPerItem());
        assertEquals(, updated.getPurchaseDate());
    }

    @Test
    public void deleteItemTest() {
        Integer id = 1;
        when(itemRepo.existsById(id)).thenReturn(true);

        boolean result = itemService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteItemNotFoundTest() {
        Integer id = 1;
        when(itemRepo.existsById(id)).thenReturn(false);

        boolean result = itemService.delete(id);

        assertFalse(result);
    }
}
