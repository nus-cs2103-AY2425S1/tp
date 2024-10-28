package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's tuition fee rate in the address book.
 */
public class Rate extends Fee {

    public static final String MESSAGE_CONSTRAINTS = "Rate "
            + Fee.MESSAGE_CONSTRAINTS
            + "2. is strictly positive";

    /**
    * Constructs a {@code Rate}.
    *
    * @param rate A valid rate.
    */
    public Rate(String rate) {
        super(rate);
    }

    public static boolean isValidRate(String test) {
        return Fee.isValidFee(test) && Double.parseDouble(test) > 0;
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
