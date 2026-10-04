package bookstore;

import bookstore.model.Book;
import bookstore.model.BookStore;
import bookstore.model.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestModel {
    public static void main(String[] args) {
        try {
            BookStore store = new BookStore();
            store.loadData();

            System.out.println("Books loaded: " + store.getBooks().size());
            System.out.println("Customers loaded: " + store.getCustomers().size());

            Customer jane = store.findCustomer("jane", "1234");

            if (jane != null) {
                System.out.println("\n--- Redeem Logic Test ---");
                System.out.println("Initial Jane points: " + jane.getPoints());
                System.out.println("Initial Jane status: " + jane.getStatusName());

                List<Book> firstPurchase = new ArrayList<>();
                firstPurchase.add(new Book("Temp1", 200));
                firstPurchase.add(new Book("Temp2", 500));

                double cost1 = jane.buyBooks(firstPurchase);
                System.out.println("First purchase cost: " + cost1);
                System.out.println("Jane points after first purchase: " + jane.getPoints());
                System.out.println("Jane status after first purchase: " + jane.getStatusName());

                List<Book> secondPurchase = new ArrayList<>();
                secondPurchase.add(new Book("Temp3", 50));

                double cost2 = jane.redeemPointsAndBuy(secondPurchase);
                System.out.println("Redeem purchase ($50) final cost: " + cost2);
                System.out.println("Jane points after redeeming on $50: " + jane.getPoints());
                System.out.println("Jane status after redeeming on $50: " + jane.getStatusName());

                List<Book> thirdPurchase = new ArrayList<>();
                thirdPurchase.add(new Book("Temp4", 100));

                double cost3 = jane.redeemPointsAndBuy(thirdPurchase);
                System.out.println("Redeem purchase ($100) final cost: " + cost3);
                System.out.println("Jane points after redeeming on $100: " + jane.getPoints());
                System.out.println("Jane status after redeeming on $100: " + jane.getStatusName());
            }

            System.out.println("\n--- Remove Book Test ---");
            Book javaBook = store.findBookByName("Java");
            System.out.println("Books before removal: " + store.getBooks().size());

            if (javaBook != null) {
                boolean removed = store.removeBook(javaBook);
                System.out.println("Java removed? " + removed);
            }

            System.out.println("Books after removal: " + store.getBooks().size());
            System.out.println("Java exists after removal? " + (store.findBookByName("Java") != null));

            System.out.println("\n--- Remove Customer Test ---");
            Customer ali = store.findCustomer("ali", "pass");
            System.out.println("Customers before removal: " + store.getCustomers().size());

            if (ali != null) {
                boolean removed = store.removeCustomer(ali);
                System.out.println("Ali removed? " + removed);
            }

            System.out.println("Customers after removal: " + store.getCustomers().size());
            System.out.println("Ali exists after removal? " + (store.findCustomer("ali", "pass") != null));

            System.out.println("\n--- Duplicate Book Test ---");
            boolean added1 = store.addBook(new Book("Physics", 120));
            boolean added2 = store.addBook(new Book("Physics", 150));

            System.out.println("First Physics added? " + added1);
            System.out.println("Second Physics added? " + added2);
            System.out.println("Physics exists? " + (store.findBookByName("Physics") != null));

            System.out.println("\n--- Duplicate Customer Test ---");
            boolean customerAdded1 = store.addCustomer(new Customer("sara", "1111", 0));
            boolean customerAdded2 = store.addCustomer(new Customer("sara", "9999", 100));

            System.out.println("First sara added? " + customerAdded1);
            System.out.println("Second sara added? " + customerAdded2);
            System.out.println("Sara exists? " + (store.findCustomerByUsername("sara") != null));

            // To test without changing file
            //store.saveData();

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}