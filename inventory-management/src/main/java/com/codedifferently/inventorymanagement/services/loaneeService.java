package com.codedifferently.inventorymanagement.services;

import com.codedifferently.inventorymanagement.models.loanee;

import java.util.List;
import java.util.Optional;

public interface loaneeService {
    loanee addLoanee(loanee loanee);

    Optional<loanee> getById(Integer id) throws Exception;

    Optional<loanee> getByEmail(String email) throws Exception;

    Optional<loanee> getByLastName(String lastName) throws Exception;

    List<loanee> getAll();

    Optional<loanee> updateLoanee(Integer id, loanee loanee) throws Exception;

    boolean delete(Integer id);
}
