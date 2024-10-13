package seedu.address.model.policy;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Policy}'s expiry date is within the next 30 days.
 */
public class PolicyExpiryDatePredicate implements Predicate<Policy> {

    private final LocalDate currentDate;

    public PolicyExpiryDatePredicate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean test(Policy policy) {
        LocalDate expiryDate = policy.getExpiryDate().toLocalDate();

        // returns true if the expiry date is between the current date and the next 30 days
        return (expiryDate.isAfter(currentDate) || expiryDate.isEqual(currentDate))
                && expiryDate.isBefore(currentDate.plusDays(31));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PolicyExpiryDatePredicate // instanceof handles nulls
                && currentDate.equals(((PolicyExpiryDatePredicate) other).currentDate)); // state check
    }
}
