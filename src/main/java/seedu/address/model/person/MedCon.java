package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.Messages.MESSAGE_CONSTRAINTS_LENGTH;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIELD;

/**
 * Represents a patient's medical condition in the address book.
 * Guarantees: The priority is immutable and always valid.
 */
public class MedCon implements Comparable<MedCon> {

    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s\\p{Alnum}+)*";
    public final String value;

    /**
     * Constructs a {@code MedCon} object with the specified medical condition string.
     *
     * @param medCon The medical condition of the patient
     */
    public MedCon(String medCon) {
        requireNonNull(medCon);
        checkArgument(!medCon.isEmpty(), MESSAGE_EMPTY_FIELD);
        checkArgument(isValidMedCon(medCon), MESSAGE_CONSTRAINTS_LENGTH);
        value = medCon;
    }

    /**
     * Returns true if a given string is a valid medical condition name.
     *
     * @param medCon the string to be validated
     * @return true if the string is not empty and does not exceed 45 characters.
     */
    public static boolean isValidMedCon(String medCon) {
        if (medCon == null || medCon.isEmpty()) {
            return false;
        }
        return medCon.matches(VALIDATION_REGEX) && medCon.length() <= 30;
    }


    /**
     * Returns the medical condition of the patient.
     *
     * @return The medical condition of the patient
     */
    public String getMedCon() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedCon // instanceof handles nulls
                && value.equals(((MedCon) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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
        return this.value.compareTo(other.value);
    }
}
