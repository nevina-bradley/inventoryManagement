package com.codedifferently.inventorymanagement.controllers;

import com.codedifferently.inventorymanagement.models.itemTemplate;
import com.codedifferently.inventorymanagement.services.itemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/template")
public class itemTemplateController {
    private itemTemplateService itemTemplateService;

    @Autowired
    public itemTemplateController(itemTemplateService itemTemplateService) {
        this.itemTemplateService = itemTemplateService;
    }

    @PostMapping
    public ResponseEntity<itemTemplate> createTemplate(@RequestBody itemTemplate itemTemplate) {
        itemTemplate createdTemplate = itemTemplateService.createTemplate(itemTemplate);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<itemTemplate> getById(@PathVariable("id") Integer id) {
        try {
            Optional<itemTemplate> itemTemplate = itemTemplateService.getById(id);

            if (itemTemplate.isPresent()) {
                return ResponseEntity.ok(itemTemplate.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<itemTemplate>> getAll() {
        List<itemTemplate> templateList = itemTemplateService.getAll();
        return ResponseEntity.ok(templateList);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        boolean deleted = itemTemplateService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
