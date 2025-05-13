package com.abis.models.actors;

import com.abis.exceptiosn.MissingTokenException;

public class OfficeManager extends Person {

    public OfficeManager(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }

    public static OfficeManager parseFromCSV(String csvLine) throws MissingTokenException {
        String[] items = csvLine.split(";");
        if (items.length != 4) throw new MissingTokenException(csvLine);

        return new OfficeManager(items[1], items[2], items[3]);
    }
}
