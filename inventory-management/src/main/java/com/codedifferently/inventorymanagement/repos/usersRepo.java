package com.codedifferently.inventorymanagement.repos;

import com.codedifferently.inventorymanagement.models.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface usersRepo extends JpaRepository<users, Integer> {
    Optional<users> findById(Integer id);

    Optional<users> findByEmail(String email);

    users save(users users);
}
