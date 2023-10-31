package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.users;

import java.util.Optional;

public interface usersService {
    users createUser(users users);

    Optional<users> getById(Integer id) throws Exception;

    Optional<users> getByEmail(String email) throws Exception;

    Optional<users> updateUser(Integer id, users users) throws Exception;

    boolean delete(Integer id);
}
