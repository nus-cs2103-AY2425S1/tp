package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Patient's health risk in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHealthRisk (String)}
 */
public class HealthRisk {
    public static final String MESSAGE_CONSTRAINTS = "Health condition should be categorized as either"
            + " 'HIGH', 'MEDIUM', or 'LOW.'";

    private enum Priority { HIGH, MEDIUM, LOW };

    public final String value;

    /**
     * Constructs an {@code HealthRisk}.
     *
     * @param healthRisk A valid health condition (LOW, MEDIUM , HIGH).
     */
    public HealthRisk(String healthRisk) {
        requireNonNull(healthRisk);
        checkArgument(isValidHealthRisk(healthRisk.toUpperCase()), MESSAGE_CONSTRAINTS);
        value = healthRisk.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid health condition.
     */
    public static boolean isValidHealthRisk(String test) {
        requireNonNull(test);
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
        if (!(other instanceof HealthRisk)) {
            return false;
        }

        HealthRisk otherHealthRisk = (HealthRisk) other;
        return value.equals(otherHealthRisk.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
