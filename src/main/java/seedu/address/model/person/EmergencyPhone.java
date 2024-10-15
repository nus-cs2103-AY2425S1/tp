package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's emergency phone number in the address book.
 * Guarantees: immutable; is always valid
 */
public class EmergencyPhone {

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public EmergencyPhone(String phone) {
        requireNonNull(phone);
        value = phone;
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
