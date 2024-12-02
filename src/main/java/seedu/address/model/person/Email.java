package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final Comparator<Email> EMAIL_COMPARATOR = Comparator
            .comparing(email -> email.value.toLowerCase());

    public static final String MESSAGE_CONSTRAINTS_FIND = "Email must be between 1 and 50 characters inclusive.";
    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails must follow the format: local-part@domain-name.tld "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters.\n"
            + "2. The domain part should consist of domain labels separated by periods (e.g., example.com).\n"
            + "   The domain name must:\n"
            + "    - Have each domain label consist of alphanumeric characters or hyphens,"
            + " with no leading or trailing hyphens.\n"
            + "    - End with a domain label that is at least 2 characters long.\n"
            + "    - Contain at least one period separating domain labels.\n"
            + "3. The top-level domain (TLD) must:\n"
            + "    - Be at least 2 characters long (e.g., .com, .org, .net).\n"
            + "    - Be composed of only alphabetic characters.\n"
            + "4. The email must not exceed 50 characters in length.";
    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "("
            + DOMAIN_PART_REGEX + "){1,}[a-zA-Z]{2,}$"; // At least two chars in the last part
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+"
            + "[a-zA-Z]{2,}$"; // At least two chars at the end
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;


    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        email = email.trim();
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        checkArgument(isValidLengthEmail(email), MESSAGE_CONSTRAINTS);

        // Split the email into local-part and domain-part
        String[] emailParts = email.split("@", 2); // Split into at most 2 parts
        String localPart = emailParts[0];
        String domainPart = emailParts[1].toLowerCase(); // Convert domain part to lower case

        // Reconstruct the email with lowercase domain
        value = localPart + "@" + domainPart;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a valid length for an email.
     */
    public static boolean isValidLengthEmail(String test) {
        return test.length() <= 50 && !test.isEmpty() && !test.contains(" ");
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
