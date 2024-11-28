package seedu.address.model.person;

/**
 * Represents a UnitNumber of a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UnitNumber {

    public static final String MESSAGE_CONSTRAINTS = "Unit numbers should be alphanumeric. \n"
            + "The unit number should be in the format of 'XXX-XXX', where X is a digit.\n"
            + "The first and second part of the unit number should not be more than 3 digits long.";

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
        if (!test.contains("-")) {
            return false;
        }
        String[] split = test.split("-");
        for (String s : split) {
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    return false;
                }
            }
        }
        // The string in each array must be at max length 3
        if (split.length == 2) {
            return split[0].length() <= 3 && split[1].length() <= 3 && split[0].length() > 0 && split[1].length() > 0;
        } else {
            return false;
        }
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
