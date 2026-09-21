package com.andef.javalabs.lab3.service;

import com.andef.javalabs.lab3.exception.NotEnoughItemsException;
import com.andef.javalabs.lab3.model.Storable;
import com.andef.javalabs.lab3.model.WithdrawalResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class Warehouse {

    private final List<Storable> items = new ArrayList<>();

    public synchronized void store(Storable item) {
        if (item == null) {
            throw new IllegalArgumentException("The item must not be null");
        }
        items.add(item);
        System.out.println("Stored: " + item.getDescription());
    }

    public synchronized WithdrawalResult withdraw(
            String requestedBy,
            Class<? extends Storable> itemType,
            int quantity
    ) {
        if (requestedBy == null || requestedBy.isBlank()) {
            throw new IllegalArgumentException("The requester must be specified");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if ("blocked-user".equalsIgnoreCase(requestedBy)) {
            throw new SecurityException("This user is not allowed to access the warehouse");
        }

        List<Storable> matchingItems = items.stream()
                .filter(itemType::isInstance)
                .limit(quantity)
                .toList();

        if (matchingItems.size() < quantity) {
            throw new NotEnoughItemsException(
                    "Requested " + quantity + " item(s) of type " + itemType.getSimpleName()
            );
        }

        items.removeAll(matchingItems);

        return new WithdrawalResult(
                requestedBy,
                new ArrayList<>(matchingItems),
                "Items issued successfully"
        );
    }

    public synchronized List<Storable> getItems() {
        return List.copyOf(items);
    }
}
