package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.commons.util.AppUtil.checkArgument;

import seedu.hireme.logic.validator.EmailValidator;

/**
 * Represents a Company's email in the internship book.
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

    private final String value;

    /**
     * Constructs an {@code Email} object with a valid email address.
     *
     * @param email A valid email address.
     * @throws NullPointerException if the email is null.
     * @throws IllegalArgumentException if the email does not satisfy the format constraints.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(EmailValidator.of().validate(email), MESSAGE_CONSTRAINTS);
        this.value = email.trim();
    }

    /**
     * Returns the value of the email.
     *
     * @return The email string.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a string representation of the email.
     *
     * @return The email string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this email to another object.
     *
     * @param other The object to compare.
     * @return True if the other object is an {@code Email} with the same value, false otherwise.
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
     * @return The hash code of the email value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
