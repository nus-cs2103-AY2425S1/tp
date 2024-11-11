package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should follow the format: "
            + "local-part@domain and adhere to the following constraints:\n"
            + "\t1. The local-part should only contain alphanumeric characters "
            + "and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters and must not contain consecutive special characters.\n"
            + "\t2. This is followed by an @ and then a domain.\n"
            + "\t3. The domain must:\n"
            + "\t\t- Consist of one or more domain labels separated by single periods.\n"
            + "\t\t\t- Each domain label must start and end with alphanumeric characters. \n"
            + "\t\t\t- The final label is the top-level domain (TLD), which must be at least "
            + "two alphabetic characters long.\n"
            + "\t4. Examples:\n"
            + "\t\t- Valid: example@gmail.com, user.name@sub-domain.example.com, example@u.nus.edu\n"
            + "\t\t- Invalid: user..name@example.com (consecutive dots in the local-part), "
            + "user.@example.com (local-part ends with a special character), "
            + "user@-example.com (domain label starts with a hyphen).";

    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "[a-zA-Z]{2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+" + DOMAIN_LAST_PART_REGEX;
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

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
        return Objects.equals(value, otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
