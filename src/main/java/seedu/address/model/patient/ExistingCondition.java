package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's past existing condition in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExistingCondition(String)}
 */
public class ExistingCondition {
    public static final String MESSAGE_CONSTRAINTS = "Past existing condition can take any values";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code ExistingConditions}.
     *
     * @param existingConditions Valid past existing condition.
     */
    public ExistingCondition(String existingConditions) {
        requireNonNull(existingConditions);
        checkArgument(isValidExistingCondition(existingConditions), MESSAGE_CONSTRAINTS);
        value = existingConditions;
    }

    /**
     * Returns true if a given string is a valid past existing condition.
     */
    public static boolean isValidExistingCondition(String test) {
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
        if (!(other instanceof ExistingCondition)) {
            return false;
        }

        ExistingCondition otherExistingConditions = (ExistingCondition) other;
        return value.equals(otherExistingConditions.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
