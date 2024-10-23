package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's register value in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegisterNumber(String)}
 */
public class RegisterNumber implements Comparable<RegisterNumber> {

    public static final String MESSAGE_CONSTRAINTS =
            "Register value should be a value between 1 and 40.";

    public static final String VALIDATION_REGEX = "^(?:[1-9]|[1-3][0-9]|40)$";

    public final String value;

    /**
     * Constructs a {@code RegisterNumber}.
     *
     * @param registerNumber A valid register value.
     */
    public RegisterNumber(String registerNumber) {
        requireNonNull(registerNumber);
        checkArgument(isValidRegisterNumber(registerNumber), MESSAGE_CONSTRAINTS);
        value = registerNumber;
    }

    /**
     * Returns true if a given string is a valid register value.
     */
    public static boolean isValidRegisterNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the integer representation of RegisterNumber
     */
    public Integer toInt() {
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
        if (!(other instanceof RegisterNumber)) {
            return false;
        }

        RegisterNumber otherNumber = (RegisterNumber) other;
        return value.equals(otherNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(RegisterNumber r) {
        return this.toInt().compareTo(r.toInt());
    }

}
