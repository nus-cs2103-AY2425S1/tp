package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Identification Number (or NRIC) in the address book.
 */
public class IdentityNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Message Constraints not yet Implemented";

    public static final String VALIDATION_REGEX = "Validation Regex Not Yet Implemented";

    public final String identificationNumber;

    /**
     * Constructs a {@code IdentityNumber}
     *
     * @param identificationNumber a valid and unique Identification Number of the patient.
     */
    public IdentityNumber(String identificationNumber) {
        requireNonNull(identificationNumber);
        checkArgument(isValidIdentityNumber(identificationNumber), MESSAGE_CONSTRAINTS);
        this.identificationNumber = identificationNumber;
    }

    /**
     * TODO
     * Returns true if a given string is a valid identification number.
     */
    public static boolean isValidIdentityNumber(String test) {
        boolean isCheckSumValid = isValidCheckSumIdentityNumber(test);
        boolean isStructureValid = isValidStructureIdentityNumber(test);
        return isCheckSumValid && isStructureValid;
    }

    /**
     * TODO
     * Returns true if the checksum of the NRIC/FIN is valid.
     * Note that this validates if the NRIC/FIN is valid, but does not guarantee that
     * it belongs to a valid person.
     */
    public static boolean isValidCheckSumIdentityNumber(String test) {
        return false;
    }

    /**
     * TODO
     * Returns true if the structure of the NRIC/FIN is valid.
     * Note that this validates if the NRIC/FIN is valid, but does not guarantee that
     * it belongs to a valid person.
     */
    public static boolean isValidStructureIdentityNumber(String test) {
        return false;
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
        if (!(other instanceof Name)) {
            return false;
        }

        IdentityNumber otherIdentificationNumber = (IdentityNumber) other;
        return identificationNumber.equals(otherIdentificationNumber.identificationNumber);
    }

    @Override
    public int hashCode() {
        return identificationNumber.hashCode();
    }
}
