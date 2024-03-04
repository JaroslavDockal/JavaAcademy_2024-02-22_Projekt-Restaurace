// Restaurace má očíslované stoly. Zákazníci u těchto stolů si objednávají jídla.
// Jedna objednávka popisuje objednání jednoho konkrétního jídla a vztahuje se ke konkrétnímu stolu.
// U každé objednávky sleduj jaké jídlo bylo objednáno, kolik kusů tohoto jídla bylo objednáno,
// kdy byla objednávka zadána a kdy vyřízena (orderedTime a fulfilmentTime) a zda je zaplacena.


package com.engeto.ja.restaurant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order {

    private int tableNumber;
    private OrderItem item;
    private boolean paid;

    public Order(int tableNumber, OrderItem item) throws RestaurantException {
        setTableNumber(tableNumber);
        this.item = item;
        this.paid = false;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) throws RestaurantException {
        if (fulfilmentTime.isBefore(item.getOrderedTime())) {
            throw new RestaurantException("Chyba při zadávání času vyřízení objednávky: " + fulfilmentTime.format(DateTimeFormatter.ofPattern(Settings.getDateFormat()))
                         + " - čas vyřízení nemůže být před časem zadání objednávky: " + item.getOrderedTime());
            }
        item.setFulfilmentTime(fulfilmentTime);
    }

    public void setFulfilmentTime() throws RestaurantException {
        item.setFulfilmentTime(LocalDateTime.now());
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setAsPaid() {
        this.paid = Boolean.TRUE;
    }

    public void setTableNumber(int tableNumber) throws RestaurantException {
        if (tableNumber < 1 || tableNumber > Settings.getMaxTableNumber()) {
            throw new RestaurantException("Zadané číslo \"" + tableNumber + "\" není v rozsahu platných stolů.");
        }
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public OrderItem getItem() {
        return item;
    }

    public boolean isPaid() {
        return paid;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        totalPrice = totalPrice.add(item.getDish().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return totalPrice;
    }

    public LocalDateTime getOrderedTime() {
        return item.getOrderedTime();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Objednávka: stůl ").append(tableNumber).append(", Položky: ");

        stringBuilder.append(item.getQuantity()).append("x ").append(item.getDish().getTitle()).append(", ");

        stringBuilder.append("objednáno: ").append(item.getOrderedTime());
        if (item.getFulfilmentTime() != null) {
            stringBuilder.append(", vyřízeno: ").append(item.getFulfilmentTime());
        }
        stringBuilder.append(", zaplaceno: ").append(paid ? "ano" : "ne");
        return stringBuilder.toString();
    }


    public int compareTo(Order otherOrder) {
        return compareTo(otherOrder, OrdersSortingCriteria.TABLE_NUMBER);
    }

    public int compareTo(Order otherOrder, OrdersSortingCriteria sortingCriteria) {
        switch (sortingCriteria) {
            case TABLE_NUMBER:
                return Integer.compare(this.tableNumber, otherOrder.tableNumber);
            case ORDERED_TIME:
                return this.item.getOrderedTime().compareTo(otherOrder.item.getOrderedTime());
            case FULFILMENT_TIME:
                if (this.item.getFulfilmentTime() == null && otherOrder.item.getFulfilmentTime() == null) {
                    return 0;
                } else if (this.item.getFulfilmentTime() == null) {
                    return -1;
                } else if (otherOrder.item.getFulfilmentTime() == null) {
                    return 1;
                } else {
                    return this.item.getFulfilmentTime().compareTo(otherOrder.item.getFulfilmentTime());
                }
            case TOTAL_PRICE:
                return this.getTotalPrice().compareTo(otherOrder.getTotalPrice());
            default:
                throw new IllegalArgumentException("Nepodporované kritérium řazení: " + sortingCriteria);
        }
    }

}
