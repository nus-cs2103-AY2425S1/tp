package seedu.address.model.buyer;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Buyer}'s {@code BuyerType} matches the given keyword.
 */
public class BuyerFulfilsPredicate implements Predicate<Buyer> {
    private final String keyword;

    public BuyerFulfilsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Buyer buyer) {
        if (keyword.isEmpty()) {
            return true;
        }
        return buyer.getBuyerType().value.toString().equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyerFulfilsPredicate)) {
            return false;
        }

        BuyerFulfilsPredicate otherPredicate = (BuyerFulfilsPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("buyerType", keyword).toString();
    }
}
