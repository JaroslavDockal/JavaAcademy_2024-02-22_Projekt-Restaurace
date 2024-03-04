//Vytvoř třídu Dish, která bude reprezentovat jednotlivá jídla v naší restauraci.
//U každého jídla/receptu ukládáme název (title), cenu (price), přibližnou dobu přípravy (preparation time) v minutách a URL odkaz na fotografii (image).
//Fotografie samotná je na serveru, její ukládání tedy neřešíš.
//Ulož pouze název souboru bez přípony — například bolonske-spagety-01. (Všechny obrázky jsou ve formátu .PNG, ani příponu tedy nemusíme ukládat.)
//Pokud nebude zadána fotografie, použij výchozí obrázek (například blank).

package com.engeto.ja.restaurant;

import java.math.BigDecimal;

public class Dish {
    private final String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;

    public Dish(String title, BigDecimal price, int preparationTime, String image) throws RestaurantException {
        this.title = title;
        this.price = price;
        setPreparationTime(preparationTime);
        this.image = image;
    }

    public Dish(String title, BigDecimal price, int preparationTime) throws RestaurantException {
        this(title, price, preparationTime, Settings.getDefaultImage());
    }

    public void setPreparationTime(int preparationTime) throws RestaurantException{
        if (preparationTime < 1) {
            throw new RestaurantException("Zadaná chybná doba přípravy. Zadaná doba: \"" + preparationTime + "\" minut.");
        }
        this.preparationTime = preparationTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Jídlo: " + title + ", cena: " + price + " Kč, doba přípravy: " + preparationTime + " minut, obrázek: " + image;
    }
}
