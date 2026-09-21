package com.andef.javalabs.lab3.model;

import com.andef.javalabs.lab3.service.Warehouse;

public interface Storable {

    void sendToWarehouse(Warehouse warehouse);

    String getDescription();
}
