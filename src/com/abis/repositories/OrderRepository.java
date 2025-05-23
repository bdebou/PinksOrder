package com.abis.repositories;

import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.repositories.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderRepository {
    List<Order> getAll();
    void addOrder(Order order);
    Order getByPerson(Person person) throws OrderNotFoundException;
    void writeOrderHistory(Order order);
}
