package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a Person's days attended in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDaysAttended(int)}
 */
public class DaysAttended {

    public static final String MESSAGE_CONSTRAINTS = "Days attended should be a non-negative integer";
    private final IntegerProperty daysAttended;

    /**
     * Constructs a {@code daysAttended}.
     *
     * @param daysAttended A valid days attended.
     */
    public DaysAttended(int daysAttended) {
        checkArgument(isValidDaysAttended(daysAttended), MESSAGE_CONSTRAINTS);
        this.daysAttended = new SimpleIntegerProperty(daysAttended);
    }

    public int getDaysAttended() {
        return daysAttended.get();
    }

    /**
     * Returns if a given integer is a valid days attended.
     */
    @Override
    public String toString() {
        return Integer.toString(daysAttended.get());
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
        return getDaysAttended() == otherDaysAttended.getDaysAttended();
    }

    /**
     * Returns the hashcode of the days attended.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(daysAttended.get());
    }

    /**
     * Increments the days attended by 1.
     */
    public void increment() {
        daysAttended.set(getDaysAttended() + 1);
    }

    /**
     * Decrements the days attended by 1.
     */
    public void decrement() {
        if (getDaysAttended() > 0) {
            daysAttended.set(getDaysAttended() - 1);
        }
    }

    /**
     * Resets the days attended to 0.
     */
    public void reset() {
        daysAttended.set(0);
    }

    /**
     * Returns the property of the days attended.
     */
    public IntegerProperty daysAttendedProperty() {
        return daysAttended;
    }
}
