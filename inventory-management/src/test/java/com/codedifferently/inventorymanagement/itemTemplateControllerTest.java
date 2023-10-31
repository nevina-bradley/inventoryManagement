package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.controllers.itemTemplateController;
import com.codedifferently.inventorymanagement.models.itemTemplate;
import com.codedifferently.inventorymanagement.services.itemTemplateService;
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
public class itemTemplateControllerTest {
    @Autowired
    private itemTemplateController itemTemplateController;

    @MockBean
    private itemTemplateService itemTemplateService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemTemplateController).build();
    }

    @Test
    public void createTemplateTest() throws Exception {
        itemTemplate newTemplate = new itemTemplate();
        newTemplate.addItem("new item");

        when(itemTemplateService.addItem(newTemplate)).thenReturn(newTemplate);

        mockMvc.perform(post("/api/v1/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newTemplate)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        itemTemplate mockTemplate = new itemTemplate();
        mockTemplate.setId(1);
        mockItem.setUnitsPerItem(25);
        mockItem.setPurchaseDate();

        Optional<itemTemplate> mockTemplateOptional = Optional.of(mockTemplate);
        BDDMockito.when(itemTemplateService.getById(mockTemplate.getId())).thenReturn(mockTemplateOptional);

        mockMvc.perform(get("/api/v1/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.unitsPerItem").value(25))
                .andExpect(jsonPath("$.purchaseDate").value());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(itemTemplateService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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

        when(itemService.updateItem(any(Integer.class), any(item.class))).thenReturn(Optional.of(updatedItem));

        mockMvc.perform(put("/api/v1/items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unitsPerItem").value(50))
                .andExpect(jsonPath("$.purchaseDate").value());
    }

    @Test
    public void deleteTemplateSuccessTest() throws Exception {
        Integer id = 1;

        when(itemTemplateService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTemplateNotFoundTest() throws Exception {
        Integer id = 2;

        when(itemTemplateService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/templates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
