package seedu.address.model.goodsreceipt;

import java.util.function.Predicate;

/**
 * Tests that a {@code GoodsReceipt}'s {@code isDelivered} matches the boolean given.
 */
public class DeliveredPredicate implements Predicate<GoodsReceipt> {
    private final boolean isDelivered;

    /**
     * Constructs a predicate that checks if good receipt is delivered.
     */
    public DeliveredPredicate(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public boolean test(GoodsReceipt goodsData) {
        return goodsData.isDelivered() == this.isDelivered;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveredPredicate otherDeliveredPredicate)) {
            return false;
        }

        return isDelivered == otherDeliveredPredicate.isDelivered;
    }

    @Override
    public String toString() {
        return this.isDelivered ? "Delivered" : "Pending";
    }
}
