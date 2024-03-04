package com.engeto.ja.restaurant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderItem {
    private Dish dish;
    private int quantity;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;

    public OrderItem(Dish dish, int quantity, LocalDateTime orderedTime) {
        this.dish = dish;
        this.quantity = quantity;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = null;
    }

    public OrderItem(Dish dish, int quantity) {
        this(dish, quantity, LocalDateTime.now());
    }

    public OrderItem(Dish dish) {
        this(dish, 1);
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) throws RestaurantException {
        if (fulfilmentTime.isBefore(orderedTime)) {
            throw new RestaurantException("Chyba při zadávání času vyřízení objednávky: " + fulfilmentTime.format(DateTimeFormatter.ofPattern(Settings.getDateFormat())) +
                    " - čas vyřízení nemůže být před časem zadání objednávky: " + orderedTime.format(DateTimeFormatter.ofPattern(Settings.getDateFormat())));
        }
        this.fulfilmentTime = fulfilmentTime;
    }
}

