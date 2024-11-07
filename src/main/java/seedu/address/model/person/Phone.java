package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Invalid phone number: only numeric characters, hyphens '-' and optional leading '+' are allowed.";

    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "Phone number must be between 3 and 15 digits inclusive.";

    public static final String MESSAGE_LENGTH_CONSTRAINTS_FIND =
            "Phone number must be between 1 and 15 digits inclusive.";

    public static final String VALIDATION_REGEX = "(\\+)?[0-9\\-\\s]+";

    public static final Comparator<Phone> PHONE_COMPARATOR = Comparator
            .comparing(phone -> phone.value);

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        phone = phone.trim(); // Trim whitespace from the start and end
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLengthPhone(phone), MESSAGE_LENGTH_CONSTRAINTS);
        // Trim hyphens from the phone number
        value = phone.replaceAll("[\\s-]", "").replaceFirst("^\\+", ""); // Remove spaces, hyphens, and leading '+'
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {

        return test.matches(VALIDATION_REGEX) && !test.replaceAll("[^0-9]", "").isEmpty();
    }

    /**
     * Returns true if a given string has a valid length for phone numbers.
     */
    public static boolean isValidLengthPhone(String test) {
        // Ensure length is between 3 and 15 digits (excluding leading '+')
        // Remove '+' and hyphens for length check
        String digitsOnly = test.replaceAll("[\\s-]", "").replaceAll("\\+", "");
        return digitsOnly.length() >= 3 && digitsOnly.length() <= 15;
    }

    /**
     * Returns true if a given string has a valid length for phone numbers in find feature.
     */
    public static boolean isValidLengthPhoneFind(String test) {
        // Ensure length is between 1 and 15 digits (excluding leading '+')
        // Remove '+' and hyphens for length check
        String digitsOnly = test.replaceAll("[\\s-]", "").replaceAll("\\+", "");
        return digitsOnly.length() >= 1 && digitsOnly.length() <= 15;
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
