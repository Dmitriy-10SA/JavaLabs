package com.andef.javalabs.lab3.model;

import java.util.List;

public record WithdrawalResult(
        String requestedBy,
        List<Storable> items,
        String message
) {
}
