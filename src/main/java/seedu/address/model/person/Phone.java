package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers must have exactly 8 numbers, and no letters or symbols. \n"
            + "They can only begin with 6, 8, or 9.";

    /**
     * This validation regex checks that the first character in the phone number is either a 6, 8, or 9.
     * Since {@code SocialBook} is meant only for use in Singapore, restricting it to the
     * possible phone numbers helps users catch possible typos.
     * After the first character, there must be 3 more numbers,
     * followed by any number of whitespaces before 4 more numbers.
     * This comes out to 8 numbers in total, with any number of whitespaces separating the phone
     * number into 2 halves of 4 numbers each. Some software displays phone numbers
     * this way, so we want to support parsing of this phone number format too.
     */
    public static final String VALIDATION_REGEX = "[689]\\d{3}\\s*\\d{4}";
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
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
