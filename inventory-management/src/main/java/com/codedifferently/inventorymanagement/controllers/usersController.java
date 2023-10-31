package com.codedifferently.inventorymanagement.controllers;

import com.codedifferently.inventorymanagement.models.users;
import com.codedifferently.inventorymanagement.services.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class usersController {
    private usersService usersService;

    @Autowired
    public usersController(usersService usersService) { this.usersService = usersService; }

    @PostMapping
    public ResponseEntity<users> createUser(@RequestBody users users) {
        users savedUser = usersService.createUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<users> getById(@PathVariable("id") Integer id) {
        try {
            Optional<users> users = usersService.getById(id);

            if (users.isPresent()) {
                return ResponseEntity.ok(users.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{email}")
    public ResponseEntity<users> getByEmail(@PathVariable("email") String email) {
        try {
            Optional<users> users = usersService.getByEmail(email);

            if (users.isPresent()) {
                return ResponseEntity.ok(users.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<users> updateUser(@PathVariable Integer id, @RequestBody users updatedUsers) throws Exception {
        Optional<users> updatedUser = usersService.updateUser(id, updatedUsers);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = usersService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
