package seedu.address.model.car;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Car's Vehicle Registration Number (Vrn) in MATER.
 * Guarantees: immutable; is valid as declared in {@link #isValidVrn(String)}
 */
public class Vrn {

    public static final String MESSAGE_CONSTRAINTS = "Vehicle Registration Number should be a valid Singapore"
            + " Car Plate of the format XXX####Y and adhere to the following constraints:\n"
            + "1. Prefix XXX\n"
            + "      XXX: Alphabetical Series, Capitalised (A-Z, except I and O).\n"
            + "2. Number ####\n"
            + "      ####: Number Series (0-9999, without leading zeros)\n"
            + "3. Suffix Y\n"
            + "      Y: Checksum Letter, Capitalised (A-Z, except F, I, N, O, Q, V and W)";
    public static final String VALIDATION_REGEX = "([A-HJ-NP-Z]{1,3})([1-9]\\d{0,3})([A-EGHJ-MPR-UX-Z])$";

    public final String vrn;

    /**
     * Constructs an {@code Vrn}.
     *
     * @param vrn A valid vrn.
     */
    public Vrn(String vrn) {
        requireNonNull(vrn);
        checkArgument(isValidVrn(vrn), MESSAGE_CONSTRAINTS);
        this.vrn = vrn;
    }

    /**
     * Returns if a given string is a valid Vrn. Check using Checksum.
     * (Source: https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Singapore)
     */
    public static boolean isValidVrn(String test) {
        Pattern pattern = Pattern.compile(VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(test);
        if (!matcher.matches()) {
            return false;
        }

        int[] fixedNumbers = {9, 4, 5, 4, 3, 2};
        String prefix = matcher.group(1);
        String numberStr = matcher.group(2);
        int number = Integer.parseInt(numberStr);
        String suffix = matcher.group(3);

        checksumParsePrefix(prefix, fixedNumbers);
        checksumParseNumber(number, fixedNumbers);
        char checksumSuffix = checksumCalculateSuffix(fixedNumbers);
        return checksumSuffix == suffix.charAt(0);
    }

    private static void checksumParsePrefix(String prefix, int[] fixedNumbers) {
        // Convert letters into int (A = 1, B = 2, ...)
        if (prefix.length() == 1) {
            // First position is taken as 0.
            fixedNumbers[0] *= 0;
            fixedNumbers[1] *= prefix.charAt(0) - 64;
        } else {
            // Last two letters of the prefix are used in the checksum.
            fixedNumbers[0] *= prefix.charAt(prefix.length() - 2) - 64;
            fixedNumbers[1] *= prefix.charAt(prefix.length() - 1) - 64;
        }
    }

    private static void checksumParseNumber(int number, int[] fixedNumbers) {
        // If number is less than 4, lead with zeros. e.g. "1" -> "0001"
        for (int i = 5; i > 1; i--) {
            int digit = number % 10;
            fixedNumbers[i] *= digit;
            number /= 10;
        }
    }

    private static char checksumCalculateSuffix(int[] fixedNumbers) {
        char[] checksumLetters = {'A', 'Z', 'Y', 'X', 'U', 'T', 'S', 'R',
            'P', 'M', 'L', 'K', 'J', 'H', 'G', 'E', 'D', 'C', 'B'};
        int checksum = 0;
        for (int n : fixedNumbers) {
            checksum += n;
        }
        return checksumLetters[checksum % 19];
    }

    @Override
    public String toString() {
        return this.vrn;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vrn)) {
            return false;
        }

        Vrn otherVrn = (Vrn) other;
        return this.vrn.equals(otherVrn.vrn);
    }

    @Override
    public int hashCode() {
        return this.vrn.hashCode();
    }
}
