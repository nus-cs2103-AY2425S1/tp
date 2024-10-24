package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Identification Number (or NRIC) in the address book.
 */
public class IdentityNumber {

    public static final int IDENTITY_NUMBER_LENGTH = 9;
    public static final String IDENTITY_NUMBER_PREFIX_REGEX = "^[STFG].*";
    public static final String IDENTITY_NUMBER_PREFIX_OFFSET_REGEX = "^[GT].*";
    public static final String IDENTITY_NUMBER_FIN_PREFIX_REGEX = "^[FG].*";
    public static final String IDENTITY_NUMBER_NRIC_PREFIX_REGEX = "^[ST].*";
    public static final int IDENTITY_NUMBER_REMAINDER_CONSTANT = 11;
    public static final int[] IDENTITY_NUMBER_WEIGHT = {2, 7, 6, 5, 4, 3, 2};
    public static final char[] IDENTITY_NUMBER_NRIC_SUFFIX = {'J', 'Z', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
    public static final char[] IDENTITY_NUMBER_FIN_SUFFIX = {'X' , 'W', 'U', 'T', 'R', 'Q', 'P', 'N', 'M', 'L', 'K'};

    public static final String MESSAGE_CONSTRAINTS =
            "NRIC must be 9 characters long, starting with 'S', 'T', 'F', or 'G', followed by 7 digits,"
            + "and ending with a checksum letter (e.g., S1234567D).";

    public final String identificationNumber;

    /**
     * Constructs a {@code IdentityNumber}
     *
     * @param identificationNumber a valid and unique Identification Number of the patient.
     */
    public IdentityNumber(String identificationNumber) {
        identificationNumber = identificationNumber.toUpperCase();
        requireNonNull(identificationNumber);
        checkArgument(isValidIdentityNumber(identificationNumber), MESSAGE_CONSTRAINTS);
        this.identificationNumber = identificationNumber;
    }

    /**
     * TODO: Stronger Validations for IdentityNumber (Check if 2nd to 7th characters are numbers, etc)
     * Returns true if a given string is a valid identification number.
     */
    public static boolean isValidIdentityNumber(String test) {
        if (test.length() != IDENTITY_NUMBER_LENGTH) {
            return false;
        }
        boolean isCheckSumValid = isValidCheckSumIdentityNumber(test);
        boolean isStructureValid = isValidStructureIdentityNumber(test);
        return isCheckSumValid && isStructureValid;
    }

    /**
     * Returns true if the checksum of the NRIC/FIN is valid.
     * Note that this validates if the NRIC/FIN is valid, but does not guarantee that
     * it belongs to a valid person. Also, given that the actual algorithm for computing the checksum
     * of the NRIC/FIN is not officially published, this method is more of a guideline for obvious outliers.
     */
    public static boolean isValidCheckSumIdentityNumber(String test) {
        String identityNumber = test.substring(1, 8);
        int checkSum = 0;
        for (int i = 0; i < 7; i++) {
            int currNumber = identityNumber.charAt(i) - '0';
            checkSum += currNumber * IDENTITY_NUMBER_WEIGHT[i];
        }

        if (test.matches(IDENTITY_NUMBER_PREFIX_OFFSET_REGEX)) {
            checkSum += 4;
        }

        int remainder = checkSum % IDENTITY_NUMBER_REMAINDER_CONSTANT;

        if (test.matches(IDENTITY_NUMBER_NRIC_PREFIX_REGEX)) {
            return IDENTITY_NUMBER_NRIC_SUFFIX[remainder] == test.charAt(8);
        }

        if (test.matches(IDENTITY_NUMBER_FIN_PREFIX_REGEX)) {
            return IDENTITY_NUMBER_FIN_SUFFIX[remainder] == test.charAt(8);
        }

        return false;
    }

    /**
     * Returns true if the structure of the NRIC/FIN is valid.
     * Note that this validates if the NRIC/FIN is valid, but does not guarantee that
     * it belongs to a valid person.
     */
    public static boolean isValidStructureIdentityNumber(String test) {
        return test.matches(IDENTITY_NUMBER_PREFIX_REGEX);
    }

    @Override
    public String toString() {
        return identificationNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IdentityNumber)) {
            return false;
        }

        IdentityNumber otherIdentificationNumber = (IdentityNumber) other;
        return this.identificationNumber.equals(otherIdentificationNumber.identificationNumber);
    }

    @Override
    public int hashCode() {
        return identificationNumber.hashCode();
    }
}
