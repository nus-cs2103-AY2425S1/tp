package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's email in the internship book.
 * Guarantees: immutable; the email is valid as declared in {@link #validate(String)}.
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters.\n"
            + "2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.";

    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    private final String value;

    /**
     * Constructs an {@code Email} with the specified email address.
     *
     * @param email A valid email address.
     * @throws NullPointerException if the {@code email} is null.
     * @throws IllegalArgumentException if the {@code email} does not satisfy the constraints.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(Email.validate(email), MESSAGE_CONSTRAINTS);
        this.value = email;
    }

    /**
     * Returns the string representation of this email.
     *
     * @return the email as a string.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a URI representation of the email in the format "mailto:email".
     *
     * @return the email URI.
     */
    public String toEmailUri() {
        return String.format("mailto:%s", this.value);
    }

    /**
     * Returns a string representation of the email.
     *
     * @return the email as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this email to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Email} and has the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    /**
     * Returns the hash code of the email.
     *
     * @return the hash code of the email value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Validates the given string as an email.
     *
     * @param test the string to be validated.
     * @return true if the string is a valid email, false otherwise.
     */
    public static boolean validate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
