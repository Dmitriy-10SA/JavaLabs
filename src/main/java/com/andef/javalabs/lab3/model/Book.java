package com.andef.javalabs.lab3.model;

import com.andef.javalabs.lab3.service.Warehouse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Book implements Storable {

    private final String title;

    @Override
    public void sendToWarehouse(Warehouse warehouse) {
        warehouse.store(this);
    }

    @Override
    public String getDescription() {
        return "Book: " + title;
    }
}
