package com.abis.repositories;

import com.abis.models.Order;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderRepository extends CommonRepository {
    private String fileLocation = "C:\\temp\\javacourses\\orderhistory.csv";

    public String formatOrderToWrite(Order order) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy '@' hh:mm a");
        String timestamp = fmt.format(LocalDateTime.now());
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(timestamp).append(";")
                .append(order.getOrderingPerson().firstname).append(";")
                .append(order.getOrderingPerson().lastname).append(";")
                .append(order.getSandwiches());

        return (stringBuilder.toString());
    }

    public void writeOrderHistory(Order order) {
        Path path = Paths.get(fileLocation);
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, Charset.forName("UTF-8"), StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(formatOrderToWrite(order));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
