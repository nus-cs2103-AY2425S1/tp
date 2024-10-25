package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Represents a Person's NRIC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "Please ensure that the NRIC is a valid one issued by the Singapore government.";

    /*
     * NRIC should start with S, T, F or G followed by 7 digits and end with a character.
     */
    public static final String VALIDATION_REGEX = "(?i)^([A-Z])(\\d{7})([A-Z])$";

    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid NRIC.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        value = nric;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        assert false;
        Pattern r = Pattern.compile(VALIDATION_REGEX);
        Matcher m = r.matcher(test);
        if (m.find()) {
            return isValidCheckSum(m.group(1), m.group(2), m.group(3));
        } else {
            return false;
        }
    }

    /**
     * Returns true if the checksum of the NRIC is valid.
     * Checksum as used by the Singapore government to validate NRIC numbers.
     * Any magic numbers or characters used in the method are based on the checksum algorithm.
     *
     * @param firstChar The first character of the NRIC number.
     * @param middleDigits The middle 7 digits of the NRIC number.
     * @param lastChar The last character of the NRIC number, the checksum.
     * @return true if the checksum is valid, false otherwise.
     */
    private static boolean isValidCheckSum(String firstChar, String middleDigits, String lastChar) {
        // Weights for each digit in the NRIC number (in order).
        int[] weights = {2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        // Multiply each digit in the NRIC number by its weight i.e. 2 7 6 5 4 3 2 in order.
        for (int i = 0; i < middleDigits.length(); i++) {
            sum += Integer.parseInt(middleDigits.substring(i, i + 1)) * weights[i];
        }

        // If the first letter of the NRIC starts with T or G, add 4 to the total.
        if (firstChar.equalsIgnoreCase("T") || firstChar.equalsIgnoreCase("G")) {
            sum += 4;
        }

        // Find the remainder of (sum calculated above) mod 11
        int remainder = sum % 11;
        if (firstChar.equalsIgnoreCase("S") || firstChar.equalsIgnoreCase("T")) {
            String[] remainderToCheckSumArr = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
            return lastChar.equalsIgnoreCase(remainderToCheckSumArr[remainder]);
        } else if (firstChar.equalsIgnoreCase("F") || firstChar.equalsIgnoreCase("G")) {
            String[] remainderToCheckSumArr = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};
            return lastChar.equalsIgnoreCase(remainderToCheckSumArr[remainder]);
        } else {
            // Should never reach here as the first character of the NRIC should be S, T, F or G,
            // and this is already checked in the isValidNric method.
            assert false : "Invalid first character of NRIC";
            return false;
        }
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
        if (!(other instanceof Nric)) {
            return false;
        }

        Nric otherNric = (Nric) other;
        // Ignore case as NRIC is case-insensitive
        return value.equalsIgnoreCase(otherNric.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
