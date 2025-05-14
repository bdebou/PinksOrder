package com.abis.repositories;

import java.nio.file.Path;

public class UnitOfWork {
    private final SandwichRepository sandwichRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public UnitOfWork() {
        this.sandwichRepository = new FileSandwichRepository(Path.of("inputcsv/sandwiches.csv").toFile());
        this.personRepository = new FilePersonRepository(Path.of("inputcsv/persons.csv").toFile());
        this.orderRepository = new FileOrderRepository();

    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public SandwichRepository getSandwichRepository() {
        return sandwichRepository;
    }
}
