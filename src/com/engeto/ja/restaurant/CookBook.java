// Kuchaři mohou přidávat, odebírat, aktualizovat a získávat informace o jídlech.
// Metody pro realizaci těchto operací  jsou implementovány, ale nejsou použity.

package com.engeto.ja.restaurant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class CookBook {

    private Map<Integer, Dish> dishes = new HashMap<>();
    private PriorityQueue<Integer> freeIds = new PriorityQueue<>();
    private int nextId = 1;

    public void addDish(Dish dish) throws RestaurantException {
        if (containsDish(dish)) {
            throw new RestaurantException("Jídlo \"" + dish.getTitle() + "\" již existuje.");
        }
        try {
            int id;
            if (freeIds.isEmpty()) {
                id = nextId++;
            } else {
                id = freeIds.poll();
            }
            dishes.put(id, dish);
        } catch (Exception e) {
            throw new RestaurantException("Nastala chyba při přidávání jídla:\n" + e.getLocalizedMessage());
        }
    }

    public void addDishWithId(int id, Dish dish) throws RestaurantException {
        if (dishes.containsKey(id) || containsDish(dish)) {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" již existuje.");
        }
        dishes.put(id, dish);
    }

    public Dish getDish(int id) throws RestaurantException {
        Dish dish = dishes.get(id);
        if (dish == null) {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
        return dish;
    }

    public void removeDish(int id) throws RestaurantException {
        if (dishes.containsKey(id)) {
            dishes.remove(id);
            freeIds.add(id);
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public void updateDish(int id, Dish dish) throws RestaurantException {
        if (dishes.containsKey(id)) {
            try {
                dishes.put(id, dish);
            } catch (Exception e) {
                throw new RestaurantException("Chyba při aktualizaci jídla s ID \"" + id + "\":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public void updateDishTitle(int id, String title) throws RestaurantException {
        if (dishes.containsKey(id)) {
            try {
                dishes.get(id).setTitle(title);
            } catch (Exception e) {
                throw new RestaurantException("Chyba při aktualizaci názvu jídla s ID \"" + id + "\":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public void updateDishPrice(int id, BigDecimal price) throws RestaurantException {
        if (dishes.containsKey(id)) {
            try {
                dishes.get(id).setPrice(price);
            } catch (Exception e) {
                throw new RestaurantException("Chyba při aktualizaci ceny jídla s ID \"" + id + "\":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public void updateDishPreparationTime(int id, int preparationTime) throws RestaurantException {
        if (dishes.containsKey(id)) {
            try {
                dishes.get(id).setPreparationTime(preparationTime);
            } catch (Exception e) {
                throw new RestaurantException("Chyba při aktualizaci doby přípravy jídla s ID \"" + id + "\":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public void updateDishImage(int id, String image) throws RestaurantException {
        if (dishes.containsKey(id)) {
            try {
                dishes.get(id).setImage(image);
            } catch (Exception e) {
                throw new RestaurantException("Chyba při aktualizaci obrázku jídla s ID \"" + id + "\":\n" + e.getLocalizedMessage());
            }
        } else {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
    }

    public Map<Integer, Dish> getDishes() {
        return new HashMap<>(dishes);
    }

    public int getDishesCount() {
        return dishes.size();
    }

    public void clearDishes() {
        dishes.clear();
        nextId = 1;
    }

    public Dish getDishById(int id) throws RestaurantException{
        if (!dishes.containsKey(id)) {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
        return dishes.get(id);
    }

    public int getDishId(Dish dish) throws RestaurantException{
        try {
            for (Map.Entry<Integer, Dish> entry : dishes.entrySet()) {
                if (entry.getValue().equals(dish)) {
                    return entry.getKey();
                }
            }
            throw new RestaurantException("Jídlo nenalezeno.");
        } catch (Exception e) {
            throw new RestaurantException("Nastala chyba při hledání jídla:\n" + e.getLocalizedMessage());
        }
    }

    public Boolean containsDish(Dish dish) {
        return dishes.values().stream().anyMatch(d -> d.getTitle().equals(dish.getTitle()));
    }

    public String getDishInfo(int id) throws RestaurantException {
        Dish dish = dishes.get(id);
        if (dish == null) {
            throw new RestaurantException("Jídlo s ID \"" + id + "\" nenalezeno.");
        }
        return "Informace o jídle s ID \"" + id + "\": " + dish.getTitle() + ", cena: " + dish.getPrice() + ", doba přípravy: " + dish.getPreparationTime() + " minut, obrázek: " + dish.getImage();
    }

    public String getDishesInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        if (dishes.isEmpty()) {
            return "Seznam jídel je prázdný.";
        } else {
            for (Map.Entry<Integer, Dish> entry : dishes.entrySet()) {
                stringBuilder.append("Jídlo s ID ").append(entry.getKey()).append(": ").append(entry.getValue().getTitle()).append(", cena: ")
                        .append(entry.getValue().getPrice()).append(" Kč, doba přípravy: ").append(entry.getValue().getPreparationTime())
                        .append(" minut, obrázek: ").append(entry.getValue().getImage()).append("\n");
            }
            return stringBuilder.toString();
        }
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public void setFreeIds(PriorityQueue<Integer> freeIds) {
        this.freeIds = new PriorityQueue<>(freeIds);
    }

}