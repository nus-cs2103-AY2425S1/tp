package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_ALPHANUMERIC;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;

/**
 * Represents an Allergy in MediBase3.
 * Guarantees: immutable; allergy name is validated to be non-empty, alphanumeric, and does not exceed 30 characters.
 */
public class Allergy implements Comparable<Allergy> {

    public static final String MESSAGE_CONSTRAINTS = "Allergy names should be alphanumeric and not "
            + "exceed 30 characters";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s\\p{Alnum}+)*";

    public final String allergyName;

    /**
     * Constructs a {@code Allergy} object with the specified allergy string.
     *
     * @param allergyName A valid allergy name.
     */
    public Allergy(String allergyName) {
        requireNonNull(allergyName);
        checkArgument(!allergyName.isEmpty(), MESSAGE_EMPTY_FIELD);
        checkArgument(isAlphanumeric(allergyName), MESSAGE_CONSTRAINTS_ALPHANUMERIC);
        checkArgument(isValidLength(allergyName), MESSAGE_CONSTRAINTS_LENGTH);
        this.allergyName = allergyName.toUpperCase();
    }

    /**
     * Returns true if a given string contains only alphanumeric characters.
     *
     * @param allergy The string to be validated.
     * @return true if the string contains only alphanumeric characters.
     */
    public static boolean isAlphanumeric(String allergy) {
        return allergy != null && allergy.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string does not exceed the maximum length.
     *
     * @param allergy The string to be validated.
     * @return true if the string length is less than or equal to 30.
     */
    public static boolean isValidLength(String allergy) {
        return allergy != null && allergy.length() <= 30;
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
     * Returns the allergy of the patient.
     *
     * @return The allergy of the patient
     */
    public String getAllergy() {
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
