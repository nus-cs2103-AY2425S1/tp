package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Patient's health condition in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHealthCondition(String)}
 */
public class HealthCondition {
    public static final String MESSAGE_CONSTRAINTS = "Health condition should be categorized as either"
            + " 'HIGH', 'MEDIUM', or 'LOW.'";

    private enum Priority { HIGH, MEDIUM, LOW };

    public final String value;

    /**
     * Constructs an {@code HealthCondition}.
     *
     * @param healthCondition A valid health condition (LOW, MEDIUM , HIGH).
     */
    public HealthCondition(String healthCondition) {
        checkArgument(isValidHealthCondition(healthCondition.toUpperCase()), MESSAGE_CONSTRAINTS);
        value = healthCondition.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid health condition.
     */
    public static boolean isValidHealthCondition(String test) {
        return Objects.equals(test, Priority.LOW.name())
                || Objects.equals(test, Priority.MEDIUM.name())
                || Objects.equals(test, Priority.HIGH.name());
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

        // instanceof handles nulls
        if (!(other instanceof HealthCondition)) {
            return false;
        }

        HealthCondition otherHealthCondition = (HealthCondition) other;
        return value.equals(otherHealthCondition.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
