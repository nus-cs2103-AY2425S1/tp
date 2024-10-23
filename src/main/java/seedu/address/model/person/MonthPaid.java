package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's months paid in the address book.
 */
public class MonthPaid implements Comparable<MonthPaid> {
    public static final String MESSAGE_CONSTRAINTS = "MonthPaid should take a string with the pattern"
            + " YYYY-MM, where YYYY should be between 1900 and 2100, and MM should be between 01 and 12.";
    public static final String SPLIT_VALIDATION_REGEX = "^(19|20)[0-9]{2}-(0[1-9]|1[0-2])$";
    public final String monthPaidValue;

    /**
     * @param monthPaidValue Raw String value of monthPaid that follows YYYY-MM format.
     */
    public MonthPaid(String monthPaidValue) {
        requireNonNull(monthPaidValue);
        checkArgument(isValidMonthPaid(monthPaidValue), MESSAGE_CONSTRAINTS);
        this.monthPaidValue = monthPaidValue;
    }
    /**
     * Returns true if a given string is a valid monthsPaid.
     */
    public static boolean isValidMonthPaid(String monthPaid) {
        return monthPaid.matches(SPLIT_VALIDATION_REGEX);
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

        return Objects.equals(monthPaidValue, otherMonthPaid.monthPaidValue);
    }

    @Override
    public int hashCode() {
        return monthPaidValue.hashCode();
    }

    @Override
    public int compareTo(MonthPaid other) {
        // Split the value into year and month
        String[] currentMonthYearParts = this.monthPaidValue.split("-");
        String[] otherMonthYearParts = other.monthPaidValue.split("-");

        int currentYear = Integer.parseInt(currentMonthYearParts[0]);
        int otherYear = Integer.parseInt(otherMonthYearParts[0]);

        // Compare by year first (descending order)
        if (currentYear != otherYear) {
            return Integer.compare(otherYear, currentYear);
        }

        // Compare by month if years are the same (descending order)
        int currentMonth = Integer.parseInt(currentMonthYearParts[1]);
        int otherMonth = Integer.parseInt(otherMonthYearParts[1]);
        return Integer.compare(otherMonth, currentMonth);
    }

    @Override
    public String toString() {
        return '[' + monthPaidValue + ']';
    }
}
