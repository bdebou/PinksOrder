package com.abis.repositories;

import java.nio.file.Path;

public class UnitOfWork {
    private SandwichRepository sandwichRepository;
    private FileOrderRepository orderRepository;
    private FilePersonRepository personRepository;

    public UnitOfWork() {
        this.sandwichRepository=new FileSandwichRepository(Path.of("inputcsv/input.csv").toFile());
//        this.orderRepository=new ???;
//        this.personRepository=new ???;

    }

    public FileOrderRepository getOrderRepository() {
        return orderRepository;
    }

    public FilePersonRepository getPersonRepository() {
        return personRepository;
    }

    public SandwichRepository getSandwichRepository() {
        return sandwichRepository;
    }
}
