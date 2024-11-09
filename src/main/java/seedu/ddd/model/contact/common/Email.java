package seedu.ddd.model.contact.common;

import static java.util.Objects.requireNonNull;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents a {@code Contact}'s email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    private static final String DISALLOW_CONSECUTIVE_DOTS = "(?!.*\\.\\.)";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format LOCAL_PART@DOMAIN_NAME "
            + "and adhere to the following constraints:\n"
            + "1. The LOCAL_PART should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ").\n"
            + "The LOCAL_PART:\n"
            + "    - must not start or end with any special characters\n"
            + "    - must not contain consecutive periods (i.e. a..a)\n"
            + "    - may contain consecutive special characters other than periods (i.e. a__a is allowed).\n"
            + "2. This is followed by a '@' and then a DOMAIN_NAME. The DOMAIN_NAME is made up of domain labels "
            + "separated by periods.\n"
            + "The DOMAIN name:\n"
            + "    - must end with a domain label at least 2 characters long\n"
            + "    - must have each domain label start and end with alphanumeric characters\n"
            + "    - must have each domain label consist of alphanumeric characters, "
            + "separated only by periods or hyphens, if any.";
    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^"
            + ALPHANUMERIC_NO_UNDERSCORE
            + DISALLOW_CONSECUTIVE_DOTS
            + "([" + SPECIAL_CHARACTERS + "]*"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        AppUtil.checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
