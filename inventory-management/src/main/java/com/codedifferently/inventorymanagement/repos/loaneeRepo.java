package com.codedifferently.inventorymanagement.repos;

import com.codedifferently.inventorymanagement.models.loanee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface loaneeRepo extends JpaRepository<loanee, Integer> {
    Optional<loanee> findById(Integer id);

    Optional<loanee> findByEmail(String email);

    Optional<loanee> findByLastName(String lastName);

    List<loanee> findAll();

    loanee save(loanee loanee);
}
