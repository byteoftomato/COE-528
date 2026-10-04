package bookstore.model;

import bookstore.state.CustomerState;
import bookstore.state.GoldState;
import bookstore.state.SilverState;
import java.util.List;

/**
 * Represents a customer in the bookstore.
 * A customer has points and a state (Silver or Gold).
 */
public class Customer extends User {
    private int points;
    private CustomerState state;

    public Customer(String username, String password, int points) {
        super(username, password);
        this.points = points;
        updateState();
    }

    public int getPoints() {
        return points;
    }

    public String getStatusName() {
        return state.getStatusName();
    }

    public CustomerState getState() {
        return state;
    }

    /**
     * Adds points earned after a purchase.
     * Rule: 10 points for every 1 CAD actually paid.
     */
    public void addEarnedPointsFromCost(double costPaid) {
        int earnedPoints = (int) Math.round(costPaid * 10.0);
        points += earnedPoints;
        updateState();
    }

    /**
     * Buys selected books without redeeming points.
     * Returns the total cost paid.
     */
    public double buyBooks(List<Book> selectedBooks) {
        double totalCost = calculateTotalCost(selectedBooks);
        addEarnedPointsFromCost(totalCost);
        return totalCost;
    }

    /**
     * Redeems points and buys selected books.
     * Returns the final cost after redemption.
     */
    public double redeemPointsAndBuy(List<Book> selectedBooks) {
        double originalCost = calculateTotalCost(selectedBooks);

        double finalCost = state.getCostAfterRedeem(points, originalCost);
        points = state.getRemainingPointsAfterRedeem(points, originalCost);

        // Earn points only on the amount actually paid after redemption.
        addEarnedPointsFromCost(finalCost);
        updateState();

        return finalCost;
    }

    /**
     * Kept because it matches your diagram idea.
     * Redeems points for a single given cost and returns the final cost.
     * Does NOT add earned points; buy/redeemPointsAndBuy handles that.
     */
    public double redeemPoints(double originalCost) {
        double finalCost = state.getCostAfterRedeem(points, originalCost);
        points = state.getRemainingPointsAfterRedeem(points, originalCost);
        updateState();
        return finalCost;
    }

    /**
     * Updates the customer's state based on current points.
     * points < 1000 -> Silver
     * points >= 1000 -> Gold
     */
    private void updateState() {
        if (points >= 1000) {
            state = new GoldState();
        } else {
            state = new SilverState();
        }
    }

    /**
     * Calculates total cost of selected books.
     */
    public static double calculateTotalCost(List<Book> selectedBooks) {
        double total = 0.0;
        for (Book b : selectedBooks) {
            total += b.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return getUsername() + "," + getPassword() + "," + points;
    }
}
