package seedu.address.model.person;

import java.util.List;

/**
 * Represents a PostalCode of a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PostalCode {
    public static final String MESSAGE_CONSTRAINTS = "Postal codes should be valid Singapore postal codes.\n"
            + "Postal code should only contain numbers and must be exactly 6 digits long.";
    public static final String VALIDATION_REGEX = "\\d{6}";
    private static final List<String> SINGAPORE_POSTAL_CODES = List.of(
            "01", "02", "03", "04", "05", "06",
            "07", "08",
            "14", "15", "16",
            "09", "10",
            "11", "12", "13",
            "17",
            "18", "19",
            "20", "21",
            "22", "23",
            "24", "25", "26", "27",
            "28", "29", "30",
            "31", "32", "33",
            "34", "35", "36", "37",
            "38", "39", "40", "41",
            "42", "43", "44", "45",
            "46", "47", "48",
            "49", "50", "81",
            "51", "52",
            "53", "54", "55", "82",
            "56", "57",
            "58", "59",
            "60", "61", "62", "63", "64",
            "65", "66", "67", "68",
            "69", "70", "71",
            "72", "73",
            "77", "78",
            "75", "76",
            "79", "80"
    );

    public final String value;

    /**
     * Constructs a {@code PostalCode}.
     *
     * @param postalCode A valid postal code.
     */
    public PostalCode(String postalCode) {
        this.value = postalCode;
    }

    /**
     * Returns true if a given string is a valid postal code.
     */
    public static boolean isValidPostalCode(String postalCode) {
        return postalCode.matches(VALIDATION_REGEX) && isValidSingaporePostalCode(postalCode);
    }

    /**
     * Returns true if a given string's first 2 digits of the 6-digit postal code is a valid Singapore postal code.
     */
    public static boolean isValidSingaporePostalCode(String postalCode) {
        boolean isPresent = false;
        String trimmedPostalCode = postalCode.substring(0, 2);
        for (String i: SINGAPORE_POSTAL_CODES) {
            if (trimmedPostalCode.equals(i)) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
