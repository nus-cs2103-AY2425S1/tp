package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents a property unit in the address book.
 * Guarantees: immutable; unit is valid as declared in {@link #isValidUnit(String)}.
 */
public class Unit {
    public static final Logger LOGGER = LogsCenter.getLogger(Unit.class);
    public static final Unit DEFAULT_LANDED_UNIT =
            new Unit("00-00");
    public static final String MESSAGE_CONSTRAINTS =
            "Unit numbers should only contain numbers delimited by a dash, and it should be minimally 2 digits long";
    public static final String VALIDATION_REGEX =
            "^(?:\\d{2}|1[0-3]\\d|14[0-8])-(?:\\d{2,5}|10\\d{4}|110\\d{3}|1110\\d{2}|11110\\d|111110)$";
    public final String value;

    /**
     * Constructs a {@code Unit}.
     *
     * @param unit A valid unit number.
     */
    public Unit(String unit) {
        LOGGER.info("Creating Unit object: " + unit);
        requireNonNull(unit);
        assert unit != null : "Unit string cannot be null";
        checkArgument(isValidUnit(unit), MESSAGE_CONSTRAINTS);
        assert isValidUnit(unit) != false : "Unit string must be at least two digit integers delimited by -";
        LOGGER.info("Unit object created: " + unit);
        value = unit;
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Unit)) {
            return false;
        }

        Unit otherUnit = (Unit) other;
        return value.equals(otherUnit.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
