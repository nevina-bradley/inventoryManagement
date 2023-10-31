package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.item;
import com.codedifferently.inventorymanagement.repos.itemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class itemServiceImpl implements itemService {
    private itemRepo itemRepo;

    @Autowired
    public itemServiceImpl(itemRepo itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public item addItem(item item) {
        return itemRepo.save(item);
    }

    @Override
    public Optional<item> getById(Integer id) throws Exception {
        Optional<item> item = itemRepo.findById(id);
        if(item.isEmpty()){
            throw new Exception("Could not find item with id " + id);
        }
        return itemRepo.findById(id);
    }

    @Override
    public List<item> getAll() {
        List<item> item = itemRepo.findAll();
        return itemRepo.findAll();
    }

    @Override
    public Optional<item> updateItem(Integer id, item item) throws Exception {
        if (!itemRepo.existsById(id)) {
            throw new Exception("Item not found with ID: " + id);
        }

        item.setId(id);

        item updatedItem = itemRepo.save(item);

        return Optional.of(updatedItem);
    }

    @Override
    public boolean delete(Integer id) {
        if (itemRepo.existsById(id)) {
            itemRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
