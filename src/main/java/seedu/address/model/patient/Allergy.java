package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Patient's allergy in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidAllergy (String)}
 */
public class Allergy {
    public static final String MESSAGE_CONSTRAINTS = "Allergy can take any values";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    /**
     * Comparator to compare two allergies by their name.
     * Used for sorting allergies by alphabetical order.
     *
     * @param allergy1
     * @param allergy2
     * @return int
     * @see Comparator
     */
    public static final Comparator<Allergy> NAME_COMPARATOR = new Comparator<Allergy>() {
        @Override
        public int compare(Allergy allergy1, Allergy allergy2) {
            return allergy1.toString().compareTo(allergy2.toString());
        }
    };

    public final String value;

    /**
     * Constructs an {@code Allergies}.
     *
     * @param allergy A valid allergy.
     */
    public Allergy(String allergy) {
        requireNonNull(allergy);
        checkArgument(isValidAllergy(allergy), MESSAGE_CONSTRAINTS);
        value = allergy;
    }

    /**
     * Returns true if a given string is a valid allergies.
     */
    public static boolean isValidAllergy(String test) {
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
        if (!(other instanceof Allergy)) {
            return false;
        }

        Allergy otherAllergy = (Allergy) other;
        return value.equals(otherAllergy.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
