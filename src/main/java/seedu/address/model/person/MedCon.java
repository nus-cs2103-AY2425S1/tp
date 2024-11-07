package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_ALPHANUMERIC;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;

/**
 * Represents a patient's medical condition in MediBase3.
 * Guarantees: immutable; medical condition name is validated to be non-empty, alphanumeric,
 * and does not exceed 30 characters.
 */
public class MedCon implements Comparable<MedCon> {

    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s\\p{Alnum}+)*";
    public final String medConName;

    /**
     * Constructs a {@code MedCon} object with the specified medical condition string.
     *
     * @param medConName The medical condition of the patient
     */
    public MedCon(String medConName) {
        requireNonNull(medConName);
        checkArgument(!medConName.isEmpty(), MESSAGE_EMPTY_FIELD);
        checkArgument(isAlphanumeric(medConName), MESSAGE_CONSTRAINTS_ALPHANUMERIC);
        checkArgument(isValidLength(medConName), MESSAGE_CONSTRAINTS_LENGTH);
        this.medConName = medConName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid medical condition name.
     *
     * @param medCon the string to be validated
     * @return true if the string passes both alphanumeric and length validation.
     */
    public static boolean isValidMedConName(String medCon) {
        return isAlphanumeric(medCon) && isValidLength(medCon);
    }

    /**
     * Returns true if the given string is alphanumeric.
     *
     * @param medCon the string to be validated
     * @return true if the string matches the alphanumeric regex.
     */
    public static boolean isAlphanumeric(String medCon) {
        if (medCon == null || medCon.isEmpty()) {
            return false;
        }
        return medCon.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is of valid length.
     *
     * @param medCon the string to be validated
     * @return true if the string does not exceed 30 characters.
     */
    public static boolean isValidLength(String medCon) {
        if (medCon == null) {
            return false;
        }
        return medCon.length() <= 30;
    }

    /**
     * Returns the medical condition of the patient.
     *
     * @return The medical condition of the patient
     */
    public String getMedCon() {
        return medConName;
    }

    @Override
    public String toString() {
        return medConName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedCon // instanceof handles nulls
                && medConName.equals(((MedCon) other).medConName)); // state check
    }

    @Override
    public int hashCode() {
        return medConName.hashCode();
    }

    /**
     * Compares this {@code MedCon} object with the specified {@code MedCon} object for order.
     * Returns a negative integer, zero, or a positive integer if this {@code MedCon}
     * is lexicographically less than, equal to, or greater than the specified {@code MedCon}.
     *
     * @param other the {@code MedCon} object to be compared.
     * @return a negative integer, zero, or a positive integer as this {@code MedCon}
     *         is less than, equal to, or greater than the specified {@code MedCon}.
     */
    @Override
    public int compareTo(MedCon other) {
        return this.medConName.compareTo(other.medConName);
    }
}
