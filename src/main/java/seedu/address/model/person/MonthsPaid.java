package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Person's months paid in the address book.
 */
public class MonthsPaid {
    public static final String MESSAGE_CONSTRAINTS = "MonthsPaid should take a string in the format"
            + "YYYY-MM, and it should only be blank for a new student";

    public final String value;

    public MonthsPaid(String monthsPaid) {
        this.value = monthsPaid;
    }

    @Override
    public String toString() {
        return value;
    }

    public static boolean isValidMonthsPaid(String monthsPaid) {
        return monthsPaid.matches("^[0-9]{4}-[0-9]{2}$");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthsPaid otherMarkPaid)) {
            return false;
        }

        return Objects.equals(value, otherMarkPaid.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
