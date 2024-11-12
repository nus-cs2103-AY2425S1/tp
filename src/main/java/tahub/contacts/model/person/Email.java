package tahub.contacts.model.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_CONSTRAINTS = "Email must be a valid email format according "
            + "to the following rules:\n"
            + "https://help.xmatters.com/ondemand/trial/valid_email_format.htm";

    // Allowed characters in email prefix
    private static final String ALLOWED_PREFIX_CHARS = "a-zA-Z0-9._-";

    // Regex for the email prefix (local part)
    private static final String PREFIX_REGEX = "(?!^[._-])" // Does not start with '.', '_', or '-'
            + "(?!.*[._-]{2,})" // No consecutive '.', '_', or '-'
            + "(?!.*[._-]@)" // Does not end with '.', '_', or '-'
            + "[" + ALLOWED_PREFIX_CHARS + "]+"; // Contains allowed characters

    // Allowed characters in email domain
    private static final String ALLOWED_DOMAIN_CHARS = "a-zA-Z0-9-";

    // Regex for each domain part (labels)
    private static final String DOMAIN_PART_REGEX = "[" + ALLOWED_DOMAIN_CHARS + "]+";

    // Regex for the domain (including subdomains and TLD)
    private static final String DOMAIN_REGEX = "("
            + DOMAIN_PART_REGEX + "\\.)+" // One or more domain labels followed by '.'
            + "[a-zA-Z]{2,}$"; // TLD with at least two letters

    // Complete validation regex
    public static final String VALIDATION_REGEX = PREFIX_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
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
