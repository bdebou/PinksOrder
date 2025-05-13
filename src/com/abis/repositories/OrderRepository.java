package com.abis.repositories;

import com.abis.models.Order;
import com.abis.models.actors.Person;

import java.util.List;

public interface OrderRepository {
    List<Order> getAll();
    void addOrder(Order order);
    Order getByPerson(Person person);
}
