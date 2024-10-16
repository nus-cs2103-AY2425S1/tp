package seedu.address.model.person;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a Person's months paid in the address book.
 */
public class MonthPaid {
    public static final String MESSAGE_CONSTRAINTS = "MonthPaid should take a string with the pattern"
            + " YYYY-MM, where one or of such patterns can be included if separated by a space.";
    public static final String SPLIT_VALIDATION_REGEX = "^[0-9]{4}-[0-9]{2}$";
    public final String value;

    public MonthPaid(String monthsPaid) {
        this.value = monthsPaid;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Concatenates with another monthsPaid
     * @param otherMonthPaid the monthsPaid to be concatenated with
     * @return the concatenated monthsPaid
     */
    public MonthPaid concatenate(MonthPaid otherMonthPaid) {
        if (value.isEmpty()) {
            return otherMonthPaid;
        } else {
            return new MonthPaid(value + " " + otherMonthPaid.value);
        }
    }

    /**
     * Returns true if a given string is a valid monthsPaid.
     */
    public static boolean isValidMonthPaid(String monthPaid) {
        assert !monthPaid.isEmpty();
        return monthPaid.matches(SPLIT_VALIDATION_REGEX);
    }

    /**
     * Returns true if all strings in a given {@code Collection<String> monthsPaid} is a valid monthPaid.
     */
    public static boolean isValidMonthsPaid(Collection<String> monthsPaid) {
        return monthsPaid.stream().allMatch(MonthPaid::isValidMonthPaid);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MonthPaid otherMonthPaid)) {
            return false;
        }

        return Objects.equals(value, otherMonthPaid.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
