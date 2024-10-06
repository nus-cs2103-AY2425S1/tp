package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's IC number in the address book.
 */
public class Ic {
    public static final String MESSAGE_CONSTRAINTS =
            "IcNumber should only contain alphanumeric characters and spaces, " +
                    "and should follow the format of Singaporean IC and FIN numbers (e.g., S1234567A)";

    public final String IcNumber;

    /**
     * Constructs a {@code Class}.
     *
     * @param IcNumber A valid class.
     */
    public Ic(String IcNumber) {
        requireNonNull(IcNumber);
        //      todo: checkArgument(isValidIcNumber(IcNumber), MESSAGE_CONSTRAINTS);
        this.IcNumber = IcNumber;
    }

    @Override
    public String toString() {
        return IcNumber;
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
        return IcNumber.equals(otherIc.IcNumber);
    }

    @Override
    public int hashCode() {
        return IcNumber.hashCode();
    }
}
