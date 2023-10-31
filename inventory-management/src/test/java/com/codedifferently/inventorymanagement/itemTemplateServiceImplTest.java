package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.models.itemTemplate;
import com.codedifferently.inventorymanagement.repos.itemTemplateRepo;
import com.codedifferently.inventorymanagement.services.itemTemplateServiceImpl;
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
public class itemTemplateServiceImplTest {
    @Autowired
    private itemTemplateServiceImpl itemTemplateService;

    @MockBean
    private itemTemplateRepo itemTemplateRepo;

    @Test
    public void createTemplateTest() {
        itemTemplate newTemplate = new itemTemplate();
        newTemplate.setItemName("item");
        newTemplate.setUnitsPerItem(25);
        newTemplate.setLoanable(false);
        newTemplate.setNotificationLimit(10);

        when(itemTemplateRepo.save(newTemplate)).thenReturn(newTemplate);

        itemTemplate createdTemplate = itemTemplateService.addItem(newTemplate);

        assertNotNull(createdTemplate);
        assertEquals("itemName", createdTemplate.getItemName());
        assertEquals("unitsPerItem", createdTemplate.getUnitsPerItem());
        assertEquals("notificationLimit", createdTemplate.getNotificationLimit());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        itemTemplate itemTemplate = new itemTemplate();
        itemTemplate.setId(id);
        when(itemTemplateRepo.findById(id)).thenReturn(Optional.of(itemTemplate));

        Optional<itemTemplate> result = itemTemplateService.getById(id);

        assertTrue(result.isPresent());
        itemTemplate foundTemplate = result.get();
        assertEquals(id, foundTemplate.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(itemTemplateRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(Exception.class, () -> {
            itemTemplateService.getById(1);
        });
    }

    @Test
    public void getAllTest() throws Exception {
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
    public void deleteTemplateTest() {
        Integer id = 1;
        when(itemTemplateRepo.existsById(id)).thenReturn(true);

        boolean result = itemTemplateService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteTemplateNotFoundTest() {
        Integer id = 1;
        when(itemTemplateRepo.existsById(id)).thenReturn(false);

        boolean result = itemTemplateService.delete(id);

        assertFalse(result);
    }
}
