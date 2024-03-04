//Management restaurace chce mít kdykoli k dispozici informace a možnosti vyjmenované dále.
// Připrav třídu ```RestaurantManager``` s metodami pro získání těchto informací.
//
//Požadované informace:
//1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
//2. Možnost seřadit objednávky podle času zadání.
// 3. Průměrnou dobu zpracování objednávek.
//   - Pro uchování času potřebuješ třídu LocalDateTime, která ukládá i čas.
//   - Počítej dobu zpracování objednávky v minutách, nemusíš řešit sekundy.
//     Počet minut mezi dvěma údaji lze zjistit pomocí knihovních funkcí Javy — zkus hledat. Nemusíš si to programovat sám a ani by to nebylo rozumné. Když už je to hotové, neztrácej tím čas!
//4. Seznam jídel, která byla dnes objednána. Bez ohledu na to, kolikrát bylo dané jídlo objednáno.

package com.engeto.ja.restaurant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RestaurantManager {

    private ArrayList<Order> orders = new ArrayList<>();

    public int getUnfinishedOrdersCount() {
        int count = 0;
        for (Order order : orders) {
            if (!order.isPaid()) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<Order> getOrdersSortedByOrderedTime(ArrayList<Order> orders) throws RestaurantException {
        ArrayList<Order> sortedOrders = new ArrayList<>(orders);
        sortOrders(sortedOrders, OrdersSortingCriteria.ORDERED_TIME);
        return sortedOrders;
    }

    public void sortOrders(ArrayList<Order> orders, OrdersSortingCriteria sortingCriteria) throws RestaurantException {
        try {
            Collections.sort(orders, (order1, order2) -> order1.compareTo(order2, sortingCriteria));
        } catch (IllegalArgumentException e) {
            throw new RestaurantException("Nepodporované kritérium řazení: " + sortingCriteria);
        }
    }

    public double getAverageFulfilmentTime() {
        double sum = 0;
        double count = 0;
        for (Order order : orders) {
            if (order.isPaid()) {
                Duration duration = Duration.between(order.getItem().getOrderedTime(), order.getItem().getFulfilmentTime());
                sum += duration.toMinutes();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    public ArrayList<Dish> getOrderedDishes() {
        ArrayList<Dish> dishes = new ArrayList<>();
        for (Order order : orders) {
            dishes.add(order.getItem().getDish());
        }
        return dishes;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(int index) {
        orders.remove(index);
    }

    public void clearOrders() {
        orders.clear();
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public int getOrdersCount() {
        return orders.size();
    }

    public Order getOrder(int index) {
        return orders.get(index);
    }

    public void updateOrder(int index, Order order) {
        orders.set(index, order);
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void setOrder(int index, Order order) {
        orders.set(index, order);
    }

    public void setOrderAsPaid(int index) {
        orders.get(index).setAsPaid();
    }

    public void setOrderFulfilmentTime(int index, LocalDateTime fulfilmentTime) throws RestaurantException {
        orders.get(index).setFulfilmentTime(fulfilmentTime);
    }

    public void setOrderFulfilmentTime(int index) throws RestaurantException {
        orders.get(index).setFulfilmentTime();
    }

    public void setOrderTableNumber(int index, int tableNumber) throws RestaurantException {
        orders.get(index).setTableNumber(tableNumber);
    }

    public void setOrderItem(int index, OrderItem item) {
        orders.get(index).setItem(item);
    }

    public void setOrderPaid(int index, boolean paid) {
        orders.get(index).setPaid(paid);
    }

    public void setOrder(int index, int tableNumber, OrderItem item) throws RestaurantException {
        orders.set(index, new Order(tableNumber, item));
    }


}
