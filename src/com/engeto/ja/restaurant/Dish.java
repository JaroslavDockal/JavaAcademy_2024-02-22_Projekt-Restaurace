//Zásobník receptů
//Kuchaři mají připraven zásobník jídel (dish + cook book).
//U každého jídla/receptu ukládáme název (title), cenu (price), přibližnou dobu přípravy (preparation time) v minutách a URL odkaz na fotografii (image).
// Fotografie samotná je na serveru, její ukládání tedy neřešíš.
//Ulož pouze název souboru bez přípony — například bolonske-spagety-01. (Všechny obrázky jsou ve formátu .PNG, ani příponu tedy nemusíme ukládat.)
//
//Doba přípravy musí být kladné číslo — systém nepovolí zadat záporné číslo či nulu.
//
//Kuchaři mají možnost některá jídla ze zásobníku vyřadit, přidat, nebo upravit.
//V systému kvůli budoucímu zobrazování nesmí být jídlo/recept bez fotografie, ale na serveru je speciální
//fotografie s názvem blank, kterou použij jako výchozí pro recepty, které zatím fotografii nemají.

package comm.engeto.restaurant;

import java.math.BigDecimal;

public class Dish {
    private final String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;

    public Dish(String title, BigDecimal price, int preparationTime, String image) {
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public Dish(String title, BigDecimal price, int preparationTime) {
        this(title, price, preparationTime, )
    }
}
