package bookstore.model;

/**
 * Represents one book in the bookstore.
 */
public class Book {
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Useful when showing the book in combo boxes, lists, etc.
     */
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}
