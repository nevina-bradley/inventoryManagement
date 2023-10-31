package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.models.users;
import com.codedifferently.inventorymanagement.repos.usersRepo;
import com.codedifferently.inventorymanagement.services.usersServiceImpl;
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
public class usersServiceImplTest {
    @Autowired
    private usersServiceImpl usersService;

    @MockBean
    private usersRepo usersRepo;

    @Test
    public void addUserTest() {
        users newUser = new users();
        newUser.setUnitsPerItem(25);
        newUser.setPurchaseDate();

        when(usersRepo.save(newUser)).thenReturn(newUser);

        users createdUser = usersService.addItem(newUser);

        assertNotNull(createdUser);
        assertEquals("unitsPerItem", createdUser.getUnitsPerItem());
        assertEquals("purchaseDate", createdUser.getPurchaseDate());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        users users = new users();
        users.setId(id);
        when(usersRepo.findById(id)).thenReturn(Optional.of(users));

        Optional<users> result = usersService.getById(id);

        assertTrue(result.isPresent());
        users foundUser = result.get();
        assertEquals(id, foundUser.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(usersRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(Exception.class, () -> {
            usersService.getById(1);
        });
    }

    @Test
    public void updateUserTest() throws Exception {
        Integer id = 1;

        users existingUser = new users();
        existingUser.setId(id);
        existingUser.setUnitsPerItem(25);
        existingUser.setPurchaseDate();

        users updatedUser = new users();
        updatedUser.setUnitsPerItem(50);
        updatedUser.setPurchaseDate();

        when(usersRepo.existsById(id)).thenReturn(true);
        when(usersRepo.save(updatedUser)).thenReturn(updatedUser);

        Optional<users> result = usersService.updateUser(id, updatedUser);

        assertTrue(result.isPresent());
        users updated = result.get();
        assertEquals(50, updated.getUnitsPerItem());
        assertEquals(, updated.getPurchaseDate());
    }

    @Test
    public void deleteUserTest() {
        Integer id = 1;
        when(usersRepo.existsById(id)).thenReturn(true);

        boolean result = usersService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteItemNotFoundTest() {
        Integer id = 1;
        when(usersRepo.existsById(id)).thenReturn(false);

        boolean result = usersService.delete(id);

        assertFalse(result);
    }
}
