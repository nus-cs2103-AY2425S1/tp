package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's emergency phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEcNumber(String)}
 */
public class EcNumber implements Comparable<EcNumber> {

    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact numbers should contain numbers that are 8 digits long\n"
            + "and it can be blank if no contact number is provided.";
    public static final String VALIDATION_REGEX = "\\d{8}";
    public final String value;

    /**
     * Constructs a {@code ECNumber}.
     *
     * @param ecNumber A valid emergency phone number.
     */
    public EcNumber(String ecNumber) {
        requireNonNull(ecNumber);
        checkArgument(isValidEcNumber(ecNumber), MESSAGE_CONSTRAINTS);
        value = ecNumber;
    }

    /**
     * Returns true if a given string is a valid emergency phone number or empty string to delete.
     */
    public static boolean isValidEcNumber(String number) {
        return number != null && (number.matches(VALIDATION_REGEX) || number.isEmpty());
    }

    /**
     * Returns the integer representation of EcNumber
     */
    public Integer toInt() {
        if (value.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        return Integer.valueOf(this.value);
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
        if (!(other instanceof EcNumber)) {
            return false;
        }

        EcNumber otherNumber = (EcNumber) other;
        return value.equals(otherNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(EcNumber e) {
        return this.toInt().compareTo(e.toInt());
    }

}
