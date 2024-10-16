package seedu.address.model.allergy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Allergy in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAllergyName(String)}
 */
public class Allergy implements Comparable<Allergy> {

    public static final String MESSAGE_CONSTRAINTS = "Allergy names should be alphanumeric and not "
            + "exceed 30 characters";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s\\p{Alnum}+)*";

    public final String allergyName;

    /**
     * Constructs a {@code Allergy}.
     *
     * @param allergyName A valid allergy name.
     */
    public Allergy(String allergyName) {
        requireNonNull(allergyName);
        checkArgument(isValidAllergyName(allergyName), MESSAGE_CONSTRAINTS);
        this.allergyName = allergyName;
    }

    /**
     * Returns true if a given string is a valid allergy name.
     */
    public static boolean isValidAllergyName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 30;
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

    /**
     * Compares this {@code Allergy} object with the specified {@code Allergy} object for order.
     * Returns a negative integer, zero, or a positive integer if this {@code Allergy}
     * is lexicographically less than, equal to, or greater than the specified {@code Allergy}.
     *
     * @param other the {@code Allergy} object to be compared.
     * @return a negative integer, zero, or a positive integer as this {@code Allergy}
     *         is less than, equal to, or greater than the specified {@code Allergy}.
     */
    @Override
    public int compareTo(Allergy other) {
        return this.allergyName.compareTo(other.allergyName);
    }
}
