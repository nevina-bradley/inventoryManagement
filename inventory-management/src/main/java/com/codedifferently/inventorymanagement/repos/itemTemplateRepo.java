package com.codedifferently.inventorymanagement.repos;

import com.codedifferently.inventorymanagement.models.itemTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface itemTemplateRepo extends JpaRepository<itemTemplate, Integer>{
    Optional<itemTemplate> findById(Integer id);

    List<itemTemplate> findAll();

    itemTemplate save(itemTemplate itemTemplate);
}
