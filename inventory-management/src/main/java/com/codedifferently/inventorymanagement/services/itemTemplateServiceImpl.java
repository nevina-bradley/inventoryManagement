package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.itemTemplate;
import com.codedifferently.inventorymanagement.repos.itemTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class itemTemplateServiceImpl implements itemTemplateService {
    private itemTemplateRepo itemTemplateRepo;

    @Autowired
    public itemTemplateServiceImpl(itemTemplateRepo itemTemplateRepo) {
        this.itemTemplateRepo = itemTemplateRepo;
    }

    @Override
    public itemTemplate createTemplate(itemTemplate itemTemplate) {
        return itemTemplateRepo.save(itemTemplate);
    }

    @Override
    public Optional<itemTemplate> getById(Integer id) throws Exception {
        Optional<itemTemplate> itemTemplate = itemTemplateRepo.findById(id);
        if(itemTemplate.isEmpty()){
            throw new Exception("Could not find item template with id " + id);
        }
        return itemTemplateRepo.findById(id);
    }

    @Override
    public List<itemTemplate> getAll() {
        List<itemTemplate> itemTemplates = itemTemplateRepo.findAll();
        return itemTemplateRepo.findAll();
    }

    @Override
    public boolean delete(Integer id) {
        if (itemTemplateRepo.existsById(id)) {
            itemTemplateRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
