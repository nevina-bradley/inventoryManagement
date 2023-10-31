package com.codedifferently.inventorymanagement.controllers;

import com.codedifferently.inventorymanagement.models.loanee;
import com.codedifferently.inventorymanagement.services.loaneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loanee")
public class loaneeController {
    private loaneeService loaneeService;

    @Autowired
    public loaneeController(loaneeService loaneeService) {
        this.loaneeService = loaneeService;
    }

    @PostMapping
    public ResponseEntity<loanee> addLoanee(@RequestBody loanee loanee) {
        loanee addedLoanee = loaneeService.addLoanee(loanee);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<loanee> getById(@PathVariable("id") Integer id) {
        try {
            Optional<loanee> loanee = loaneeService.getById(id);

            if (loanee.isPresent()) {
                return ResponseEntity.ok(loanee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{email}")
    public ResponseEntity<loanee> getByEmail(@PathVariable("email") String email) {
        try {
            Optional<loanee> loanee = loaneeService.getByEmail(email);

            if (loanee.isPresent()) {
                return ResponseEntity.ok(loanee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{lastName}")
    public ResponseEntity<loanee> getByLastName(@PathVariable("lastName") String lastName) {
        try {
            Optional<loanee> loanee = loaneeService.getByLastName(lastName);

            if (loanee.isPresent()) {
                return ResponseEntity.ok(loanee.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<loanee>> getAll() {
        List<loanee> loaneeList = loaneeService.getAll();
        return ResponseEntity.ok(loaneeList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<loanee> updateLoanee(@PathVariable Integer id, @RequestBody loanee updatedLoanee) throws Exception {
        Optional<loanee> loaneeUpdated = loaneeService.updateLoanee(id, updatedLoanee);
        if (loaneeUpdated.isPresent()) {
            return ResponseEntity.ok(loaneeUpdated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = loaneeService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
