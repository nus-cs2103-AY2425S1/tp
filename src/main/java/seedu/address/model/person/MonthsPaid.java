package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Person's months paid in the address book.
 */
public class MonthsPaid {
    public static final String MESSAGE_CONSTRAINTS = "MonthsPaid should take a string with the pattern"
            + " YYYY-MM, where one or of such patterns can be included if separated by a space.";
    public static final String SPLIT_VALIDATION_REGEX = "^[0-9]{4}-[0-9]{2}$";
    public final String value;

    public MonthsPaid(String monthsPaid) {
        this.value = monthsPaid;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Concatenates with another monthsPaid
     * @param otherMonthsPaid the monthsPaid to be concatenated with
     * @return the concatenated monthsPaid
     */
    public MonthsPaid concatenate(MonthsPaid otherMonthsPaid) {
        if (value.isEmpty()) {
            return otherMonthsPaid;
        } else {
            return new MonthsPaid(value + " " + otherMonthsPaid.value);
        }
    }

    /**
     * Returns true if a given string is a valid monthsPaid.
     */
    public static boolean isValidMonthsPaid(String monthsPaid) {
        if (monthsPaid.isEmpty()) {
            return true;
        }
        for (String i : monthsPaid.split(" ")) {
            if (!i.matches(SPLIT_VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthsPaid otherMonthsPaid)) {
            return false;
        }

        return Objects.equals(value, otherMonthsPaid.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
