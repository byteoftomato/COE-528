package bookstore.state;

/**
 * State interface for the State Design Pattern.
 * A customer can be in either SilverState or GoldState.
 */
public interface CustomerState {

    /**
     * Returns the name of the state.
     */
    String getStatusName();

    /**
     * Calculates the final cost after redeeming points.
     * Rule: 100 points = 1 CAD discount.
     */
    double getCostAfterRedeem(int currentPoints, double originalCost);

    /**
     * Calculates how many points remain after redeeming points.
     */
    int getRemainingPointsAfterRedeem(int currentPoints, double originalCost);
}
