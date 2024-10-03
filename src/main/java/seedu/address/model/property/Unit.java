package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Unit {
    public static final String MESSAGE_CONSTRAINTS =
            "Unit numbers should only contain numbers delimited by a dash, and it should be minimally 2 digits long";
    public static final String VALIDATION_REGEX = "\\d{2,}-\\d{2,}";
    public final String value;

    public Unit(String unit) {
        requireNonNull(unit);
        checkArgument(isValidUnit(unit), MESSAGE_CONSTRAINTS);
        value = unit;
    }

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

        Unit otherUnit= (Unit) other;
        return value.equals(otherUnit.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}