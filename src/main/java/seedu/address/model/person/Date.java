package seedu.address.model.person;

import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's next appointment date in the address book.
 * Guarantees: immutable; is always valid
 */
public class Date {

    public static String MESSAGE_CONSTRAINTS = "Invalid date format! ";
    private static final String DATE_PATTERN =
          "^([1-9]|[12][0-9]|3[01])/([1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final String FORMAT_PATTERN = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public final LocalDateTime value;

    /**
     * Constructs a {@code Date} from a LocalDateTime.
     *
     * @param date A valid LocalDateTime.
     */
    public Date(LocalDateTime date) {
        requireNonNull(date);
        value = date;
    }

    public static boolean isValidDate(String date) {
        if (!date.matches(DATE_PATTERN)) {
            if (!date.matches(FORMAT_PATTERN)) {
                MESSAGE_CONSTRAINTS = "Invalid date format! Please use 'd/M/yyyy HHmm'. " +
                      "For example, '2/12/2024 1800'";
                return false;
            } else {
                MESSAGE_CONSTRAINTS = "Invalid date or time values! "
                      + "Ensure day, month, hour, and minute ranges are correct.";
                return false;
            }
        }
        String[] dateAndTime = date.split(" ");
        String[] dateParts = dateAndTime[0].split("/");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        // Check month-day combinations, including leap year validation
        if (month == 2) {
            if (day == 29 && !Year.of(year).isLeap()) {
                MESSAGE_CONSTRAINTS = "Invalid date: " + Month.of(month) + " "
                      + day + " is only valid in leap years.";
                return false;
            } else if (day > 29) {
                MESSAGE_CONSTRAINTS = "Invalid date: " + Month.of(month) + " cannot have more than 29 days.";
                return false;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                MESSAGE_CONSTRAINTS = "Invalid date: " + Month.of(month) + " cannot have more than 30 days.";
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return value != LocalDateTime.MIN ? value.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm")) : "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
