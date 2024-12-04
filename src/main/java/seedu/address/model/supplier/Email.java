package seedu.address.model.supplier;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Supplier's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    private static final String MESSAGE_CONSTRAINTS_GENERAL =
            "Email should follow the format local-part@domain. ";
    private static final String MESSAGE_CONSTRAINTS_LOCAL_PART =
            "The local-part should contain only alphanumeric characters and these special characters: "
                    + SPECIAL_CHARACTERS + ". It should not start or end with a special character.";
    private static final String MESSAGE_CONSTRAINTS_DOMAIN = "The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any";

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

    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
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
        checkArgument(isValidEmail(email), getDetailedErrorMessage(email));
        value = email;
    }

    /**
     * Returns a specific error message depending on which part of the email is invalid.
     */
    public static String getDetailedErrorMessage(String email) {
        boolean hasAtSymbol = email.contains("@");

        String detailedErrorMessage = MESSAGE_CONSTRAINTS_GENERAL;

        if (!hasAtSymbol) {
            detailedErrorMessage += " Missing '@' symbol.";
            return detailedErrorMessage;
        }

        String[] parts = email.split("@", 2);
        String localPart = parts[0];
        String domain = parts.length > 1 ? parts[1] : "";

        // Check if localPart or domain is empty
        if (localPart.isEmpty()) {
            detailedErrorMessage += "\nLocal part is missing.";
            return detailedErrorMessage;
        }
        if (domain.isEmpty()) {
            detailedErrorMessage += "\nDomain part is missing.";
            return detailedErrorMessage;
        }

        if (!localPart.matches(LOCAL_PART_REGEX)) {
            detailedErrorMessage += "\n" + MESSAGE_CONSTRAINTS_LOCAL_PART;
        }

        if (!domain.matches(DOMAIN_REGEX)) {
            detailedErrorMessage += "\n" + MESSAGE_CONSTRAINTS_DOMAIN;
        }

        return detailedErrorMessage;
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
