package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's past health records in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHealthRecords(String)}
 */
public class HealthRecords {
    public static final String MESSAGE_CONSTRAINTS = "Past health records can take any values";
    public static final String VALIDATION_REGEX = "[^\\s]*";

    public final String value;

    /**
     * Constructs an {@code HealthRecords}.
     *
     * @param healthRecords Valid past health records.
     */
    public HealthRecords(String healthRecords) {
        checkArgument(isValidHealthRecords(healthRecords), MESSAGE_CONSTRAINTS);
        value = healthRecords;
    }

    /**
     * Returns true if a given string is a valid past health records.
     */
    public static boolean isValidHealthRecords(String test) {
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

        // instanceof handles nulls
        if (!(other instanceof HealthRecords)) {
            return false;
        }

        HealthRecords otherHealthRecords = (HealthRecords) other;
        return value.equals(otherHealthRecords.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
