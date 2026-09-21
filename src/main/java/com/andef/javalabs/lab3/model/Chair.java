package com.andef.javalabs.lab3.model;

import com.andef.javalabs.lab3.service.Warehouse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Chair implements Storable {

    private final String color;

    @Override
    public void sendToWarehouse(Warehouse warehouse) {
        warehouse.store(this);
    }

    @Override
    public String getDescription() {
        return "Chair, color: " + color;
    }
}
