package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Allergy in the system.
 * Guarantees: immutable; allergy name is valid as declared in {@link #isValidAllergy(String)}
 */
public class Allergy {
    public static final String MESSAGE_CONSTRAINTS = "ERROR: Invalid allergies format. "
            + "Allergies should be a non-empty alphanumeric string, and may include "
            + "commas, spaces, hyphens, or periods.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum},\\s.-]+";
    public static final String MESSAGE_FIELD_MISSING_FORMAT = "Person's Allergy field is missing!";
    public final String allergyName;

    /**
     * Constructs an {@code Allergy}.
     *
     * @param allergyName A valid allergy name.
     */
    public Allergy(String allergyName) {
        requireNonNull(allergyName);
        allergyName = allergyName.strip();
        checkArgument(isValidAllergy(allergyName), MESSAGE_CONSTRAINTS);
        this.allergyName = allergyName;
    }

    /**
     * Returns true if a given string is a valid allergy.
     */
    public static boolean isValidAllergy(String test) {
        return !test.isEmpty() && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Allergy)) {
            return false;
        }

        Allergy otherAllergy = (Allergy) other;
        return allergyName.equals(otherAllergy.allergyName);
    }

    @Override
    public int hashCode() {
        return allergyName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return allergyName;
    }
}


