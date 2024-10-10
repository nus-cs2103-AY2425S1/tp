package seedu.address.model.person;

/**
 * Represents a UnitNumber of a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UnitNumber {

    public static final String MESSAGE_CONSTRAINTS = "Unit numbers should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\d+-\\d+";

    public final String value;

    /**
     * Constructs a {@code UnitNumber}.
     *
     * @param unitNumber A valid unit number.
     */
    public UnitNumber(String unitNumber) {
        this.value = unitNumber;
    }

    /**
     * Returns true if a given string is a valid unit number.
     */
    public static boolean isValidUnitNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnitNumber // instanceof handles nulls
                && value.equals(((UnitNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
