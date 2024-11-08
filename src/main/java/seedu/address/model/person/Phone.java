package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should be in the format: (+XXX) 123 456 789 [notes]\n"
                    + "where:\n"
                    + "- Country code (+XXX) is optional and X must be 1-3 digits\n"
                    + "- Main phone number must contain digits with optional single spaces between numbers\n"
                    + "- [notes] is optional and must be maximum 10 characters\n"
                    + "Note: Special characters and letters in the phone number section will be automatically removed";

    // Regex specifically for validating country code format if present
    private static final String COUNTRY_CODE_REGEX = "^\\(\\+\\d{1,3}\\).*$";

    // Regex specifically for validating notes format if present
    private static final String NOTES_REGEX = ".*\\[.{1,10}\\]$";

    // Regex for validating the overall structure without notes validation
    private static final String MAIN_STRUCTURE_REGEX =
            "^(?:\\(\\+\\d{1,3}\\)\\s)?[\\d\\s\\W\\w]+$";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = normalizePhoneNumber(phone);
    }

    /**
     * Normalizes the phone number by:
     * 1. Preserving valid country code if present
     * 2. Removing all non-numeric characters from the main phone number section
     * 3. Normalizing spaces between digits (multiple spaces become single space)
     * 4. Preserving the notes section if present
     */
    private String normalizePhoneNumber(String phone) {
        // Split the input into its components
        String countryCode = "";
        String notes = "";
        String mainNumber = phone;

        // Extract country code if present
        if (phone.startsWith("(+")) {
            int closeParen = phone.indexOf(")");
            countryCode = phone.substring(0, closeParen + 1);
            mainNumber = phone.substring(closeParen + 1).trim();
        }

        // Extract notes if present
        if (mainNumber.contains("[")) {
            int openBracket = mainNumber.lastIndexOf("[");
            notes = mainNumber.substring(openBracket);
            mainNumber = mainNumber.substring(0, openBracket).trim();
        }

        // Process main number:
        // 1. Remove all non-numeric characters
        // 2. Split by digits to preserve spacing
        StringBuilder normalizedNumber = new StringBuilder();
        String[] numberGroups = mainNumber.split("\\s+");

        for (int i = 0; i < numberGroups.length; i++) {
            // Remove all non-numeric characters from each group
            String digits = numberGroups[i].replaceAll("[^0-9]", "");
            if (!digits.isEmpty()) {
                if (i > 0 && !normalizedNumber.isEmpty()) {
                    normalizedNumber.append(" ");
                }
                normalizedNumber.append(digits);
            }
        }

        // Combine all parts
        StringBuilder result = new StringBuilder();
        if (!countryCode.isEmpty()) {
            result.append(countryCode).append(" ");
        }
        result.append(normalizedNumber);
        if (!notes.isEmpty()) {
            result.append(" ").append(notes);
        }

        return result.toString();
    }

    /**
     * Returns true if a given string is a valid phone number.
     * Validates both the format and content after normalization.
     */
    public static boolean isValidPhone(String test) {
        // First check if there's a country code and if it's valid
        if (test.startsWith("(+") && !test.matches(COUNTRY_CODE_REGEX)) {
            return false;
        }

        // Check if there are notes and if they're valid
        if (test.contains("[")) {
            if (!test.matches(NOTES_REGEX)) {
                return false;
            }
            // Remove notes for main structure validation
            test = test.substring(0, test.lastIndexOf("[")).trim();
        }

        // Check main structure (without notes)
        if (!test.matches(MAIN_STRUCTURE_REGEX)) {
            return false;
        }

        // Validate the normalized form to ensure we have digits after stripping
        String normalized = normalizeForValidation(test);
        return !normalized.isEmpty();
    }

    /**
     * Helper method to normalize a phone number for validation purposes.
     * Similar to normalizePhoneNumber but only returns the numeric part for validation.
     */
    private static String normalizeForValidation(String phone) {
        String mainNumber = phone;

        // Remove country code if present
        if (phone.startsWith("(+")) {
            int closeParen = phone.indexOf(")");
            mainNumber = phone.substring(closeParen + 1).trim();
        }

        // Remove notes if present
        if (mainNumber.contains("[")) {
            int openBracket = mainNumber.lastIndexOf("[");
            mainNumber = mainNumber.substring(0, openBracket).trim();
        }

        // Return only the digits
        return mainNumber.replaceAll("[^0-9]", "");
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
