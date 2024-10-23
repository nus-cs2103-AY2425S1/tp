package seedu.address.model.owner;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Owner's Identification Card (IC) Number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIcNumber(String)}
 */
public class IdentificationCardNumber {

    public static final String MESSAGE_CONSTRAINTS = "Identification Card (IC) Number should only contain "
        + "alphanumerics and adhere to the constraints based on the format of the 9-character Singapore IC number."
        + " Letters should be upper case.";

    private static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$"; // STFG followed by 7 digits and 1 letter
    public final String value;

    /**
     * Constructs an {@code IdentificationCardNumber}.
     *
     * @param icNumber A valid identification card number.
     */
    public IdentificationCardNumber(String icNumber) {
        requireNonNull(icNumber);
        checkArgument(isValidIcNumber(icNumber), MESSAGE_CONSTRAINTS);
        value = icNumber.trim();
    }

    /**
     * Returns if a given string is a valid IC number.
     */
    public static boolean isValidIcNumber(String test) {
        requireNonNull(test);

        // Remove whitespaces
        test = test.trim();

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        char[] characters = test.toCharArray();

        int weight = 0;
        weight += Character.getNumericValue(characters[1]) * 2;
        weight += Character.getNumericValue(characters[2]) * 7;
        weight += Character.getNumericValue(characters[3]) * 6;
        weight += Character.getNumericValue(characters[4]) * 5;
        weight += Character.getNumericValue(characters[5]) * 4;
        weight += Character.getNumericValue(characters[6]) * 3;
        weight += Character.getNumericValue(characters[7]) * 2;

        if (characters[0] == 'T' || characters[0] == 'G') {
            weight += 4;
        }

        weight %= 11;

        String[] checksumForST = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
        String[] checksumForFG = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};

        String expectedChecksumLetter = null;
        if (characters[0] == 'S' || characters[0] == 'T') {
            expectedChecksumLetter = checksumForST[weight];
        } else if (characters[0] == 'F' || characters[0] == 'G') {
            expectedChecksumLetter = checksumForFG[weight];
        } else {
            return false; // Should not reach here
        }

        return String.valueOf(characters[8]).equals(expectedChecksumLetter);
    }

    /**
     * Returns the redacted version of the IC number.
     * Only first letter and last 4 digits shown with other values as X.
     *
     */
    public String getRedacted() {
        return value.charAt(0) + "XXXX" + value.substring(5);
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
        if (!(other instanceof IdentificationCardNumber)) {
            return false;
        }

        IdentificationCardNumber otherIcNumber = (IdentificationCardNumber) other;
        return value.equals(otherIcNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
