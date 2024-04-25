import com.engeto.ja.restaurant.*;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws RestaurantException {

        String fileNameOrders = Settings.getFileNameOrders();
        String fileNameCookBook = Settings.getFileNameCookBook();
        String fileNameSummary = Settings.getFileNameSummary();

        CookBook cookBook = new CookBook();
        Orders orders = new Orders();
        RestaurantManager restaurantManager = new RestaurantManager();
        FileOperations fileOperations = new FileOperations();

//        Načti stav evidence z disku.

        try {
            fileOperations.loadCookBookFromFile(cookBook, fileNameCookBook);
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při načítání jídelníčku:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }

        try {
            fileOperations.loadOrdersFromFile(orders.getOrders(), cookBook, fileNameOrders);
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při načítání objednávek:\n" + e.getLocalizedMessage());
        }
        catch (Exception e) {
            System.out.println("Nastala neznámá chyba:\n" + e.getLocalizedMessage());
        }

        addDishToCookBook(cookBook, "Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(150), 10);
        addDishToCookBook(cookBook,"Hranolky 150 g", BigDecimal.valueOf(50), 10);
        addDishToCookBook(cookBook,"Pstruh na víně 200 g", BigDecimal.valueOf(180), 30);
        addDishToCookBook(cookBook,"Kofola 0,5 l", BigDecimal.valueOf(30), 5);


        createNewOrder(cookBook, orders, 15, 1, 2);
        createNewOrder(cookBook, orders, 15, 2, 2);
        createNewOrder(cookBook, orders, 15, 4, 2);
        setOrderFulfilmentTime(orders.getOrders(), 2);
        createNewOrder(cookBook, orders, 2, 3, 1);

        orders.printOrders();

        System.out.println("Celková cena konzumace pro stůl číslo 15: " + restaurantManager.getTotalPriceForTable(orders.getOrders(), 15) + " Kč");

        fileOperations.saveOrdersToFile(orders.getOrders(), cookBook, fileNameOrders);

        System.out.println("Počet nevyřízených objednávek: " + restaurantManager.getUnfinishedOrdersCount(orders.getOrders()));
        System.out.println("Seznam objednávek seřazený podle času objednání:\n" + restaurantManager.getOrdersSortedByOrderedTime(orders.getOrders()));
        System.out.println("Průměrná doba zpracování objednávek: " + restaurantManager.getAverageOrderProcessingTime(orders.getOrders()) + " minut");
        System.out.println("Seznam jídel, která byla dnes objednána:\n" + restaurantManager.getUniqueDishesOrderedToday(orders.getOrders()));
        System.out.println("Seznam objednávek pro stůl číslo 15:\n" + restaurantManager.getOrdersForTable(orders.getOrders(),15));

        try {
            System.out.println("Seznam objednávek seřazený podle času objednání:\n" + restaurantManager.getOrdersSortedByOrderedTime(orders.getOrders()));
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při zpracování objednávek:\n" + e.getLocalizedMessage());
        }
        setOrderFulfilmentTime(orders.getOrders(), 3);
        orders.getOrders().get(3).setAsPaid();
        try {
            fileOperations.loadOrdersFromFile(orders.getOrders(), cookBook, fileNameOrders);
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při načítání objednávek:\n" + e.getLocalizedMessage());
        }
        try {
             System.out.println("Seznam objednávek seřazený podle času objednání:\n" + restaurantManager.getOrdersSortedByOrderedTime(orders.getOrders()));
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při zpracování objednávek:\n" + e.getLocalizedMessage());
        }

    }

    private static void setOrderFulfilmentTime(List<Order> orders, int i){
        try {
            orders.get(i).setFulfilmentTime();
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při nastavování času splnění objednávky:\n" + e.getLocalizedMessage());
        }
    }

    public static void addDishToCookBook(CookBook cookBook, String title, BigDecimal price, int preparationTime) {
        System.out.println("Přidávám jídlo: " + title);
        try {
            cookBook.addDish(new Dish(title, price, preparationTime));
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při přidávání jídla:\n" + e.getLocalizedMessage());
        }
    }

    public static void createNewOrder(CookBook cookBook, Orders orders, int tableNumber, int dishId, int quantity) {
        System.out.println("Vytvářím novou objednávku pro stůl číslo " + tableNumber + ": " + quantity + "x jídlo s ID " + dishId);
        try {
            orders.addOrder(new Order(cookBook.getDishById(dishId), quantity,tableNumber));
        } catch (RestaurantException e) {
            System.err.println("Nastala chyba při vytváření objednávky:\n" + e.getLocalizedMessage());
        }
    }
}