package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {
    public static final String MESSAGE_CONSTRAINTS =
            "NUSNet ID should only contain alphanumeric characters, and it should be 8 characters long";
    public static final String DOMAIN = "@u.nus.edu";
    public static final String EMAIL_VALIDATION_REGEX = "[a-zA-Z]\\d{7}" + DOMAIN;
    public static final String NETID_VALIDATION_REGEX = "[a-zA-Z]\\d{7}";
    private static final Email EMPTY_EMAIL = new Email();

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    private Email(String email) {
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email.toLowerCase();
    }

    /**
     * Constructs an empty {@code Email}.
     *
     */
    private Email() {
        value = "";
    }

    /**
     * Constructs an {@code Email} or an empty {@code Email} instance.
     *
     * @param email A valid email address or an empty string
     */
    public static Email makeEmail(String email) {
        requireNonNull(email);

        if (email.isEmpty()) {
            return EMPTY_EMAIL;
        }

        return new Email(email);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a valid NUS Net Id.
     */
    public static boolean isValidNetId(String test) {
        return test.matches(NETID_VALIDATION_REGEX);
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
