package com.abis.repositories;

import com.abis.models.sandwiches.*;
import com.abis.exceptiosn.MissingTokenException;
import com.abis.repositories.exceptions.SandwichAlreadyExistsException;
import com.abis.repositories.exceptions.SandwichNotFoundException;
import com.abis.repositories.exceptions.TypeNotImplementedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSandwichRepository implements SandwichRepository {
    private final List<Sandwich> sandwiches = new ArrayList<>();

    public FileSandwichRepository(File inputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String csvLine;
            /* escape the header line */
            br.readLine();
            while ((csvLine = br.readLine()) != null) {
                try {
                    /* Type;NameFR;DescFR;NameNL;DescNL;Price */
                    String[] items = csvLine.split(";");
                    if (items.length == 6) {
                        Sandwich sandwich = getSandwich(items);
                        this.addSandwich(sandwich);
                    } else {
                        throw new MissingTokenException(csvLine);
                    }
                } catch (MissingTokenException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException | TypeNotImplementedException | SandwichAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Sandwich getSandwich(String[] items) throws TypeNotImplementedException {
        String type = items[0];
        String nameFR = items[1];
        String descriptionFR = items[2];
        String nameNL = items[3];
        String descriptionNL = items[4];
        Double price = Double.parseDouble(items[5]);
        return switch (type.toLowerCase()) {
            case "meat" -> new Meat(nameFR, nameNL, price);
            case "fish" -> new Fish(nameFR, nameNL, price);
            case "chicken" -> new Chicken(nameFR, nameNL, price);
            case "cheese" -> new Cheese(nameFR, nameNL, price);
            case "specials" -> new Special(nameFR, nameNL, descriptionFR, descriptionNL, price);
            case "vegetarian" -> new Vegetarian(nameFR, nameNL, descriptionFR, descriptionNL, price);
            default -> throw new TypeNotImplementedException(type);
        };
    }

    @Override
    public void addSandwich(Sandwich sandwich) throws SandwichAlreadyExistsException {
        if (this.sandwiches.contains(sandwich)) {
            throw new SandwichAlreadyExistsException();
        }
        this.sandwiches.add(sandwich);
    }

    @Override
    public Sandwich getSandwichByName(String name) throws SandwichNotFoundException {
        return this.sandwiches.stream()
                .filter(s -> s.getNameFR().equalsIgnoreCase(name)
                        || s.getNameNL().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new SandwichNotFoundException(name));
    }

    @Override
    public List<Sandwich> getAll() {
        return this.sandwiches;
    }

    @Override
    public void removeSandwich(String nameOfSandwich) throws SandwichNotFoundException {
        Sandwich sandwichToRemove = this.getSandwichByName(nameOfSandwich);
        this.sandwiches.remove(sandwichToRemove);
    }
}
