package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.loanee;
import com.codedifferently.inventorymanagement.repos.loaneeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class loaneeServiceImpl implements loaneeService {
    private loaneeRepo loaneeRepo;

    @Autowired
    public loaneeServiceImpl(loaneeRepo loaneeRepo) {
        this.loaneeRepo = loaneeRepo;
    }

    @Override
    public loanee addLoanee(loanee loanee) {
        return loaneeRepo.save(loanee);
    }

    @Override
    public Optional<loanee> getById(Integer id) throws Exception {
        Optional<loanee> loanee = loaneeRepo.findById(id);
        if(loanee.isEmpty()){
            throw new Exception("Could not find loanee with id " + id);
        }
        return loaneeRepo.findById(id);
    }

    @Override
    public Optional<loanee> getByEmail(String email) throws Exception {
        Optional<loanee> loanee = loaneeRepo.findByEmail(email);
        if(loanee.isEmpty()){
            throw new Exception("Could not find loanee with email " + email);
        }
        return loaneeRepo.findByEmail(email);
    }

    @Override
    public Optional<loanee> getByLastName(String lastName) throws Exception {
        Optional<loanee> loanee = loaneeRepo.findByLastName(lastName);
        if(loanee.isEmpty()){
            throw new Exception("Could not find loanee with last name " + lastName);
        }
        return loaneeRepo.findByLastName(lastName);
    }

    @Override
    public List<loanee> getAll() {
        List<loanee> loanee = loaneeRepo.findAll();
        return loaneeRepo.findAll();
    }

    @Override
    public Optional<loanee> updateLoanee(Integer id, loanee loanee) throws Exception {
        if (!loaneeRepo.existsById(id)) {
            throw new Exception("Loanee not found with ID: " + id);
        }

        loanee.setId(id);

        loanee updatedLoanee = loaneeRepo.save(loanee);

        return Optional.of(updatedLoanee);
    }

    @Override
    public boolean delete(Integer id) {
        if (loaneeRepo.existsById(id)) {
            loaneeRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
