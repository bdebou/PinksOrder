package com.abis.repositories;

import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.models.actors.Student;
import com.abis.repositories.exceptions.OrderNotFoundException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import exception.MaxSandwichesReachedException;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

public class OrderRepositoryTest {


    @Test
    public void getByPersonOrderNotFoundException() throws SandwichNotFoundException, MaxSandwichesReachedException, OrderNotFoundException {
        FileSandwichRepository fileSandwichRepository = new FileSandwichRepository(Path.of("inputcsv/input.csv").toFile());
        Person person = new Student("Raghunath", "Singh", "rgu@gmail.com", "TCS");
        Person person2 = new Student("Raghu", "Singh", "rgu2@gmail.com", "TCS");
        Order order = new Order("JAVA", person);
        order.addSandwich(fileSandwichRepository.getSandwichByName("Salami"));
        order.addSandwich(fileSandwichRepository.getSandwichByName("Gouda"));

        FileOrderRepository fileOrderRepository = new FileOrderRepository();
        fileOrderRepository.addOrder(order);
//        fileOrderRepository.getByPerson(person2);
        assertThrows(OrderNotFoundException.class, () -> fileOrderRepository.getByPerson(person2));

    }


    @Test
    public void getByPersonOrderFound() throws SandwichNotFoundException, MaxSandwichesReachedException, OrderNotFoundException {
        FileSandwichRepository fileSandwichRepository = new FileSandwichRepository(Path.of("inputcsv/input.csv").toFile());
        Person person = new Student("Raghunath", "Singh", "rgu@gmail.com", "TCS");
        Person person2 = new Student("Raghu", "Singh", "rgu2@gmail.com", "TCS");
        Order order = new Order("JAVA", person);
        order.addSandwich(fileSandwichRepository.getSandwichByName("Salami"));
        order.addSandwich(fileSandwichRepository.getSandwichByName("Gouda"));

        FileOrderRepository fileOrderRepository = new FileOrderRepository();
        fileOrderRepository.addOrder(order);
//        fileOrderRepository.getByPerson(person2);
        assertEquals("Raghunath",fileOrderRepository.getByPerson(person).getOrderingPerson().getFirstname());
        assertEquals("Singh",fileOrderRepository.getByPerson(person).getOrderingPerson().getLastname());

    }

}
