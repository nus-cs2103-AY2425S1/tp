package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's emergency phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmergencyPhone(String)}
 */
public class EmergencyPhone {

    public static final String MESSAGE_CONSTRAINTS =
            "Emergency numbers should contain numbers that are 8 digits long";
    public static final String VALIDATION_REGEX = "\\d{8}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public EmergencyPhone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidEmergencyPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidEmergencyPhone(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
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
        if (!(other instanceof EmergencyPhone)) {
            return false;
        }

        EmergencyPhone otherPhone = (EmergencyPhone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
