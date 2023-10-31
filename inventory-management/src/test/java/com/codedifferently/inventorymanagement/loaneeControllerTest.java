package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.controllers.loaneeController;
import com.codedifferently.inventorymanagement.models.loanee;
import com.codedifferently.inventorymanagement.services.loaneeService;
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
public class loaneeControllerTest {
    @Autowired
    private loaneeController loaneeController;

    @MockBean
    private loaneeService loaneeService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loaneeController).build();
    }

    @Test
    public void createLoaneeTest() throws Exception {
        loanee newLoanee = new loanee();
        newLoanee.addItem("new item");

        when(loaneeService.addItem(newLoanee)).thenReturn(newLoanee);

        mockMvc.perform(post("/api/v1/loanee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newLoanee)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        loanee mockLoanee = new loanee();
        mockLoanee.setId(1);
        mockLoanee.setFirstName("first");
        mockLoanee.setLastName("last");
        mockLoanee.setEmail("email");
        mockLoanee.setPhoneNumber("3028888888");

        Optional<loanee> mockLoaneeOptional = Optional.of(mockLoanee);
        BDDMockito.when(loaneeService.getById(mockLoanee.getId())).thenReturn(mockLoaneeOptional);

        mockMvc.perform(get("/api/v1/loanee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("first"))
                .andExpect(jsonPath("$.lastName").value("last"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.phoneNumber").value("3028888888"));
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(loaneeService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/loanee/{id}", id)
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
    public void updateItemTest() throws Exception {
        Integer id = 1;

        loanee existingLoanee = new loanee();
        existingLoanee.setId(id);
        existingLoanee.setFirstName("first");
        existingLoanee.setLastName("last");
        existingLoanee.setEmail("email");
        existingLoanee.setPhoneNumber("3028888888");

        loanee updatedLoanee = new loanee();
        updatedLoanee.setEmail("new email");
        updatedLoanee.setPhoneNumber("3024444444");

        when(loaneeService.updateLoanee(any(Integer.class), any(loanee.class))).thenReturn(Optional.of(updatedLoanee));

        mockMvc.perform(put("/api/v1/loanee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedLoanee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("first"))
                .andExpect(jsonPath("$.lastName").value("last"))
                .andExpect(jsonPath("$.email").value("new email"))
                .andExpect(jsonPath("$.phoneNumber").value("3024444444"));
    }

    @Test
    public void deleteTemplateSuccessTest() throws Exception {
        Integer id = 1;

        when(loaneeService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/loanee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTemplateNotFoundTest() throws Exception {
        Integer id = 2;

        when(loaneeService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/loanee/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
