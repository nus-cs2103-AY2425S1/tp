package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;

/**
 * Represents a Person's days attended in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDaysAttended(int)}
 */
public class DaysAttended {

    public static final String MESSAGE_CONSTRAINTS = "Days attended should be a non-negative integer";
    public static final String DEFAULT_INPUT_VALUE = "0";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
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

    public int getValue() {
        logger.info("Getting integer value of days attended.");
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
        return getValue() == otherDaysAttended.getValue();
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
     *
     * @return A new {@code DaysAttended} instance representing the updated days attended.
     */
    public DaysAttended incremented() {
        logger.info("Incrementing days attended.");
        return new DaysAttended(getValue() + 1);
    }

    /**
     * Decrements the days attended by 1, ensuring it does not fall below 0.
     *
     * @return A new {@code DaysAttended} instance representing the updated days attended,
     *         or 0 if the current days attended is 0.
     */
    public DaysAttended decremented() {
        logger.info("Decrementing days attended.");
        int newDays = Math.max(getValue() - 1, 0);
        return new DaysAttended(newDays);
    }

    /**
     * Resets the days attended to 0.
     *
     * @return A new {@code DaysAttended} instance with days attended set to 0.
     */
    public DaysAttended reset() {
        logger.info("Resetting days attended.");
        return new DaysAttended(0);
    }

    /**
     * Returns the property of the days attended.
     */
    public IntegerProperty daysAttendedProperty() {
        requireNonNull(daysAttended);
        return daysAttended;
    }
}
