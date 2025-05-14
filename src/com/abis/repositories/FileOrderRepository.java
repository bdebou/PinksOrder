package com.abis.repositories;

import com.abis.models.Order;
import com.abis.models.actors.Person;
import com.abis.repositories.exceptions.OrderNotFoundException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository implements OrderRepository {

    private List<Order> orders = new ArrayList<>();


    private String logFileLocation = "C:\\temp\\javacourses\\orderhistory.csv";
    private String orderFileLocation = "C:\\temp\\javacourses\\order.csv";

    public String formatOrderToWrite(Order order) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy '@' hh:mm:ss a");
        String timestamp = fmt.format(LocalDateTime.now());
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(timestamp).append(";")
                .append(order.getOrderingPerson().firstname).append(";")
                .append(order.getOrderingPerson().lastname).append(";")
                .append(order.getCourse()).append(";")
                .append(order.getSandwiches()).append(";")
                .append(order.sandwiches.size()).append(";")
                .append(order.getSingleOrderPrice());



        return (stringBuilder.toString());
    }

    public void writeOrderHistory(Order order) {
        Path path = Paths.get(logFileLocation);
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(formatOrderToWrite(order));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        return this.orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public Order getByPerson(Person person) throws OrderNotFoundException {
        return orders.stream()
                .filter(p1 -> p1.orderingPerson.getFirstname().equalsIgnoreCase(person.getFirstname()) &&
                        p1.orderingPerson.getLastname().equalsIgnoreCase(person.getLastname()))
                .findFirst().orElseThrow(() -> new OrderNotFoundException("Order not found by person name"));
    }

    public double getTotalOrderPrice() {
        double totalPrice = 0.0;
        for (Order order : orders) {
            totalPrice += order.getSingleOrderPrice();

        }
        return totalPrice;
    }

    public int getTotalOrderSandwichCount(){
        int totalSandwichCount=0;
        for (Order order : orders) {
            totalSandwichCount += order.getSingleOrderSandwichCount();

        }
        return totalSandwichCount;
    }

    public void printOrderRepo() {
        Path path = Paths.get(orderFileLocation);
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write("----------------------------------------------------------------------------------------------------");
            writer.newLine();
            String titleFormat = String.format("%1$-15s%2$-15s%3$-15s%4$-50s%5$s\n", "Last Name", "First Name", "Course", "Sandwich/Bread type", "Count");
            writer.write(titleFormat);
            writer.newLine();
            for (Order order : orders) {
                writer.write(order.formatPrintOrder());
                writer.newLine();
            }
            writer.write("----------------------------------------------------------------------------------------------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%1$-15s%2$-15s%3$-15s%4$-50s%5$s\n", "Last Name", "First Name", "Course", "Sandwich/Bread type", "Count");
        for (Order order : orders) {
            System.out.println(order.formatPrintOrder());
        }
        System.out.println("----------------------------------------------------------------------------------------------------");

    }

}
