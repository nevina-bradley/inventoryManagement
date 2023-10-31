package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.controllers.usersController;
import com.codedifferently.inventorymanagement.models.users;
import com.codedifferently.inventorymanagement.services.usersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
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
public class usersControllerTest {
    @Autowired
    private usersController usersController;

    @MockBean
    private usersService usersService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void createUserTest() throws Exception {
        users newUser = new users();
        newUser.addItem("new item");

        when(usersService.addItem(newUser)).thenReturn(newUser;

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getByIdFoundTest() throws Exception {
        Integer id = 1;

        users mockUser = new users();
        mockUser.setId(1);
        mockUser.setRole();
        mockUser.setEmail("email");
        mockUser.setPassword("password");
        mockUser.setToken("84532754");

        Optional<users> mockUsersOptional = Optional.of(mockUser);
        BDDMockito.when(usersService.getById(mockUser.getId())).thenReturn(mockUsersOptional);

        mockMvc.perform(get("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role").value())
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.token").value("84532754"));
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        Integer id = 2;

        when(usersService.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsersTest() throws Exception {
        Integer id = 1;

        users existingUser = new users();
        existingUser.setId(id);
        existingUser.setRole();
        existingUser.setEmail("email");
        existingUser.setPassword("password");
        existingUser.setToken("84532754");

        users updatedUser = new users();
        updatedUser.setEmail("new email");
        updatedUser.setPassword("new password");

        when(usersService.updateUser(any(Integer.class), any(users.class))).thenReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value())
                .andExpect(jsonPath("$.email").value("new email"))
                .andExpect(jsonPath("$.password").value("new password"))
                .andExpect(jsonPath("$.token").value("84532754"));
    }

    @Test
    public void deleteTemplateSuccessTest() throws Exception {
        Integer id = 1;

        when(usersService.delete(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTemplateNotFoundTest() throws Exception {
        Integer id = 2;

        when(usersService.delete(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
