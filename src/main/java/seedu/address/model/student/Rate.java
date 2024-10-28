package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's tuition fee rate in the address book.
 */
public class Rate extends Fee {
    public static final double MAX_VALUE = 1000.00;

    public static final String MESSAGE_CONSTRAINTS = "Rate "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is between the range of 0.01 to " + MAX_VALUE;


    /**
    * Constructs a {@code Rate}.
    *
    * @param rate A valid rate.
    */
    public Rate(String rate) {
        super(rate);
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        if (!Fee.isValidFee(test)) {
            return false;
        }

        double rate = Double.parseDouble(test);
        return rate > 0 && rate <= MAX_VALUE;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rate)) {
            return false;
        }

        Rate otherRate = (Rate) other;
        return value == otherRate.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, Rate.class);
    }
}
