package seedu.sellsavvy.model.order;

import java.util.function.Predicate;

import seedu.sellsavvy.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Order}'s {@code Status} matches the order status given.
 */
public class StatusEqualsKeywordPredicate implements Predicate<Order> {
    private final Status status;

    public StatusEqualsKeywordPredicate(Status status) {
        this.status = status;
    }

    /** Returns the value of the predicate as a string. */
    public String getValue() {
        return status.toString();
    }

    @Override
    public boolean test(Order order) {
        return status.equals(order.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatusEqualsKeywordPredicate)) {
            return false;
        }

        StatusEqualsKeywordPredicate otherStatusEqualsKeywordPredicate = (StatusEqualsKeywordPredicate) other;
        return status.equals(otherStatusEqualsKeywordPredicate.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("status", status).toString();
    }
}
