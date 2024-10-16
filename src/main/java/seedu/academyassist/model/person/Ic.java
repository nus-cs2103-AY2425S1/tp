package seedu.academyassist.model.person;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.commons.util.AppUtil;

/**
 * Represents a Person's IC number in the management system.
 */
public class Ic {

    public static final String MESSAGE_CONSTRAINTS =
            "IC should only contain alphanumeric characters and spaces, "
                    + "and should follow the format of Singaporean IC and FIN numbers (e.g., S1234567A)";
    public static final String VALIDATION_REGEX = "^[STFGM]\\d{7}[A-Z]$";
    public final String value;

    /**
     * Constructs a {@code Class}.
     *
     * @param ic A valid class.
     */
    public Ic(String ic) {
        requireNonNull(ic);
        AppUtil.checkArgument(isValidIc(ic), MESSAGE_CONSTRAINTS);
        this.value = ic;
    }

    /**
     * Returns true if a given string is a valid IC number.
     */
    public static boolean isValidIc(String test) {
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
        if (!(other instanceof Ic)) {
            return false;
        }

        Ic otherIc = (Ic) other;
        return value.equals(otherIc.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
