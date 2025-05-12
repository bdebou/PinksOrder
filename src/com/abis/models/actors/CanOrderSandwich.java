package com.abis.models.actors;

import com.abis.models.Order;

public class CanOrderSandwich extends Person {
    public CanOrderSandwich(String firstname, String lastname, String email) {
        super(firstname, lastname, email);
    }

    public CanOrderSandwich(String firstname, String lastname) {
        super(firstname, lastname);
    }
}
