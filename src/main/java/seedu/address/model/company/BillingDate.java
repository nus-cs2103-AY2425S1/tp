package seedu.address.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the billing date of a company in the address book.
 * Guarantees: can be cast to an integer from 1 to 28.
 */
public class BillingDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Billing date should only be a number between 1 and 28";

    public final String date;

    /**
     * Constructs a <code>BillingDate</code>.
     *
     * @param date A valid billing date.
     */
    public BillingDate(String date) {
        requireNonNull(date);
        checkArgument(isValidBillingDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Checks if the String is a valid billing date.
     *
     * @param test String to check if valid.
     * @return true if valid.
     */
    public static boolean isValidBillingDate(String test) {
        requireNonNull(test);
        try {
            int intTest = Integer.parseInt(test);
            return ((intTest >= 1) && (intTest <= 28));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BillingDate)) {
            return false;
        }

        BillingDate otherBillingDate = (BillingDate) other;
        return this.date.equals(otherBillingDate.date);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public String toString() {
        return "day " + this.date + " of the month";
    }
}
