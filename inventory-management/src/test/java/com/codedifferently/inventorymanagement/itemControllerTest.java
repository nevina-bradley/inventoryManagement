package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.controllers.itemController;
import com.codedifferently.inventorymanagement.models.item;
import com.codedifferently.inventorymanagement.services.itemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class itemControllerTest {
    @Autowired
    private itemController itemController;

    @MockBean
    private itemService itemService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void addItemTest() throws Exception {
        item newItem = new item();
        newItem.addItem("new item");

        when(itemService.addItem(newItem)).thenReturn(newItem);

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newItem)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        item mockItem = new item();
        mockItem.setId(1);
        mockItem.setUnitsPerItem(25);
        mockItem.setPurchaseDate();

        Optional<item> mockItemOptional = Optional.of(mockItem);
        BDDMockito.when(itemService.getById(mockItem.getId())).thenReturn(mockItemOptional);

        mockMvc.perform(get("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.unitsPerItem").value(25))
                .andExpect(jsonPath("$.purchaseDate").value());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(itemService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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

        when(itemService.updateItem(any(Integer.class), any(item.class))).thenReturn(Optional.of(updatedItem));

        mockMvc.perform(put("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unitsPerItem").value(50))
                .andExpect(jsonPath("$.purchaseDate").value());
    }

    @Test
    public void deleteItemSuccessTest() throws Exception {
        Integer id = 1;

        when(itemService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteItemNotFoundTest() throws Exception {
        Integer id = 2;

        when(itemService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
