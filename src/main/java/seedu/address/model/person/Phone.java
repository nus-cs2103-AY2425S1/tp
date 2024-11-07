package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "The `PHONE_NUMBER` field is defined as a string where, if split by spaces,"
            + " at least one of the resulting tokens is a valid phone number, which is a"
            + " string without spaces that has at least 3 digits, and no alphabets.";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhoneField(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     * Our definition of a valid phone number is a string without spaces that has
     * at least 3 digits, and no alphabets.
     */
    static boolean isValidPhone(String test) {
        // Assert that there should not be whitespaces in the string
        assert test.chars().noneMatch(ch -> ch == ' ') : "Phone number itself should not contain space";

        long digitCount = test.chars()
            .filter(Character::isDigit)
            .count();

        long alphaCount = test.chars()
            .filter(Character::isAlphabetic)
            .count();

        return digitCount >= 3 && alphaCount == 0;
    }

    /**
     * Returns true if a given string is a valid `PHONE_NUMBER` field.
     * The `PHONE_NUMBER` field is defined as a string where, if split by spaces,
     * at least one of the resulting tokens is a valid phone number, as defined in isValidPhone.
     */
    public static boolean isValidPhoneField(String test) {
        return Arrays.stream(test.split("\\s+"))
                     .anyMatch(Phone::isValidPhone);
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
