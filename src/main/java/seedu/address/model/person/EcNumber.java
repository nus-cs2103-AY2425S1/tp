package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's emergency contact number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEcNumber(String)}
 */
public class EcNumber implements Comparable<EcNumber> {

    public static final String MESSAGE_GUI = "Emergency Contact Number: %1$s";
    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact numbers should only contain numbers, and should be at least 3 digits long\n"
            + "and it can be blank if no contact number is provided.";
    public static final String VALIDATION_REGEX = "\\d{3,}";

    public final String value;

    /**
     * Constructs a {@code EcNumber}.
     *
     * @param ecNumber A valid emergency phone number.
     */
    public EcNumber(String ecNumber) {
        requireNonNull(ecNumber);
        checkArgument(isValidEcNumber(ecNumber), MESSAGE_CONSTRAINTS);
        value = ecNumber;
    }

    /**
     * Returns true if a given string is a valid emergency contact number or empty string.
     */
    public static boolean isValidEcNumber(String number) {
        return number != null && (number.matches(VALIDATION_REGEX) || number.isEmpty());
    }

    /**
     * Returns the integer representation of EcNumber.
     */
    public Integer toInt() {
        if (value.isEmpty()) {
            // max value when empty so that it goes to the bottom when sorted
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

        EcNumber otherEcNumber = (EcNumber) other;
        return value.equals(otherEcNumber.value);
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
