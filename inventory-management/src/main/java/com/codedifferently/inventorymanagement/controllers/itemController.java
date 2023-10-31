package com.codedifferently.inventorymanagement.controllers;

import com.codedifferently.inventorymanagement.models.item;
import com.codedifferently.inventorymanagement.services.itemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/items")
public class itemController {
    private itemService itemService;

    @Autowired
    public itemController(itemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<item> addItem(@RequestBody item item) {
        item addedItem = itemService.addItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<item> getById(@PathVariable("id") Integer id) {
        try {
            Optional<item> item = itemService.getById(id);

            if (item.isPresent()) {
                return ResponseEntity.ok(item.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<item>> getAll() {
        List<item> itemList = itemService.getAll();
        return ResponseEntity.ok(itemList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<item> updateItem(@PathVariable Integer id, @RequestBody item updatedItem) throws Exception {
        Optional<item> itemUpdated = itemService.updateItem(id, updatedItem);
        if (itemUpdated.isPresent()) {
            return ResponseEntity.ok(itemUpdated.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = itemService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
