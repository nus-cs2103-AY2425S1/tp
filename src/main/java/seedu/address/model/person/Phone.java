package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers can contain numbers, spaces and hyphens, and it should be at least 3 digits long.\n"
                    + "Only 1 space or hyphen is allowed between each digit.\n"
                    + "Phone number can start with not more than one '+' to indicate country code.";
    public static final String VALIDATION_REGEX = "\\+{0,1}(?=((\\D*\\d){3,}))\\d{1,}([ -]\\d{1,})*\\s*";
    public static final String PHONE_KEY = "phone";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.trim().equals(otherPhone.value.trim());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
