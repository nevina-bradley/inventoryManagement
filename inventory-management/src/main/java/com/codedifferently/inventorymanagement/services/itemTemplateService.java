package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.itemTemplate;

import java.util.List;
import java.util.Optional;

public interface itemTemplateService {
    itemTemplate createTemplate(itemTemplate itemTemplate);

    Optional<itemTemplate> getById(Integer id) throws Exception;

    List<itemTemplate> getAll();

    boolean delete(Integer id);
}
