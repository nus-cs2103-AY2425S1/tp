package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the date where the student is absent.
 * The date should only be in the form of DD-MM-YYYY, must be within the current year,
 * and must not exceed the current date.
 */
public class AbsentDate {

    public static final String MESSAGE_CONSTRAINTS =
            "The absent date should be in the format DD-MM-YYYY, must be within the current year, "
                    + "and must not exceed the current date. Ensure the day is valid for the specified month "
                    + "(e.g., February should not exceed 29 days).";
    public static final int CURRENT_YEAR = LocalDate.now().getYear();
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|20[01]\\d|20"
            + (CURRENT_YEAR % 100) + ")$";
    public final String absentDate;

    /**
     * Constructs an {@code AbsentDate}
     *
     * @param absentDate A valid date where student is absent.
     */
    public AbsentDate(String absentDate) {
        requireNonNull(absentDate);
        checkArgument(isValidAbsentDate(absentDate), MESSAGE_CONSTRAINTS);
        this.absentDate = absentDate;
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param absentDate A valid date where student is absent.
     * @return true if absentDate is valid
     */
    public static boolean isValidAbsentDate(String absentDate) {
        if (absentDate == null || !absentDate.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] dateParts = absentDate.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        YearMonth yearMonth = YearMonth.of(CURRENT_YEAR, month);
        int maxDay = yearMonth.lengthOfMonth();
        if (day > maxDay) {
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDate = LocalDate.parse(absentDate, formatter);
        return !parsedDate.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return absentDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AbsentDate)) {
            return false;
        }

        AbsentDate otherDate = (AbsentDate) other;
        return absentDate.equals(otherDate.absentDate);
    }

    @Override
    public int hashCode() {
        return absentDate.hashCode();
    }
}
