package com.engeto.ja.restaurant;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

import static com.engeto.ja.restaurant.Settings.getDelimiter;

public class FileOperations {

    //Obecně jsou na řádku položky:
//[číslo položky][.][mezera]
//[název jídla][mezera]
//[počet kusů v objednávce][mezera][(][celková cena včetně textu " Kč"][)]
//[dvojtečka][tabulátor]
//[čas zadání objednávky ve formátu hh:mm]
//[-]
//[čas vyřízení ve formátu hh:mm][tabulátor]
//[text "zaplaceno", pokud již byla zaplacena – jinak nic]


    //ToDo Specify the format of the file content
    public String loadOrdersFromFile(ArrayList<Order> orders, String fileName) throws RestaurantException {
        int lineCounter = 0;
        orders.clear();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine();
                System.out.println(line);
                String[] parts = line.split("\\. ");
                if (parts.length != 2) {
                    throw new RestaurantException("Nesprávný formát na řádku číslo " + lineCounter + ": " + line + "!");
                }
                String[] orderInfo = parts[1].split("\t");
                if (orderInfo.length != 4 && orderInfo.length != 5) {
                    throw new RestaurantException("Nesprávný počet položek na řádku číslo " + lineCounter + ": " + line + "!");
                }
                String[] timeInfo = orderInfo[2].split("-");
                String dishNo = orderInfo[0];
                int amount = Integer.parseInt(orderInfo[1]);
                double totalPrice = Double.parseDouble(timeInfo[0].replaceAll("[^0-9.]+", ""));
                String orderedTime = timeInfo[1].trim();
                String fulfilmentTime = null;
                boolean paid = false;
                if (orderInfo.length == 5) {
                    fulfilmentTime = orderInfo[3].trim();
                    paid = orderInfo[4].equals("zaplaceno");
                }
                Order order = new Order(dishNo, amount, totalPrice, orderedTime, fulfilmentTime, paid);
                orders.add(order);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Nesprávný formát čísla na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (DateTimeParseException e) {
            throw new RestaurantException("Nesprávný formát data na řádku číslo: " + lineCounter + "!\n" + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new RestaurantException("Nastala chyba při načítání seznamu objednávek ze souboru " + fileName + " (na řádku číslo " + lineCounter + ")!\n" + e.getLocalizedMessage());
        }
        return loadFromFileStatusMsg(orders.size());
    }

    public String saveOrdersToFile(ArrayList<Order> orders, String fileName) throws RestaurantException{
        int lineCounter = 0;
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order order : orders) {
                writer.println(
                        order.dishNumber() + Settings.getDelimiter() +
                                order.getAmount + Settings.getDelimiter() +
                                order.getTotalPrice() + Settings.getDelimiter() +
                                order.getOrderedTime() + Settings.getDelimiter() +
                                order.getFulfilmentTime() + Settings.getDelimiter() +
                                (order.isPaid() ? "zaplaceno" : "")
                lineCounter++;
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RestaurantException("Nastala chyba při zápisu do souboru " + fileName + "!\n" + e.getLocalizedMessage());
        } finally {
            return saveToFileStatusMsg(lineCounter);
        }

    }

    private String loadFromFileStatusMsg (int listSize) {
        String loadedStr, itemStr;
        switch (listSize) {
            case 0 -> {
                // Neberu to jako chybu, ale jen jako informaci o stavu
                return "Žádné položky nebyly načteny.";
            }case 1 -> {
                loadedStr = "Načtena ";
                itemStr = " položka.";
            } case 2, 3, 4 -> {
                loadedStr = "Načteny ";
                itemStr = " položky.";
            }
            default -> {
                loadedStr = "Načteno ";
                itemStr = " položek.";
            }
        }
        return loadedStr + listSize + itemStr;
    }

    private String saveToFileStatusMsg (int listSize) {
        String savedStr, itemStr;
        switch (listSize) {
            case 0 -> {
                return "Žádné položky nebyly uloženy - soubor bude prázdný.";
            }case 1 -> {
                savedStr = "Uložena ";
                itemStr = " položka.";
            } case 2, 3, 4 -> {
                savedStr = "Uloženy ";
                itemStr = " položky.";
            }
            default -> {
                savedStr = "Uloženo ";
                itemStr = " položek.";
            }
        }
        return savedStr + listSize + itemStr;
    }


}
