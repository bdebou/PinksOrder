package com.abis.repositories;

import com.abis.models.sandwiches.Sandwich;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;

import java.util.List;

public interface SandwichRepository {
    void addSandwich(Sandwich sandwich) throws SandwichAlreadyExistsException;

    Sandwich getSandwichByName(String name) throws SandwichNotFoundException;

    List<Sandwich> getAll();
}
