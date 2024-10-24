package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's days attended in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDaysAttended(int)}
 */
public class DaysAttended {

    public static final String MESSAGE_CONSTRAINTS = "Days attended should be a non-negative integer";
    private int daysAttended;

    /**
     * Constructs a {@code daysAttended}.
     *
     * @param daysAttended A valid days attended.
     */
    public DaysAttended(int daysAttended) {
        requireNonNull(daysAttended);
        checkArgument(isValidDaysAttended(daysAttended), MESSAGE_CONSTRAINTS);
        this.daysAttended = daysAttended;
    }

    public int getDaysAttended() {
        return daysAttended;
    }

    /**
     * Returns if a given integer is a valid days attended.
     */
    @Override
    public String toString() {
        return Integer.toString(daysAttended);
    }

    /**
     * Returns if a given integer is a valid days attended.
     */
    public static boolean isValidDaysAttended(int test) {
        return test >= 0;
    }

    /**
     * Returns the days attended.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DaysAttended)) {
            return false;
        }

        DaysAttended otherDaysAttended = (DaysAttended) other;
        return daysAttended == otherDaysAttended.daysAttended;
    }

    /**
     * Returns the hashcode of the days attended.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(daysAttended);
    }

    /**
     * Increments the days attended by 1.
     */
    public void increment() {
        this.daysAttended++;
    }

    /**
     * Decrements the days attended by 1.
     */
    public void decrement() {
        if (this.daysAttended > 0) {
            this.daysAttended--;
        }
    }

    /**
     * Resets the days attended to 0.
     */
    public void reset() {
        this.daysAttended = 0;
    }
}
