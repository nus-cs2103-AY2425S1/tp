package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's past health record in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHealthRecord(String)}
 */
public class HealthRecord {
    public static final String MESSAGE_CONSTRAINTS = "Past health record can take any values";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code HealthRecords}.
     *
     * @param healthRecords Valid past health record.
     */
    public HealthRecord(String healthRecords) {
        requireNonNull(healthRecords);
        checkArgument(isValidHealthRecord(healthRecords), MESSAGE_CONSTRAINTS);
        value = healthRecords;
    }

    /**
     * Returns true if a given string is a valid past health record.
     */
    public static boolean isValidHealthRecord(String test) {
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
        if (!(other instanceof HealthRecord)) {
            return false;
        }

        HealthRecord otherHealthRecords = (HealthRecord) other;
        return value.equals(otherHealthRecords.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
