package com.andef.javalabs.lab3.model;

import com.andef.javalabs.lab3.service.Warehouse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Laptop implements Storable {

    private final String model;

    @Override
    public void sendToWarehouse(Warehouse warehouse) {
        warehouse.store(this);
    }

    @Override
    public String getDescription() {
        return "Laptop: " + model;
    }
}
