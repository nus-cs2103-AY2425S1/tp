package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_CONSTRAINTS = "Invalid email format. "
            + "Please ensure your email includes a valid domain "
            + "(e.g., name@example.com)";
    private static final String SPECIAL_CHARACTERS = "+_.-";
    private static final String ALPHANUMERIC = "[a-zA-Z0-9]";
    private static final String LOCAL_PART_REGEX = ALPHANUMERIC
            + "([a-zA-Z0-9" + SPECIAL_CHARACTERS + "]*[a-zA-Z0-9])?";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC + "([a-zA-Z0-9-]*[a-zA-Z0-9])?";
    private static final String DOMAIN_REGEX = DOMAIN_PART_REGEX + "(\\." + DOMAIN_PART_REGEX + ")*\\.[a-zA-Z]{2,}$";
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        checkArgument(isValidEmail(trimmedEmail), MESSAGE_CONSTRAINTS);
        value = trimmedEmail;
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
        return this.value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
