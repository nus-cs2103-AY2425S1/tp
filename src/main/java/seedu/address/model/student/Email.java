package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "._";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
        + "and adhere to the following constraints:\n"
        + "1. The local-part should follow NUS email requirements.\n"
        + "Only contain alphanumeric characters and these special characters, excluding "
        + "the parentheses, (" + SPECIAL_CHARACTERS + ").\n"
        + "Cannot end with special characters.\n"
        + "2. This is followed by a '@' and then the school domain name: 'u.nus.edu'";
    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
        + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_REGEX = "(?i)u\\.nus\\.edu";
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    private final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email.toLowerCase();
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns Email of Student.
     */
    public String getEmail() {
        return value;
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equalsIgnoreCase(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
