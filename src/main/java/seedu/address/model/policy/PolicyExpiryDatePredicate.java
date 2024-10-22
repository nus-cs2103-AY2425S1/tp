package seedu.address.model.policy;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Policy}'s expiry date is within the specified number of days from the current date.
 */
public class PolicyExpiryDatePredicate implements Predicate<Policy> {

    private final LocalDate currentDate;
    private final int daysFromExpiry;

    /**
     * Constructs a {@code PolicyExpiryDatePredicate} that tests if a policy expires within the given time frame.
     *
     * @param currentDate The current date.
     * @param daysFromExpiry The number of days from the current date to check for expiring policies.
     */
    public PolicyExpiryDatePredicate(LocalDate currentDate, int daysFromExpiry) {
        this.currentDate = currentDate;
        this.daysFromExpiry = daysFromExpiry;
    }

    @Override
    public boolean test(Policy policy) {
        ExpiryDate expiryDate = policy.getExpiryDate();

        // returns true if the expiry date is between the current date and the next 'daysFromExpiry' days
        return (expiryDate.isAfter(currentDate) || expiryDate.isEqual(currentDate))
                && expiryDate.isBefore(currentDate.plusDays(daysFromExpiry + 1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PolicyExpiryDatePredicate)) {
            return false;
        }

        PolicyExpiryDatePredicate otherPredicate = (PolicyExpiryDatePredicate) other;
        return currentDate.equals(otherPredicate.currentDate) && daysFromExpiry == otherPredicate.daysFromExpiry;
    }
}
