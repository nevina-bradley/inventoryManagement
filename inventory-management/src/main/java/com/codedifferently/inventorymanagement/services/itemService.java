package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.item;

import java.util.List;
import java.util.Optional;

public interface itemService {
    item addItem(item item);

    Optional<item> getById(Integer id) throws Exception;

    List<item> getAll();

    Optional<item> updateItem(Integer id, item item) throws Exception;

    boolean delete(Integer id);
}
