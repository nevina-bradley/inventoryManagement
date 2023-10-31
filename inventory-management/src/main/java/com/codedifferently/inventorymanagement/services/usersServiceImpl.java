package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.users;
import com.codedifferently.inventorymanagement.repos.usersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class usersServiceImpl implements usersService {
    private usersRepo usersRepo;

    @Autowired
    public usersServiceImpl(usersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public users createUser(users users) {
        return usersRepo.save(users);
    }

    @Override
    public Optional<users> getById(Integer id) throws Exception {
        Optional<users> users = usersRepo.findById(id);
        if(users.isEmpty()){
            throw new Exception("Could not find user with id " + id);
        }
        return usersRepo.findById(id);
    }

    @Override
    public Optional<users> getByEmail(String email) throws Exception {
        Optional<users> users = usersRepo.findByEmail(email);
        if(users.isEmpty()){
            throw new Exception("Could not find user with email " + email);
        }
        return usersRepo.findByEmail(email);
    }

    @Override
    public Optional<users> updateUser(Integer id, users users) throws Exception {
        if (!usersRepo.existsById(id)) {
            throw new Exception("User not found with ID: " + id);
        }

        users.setId(id);

        users updatedUsers = usersRepo.save(users);

        return Optional.of(updatedUsers);
    }

    @Override
    public boolean delete(Integer id) {
        if (usersRepo.existsById(id)) {
            usersRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
