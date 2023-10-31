package com.codedifferently.inventorymanagement.repos;

import com.codedifferently.inventorymanagement.models.item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface itemRepo extends JpaRepository<item, Integer> {
    Optional<item> findById(Integer id);

    List<item> findAll();

    item save(item item);
}
