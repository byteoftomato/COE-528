package bookstore.state;

/**
 * Concrete state: Silver
 */
public class SilverState implements CustomerState {

    @Override
    public String getStatusName() {
        return "Silver";
    }

    @Override
    public double getCostAfterRedeem(int currentPoints, double originalCost) {
        int maxRedeemablePoints = (int) Math.floor(originalCost * 100.0);
        int pointsToRedeem = Math.min(currentPoints, maxRedeemablePoints);
        double discount = pointsToRedeem / 100.0;
        return originalCost - discount;
    }

    @Override
    public int getRemainingPointsAfterRedeem(int currentPoints, double originalCost) {
        int maxRedeemablePoints = (int) Math.floor(originalCost * 100.0);
        int pointsToRedeem = Math.min(currentPoints, maxRedeemablePoints);
        return currentPoints - pointsToRedeem;
    }
}
