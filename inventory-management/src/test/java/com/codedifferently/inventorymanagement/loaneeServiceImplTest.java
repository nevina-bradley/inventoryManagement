package com.codedifferently.inventorymanagement;

import com.codedifferently.inventorymanagement.models.loanee;
import com.codedifferently.inventorymanagement.repos.loaneeRepo;
import com.codedifferently.inventorymanagement.services.loaneeServiceImpl;
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
public class loaneeServiceImplTest {
    @Autowired
    private loaneeServiceImpl loaneeService;

    @MockBean
    private loaneeRepo loaneeRepo;

    @Test
    public void createLoaneeTest() {
        loanee newLoanee = new loanee();
        newLoanee.setUnitsPerItem(25);
        newLoanee.setPurchaseDate();

        when(loaneeRepo.save(newLoanee)).thenReturn(newLoanee);

        loanee createdLoanee = loaneeService.addItem(newLoanee);

        assertNotNull(createdLoanee);
        assertEquals("unitsPerItem", addedItem.getUnitsPerItem());
        assertEquals("purchaseDate", addedItem.getPurchaseDate());
    }

    @Test
    public void getByIdTest() throws Exception {
        Integer id = 1;
        loanee loanee = new loanee();
        loanee.setId(id);
        when(loaneeRepo.findById(id)).thenReturn(Optional.of(loanee));

        Optional<loanee> result = loaneeService.getById(id);

        assertTrue(result.isPresent());
        loanee foundLoanee = result.get();
        assertEquals(id, foundLoanee.getId());
    }

    @Test
    public void getByIdNotFoundTest() throws Exception {
        BDDMockito.doReturn(Optional.empty()).when(loaneeRepo).findById(ArgumentMatchers.any());
        Assertions.assertThrows(Exception.class, () -> {
            loaneeService.getById(1);
        });
    }

    @Test
    public void updateLoaneeTest() throws Exception {
        Integer id = 1;

        loanee existingLoanee = new loanee();
        existingLoanee.setId(id);
        existingLoanee.setUnitsPerItem(25);
        existingLoanee.setPurchaseDate();

        loanee updatedLoanee = new loanee();
        updatedLoanee.setUnitsPerItem(50);
        updatedLoanee.setPurchaseDate();

        when(loaneeRepo.existsById(id)).thenReturn(true);
        when(loaneeRepo.save(updatedLoanee)).thenReturn(updatedLoanee);

        Optional<loanee> result = loaneeService.updateItem(id, updatedLoanee);

        assertTrue(result.isPresent());
        loanee updated = result.get();
        assertEquals(50, updated.getUnitsPerItem());
        assertEquals(, updated.getPurchaseDate());
    }

    @Test
    public void deleteItemTest() {
        Integer id = 1;
        when(loaneeRepo.existsById(id)).thenReturn(true);

        boolean result = loaneeService.delete(id);

        assertTrue(result);
    }

    @Test
    public void deleteItemNotFoundTest() {
        Integer id = 1;
        when(loaneeRepo.existsById(id)).thenReturn(false);

        boolean result = loaneeService.delete(id);

        assertFalse(result);
    }
}
