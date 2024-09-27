package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's fees in the address book.
 */
public class Fees {
    public static final String MESSAGE_CONSTRAINTS = "Fees can take any positive values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final int value;
    /**
     * Constructs a {@code Fees}.
     * @param fees
     */
    public Fees(int fees) {
        checkArgument(isValidFee(fees), MESSAGE_CONSTRAINTS);
        value = fees;
    }

    public static boolean isValidFees(int fees) {
        return fees > 0;
    }

    public int getFees() {
        return value;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidFee(int fee) {
        return fee > 0;
    }

    @Override
    public String toString() {
        String fees = Integer.toString(this.value);
        return fees;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Fees otherFees = (Fees) other;
        return this.value == otherFees.value;
    }

    public static Fees parseFees(String fees) {
        return new Fees(Integer.parseInt(fees));
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
