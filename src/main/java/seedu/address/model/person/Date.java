package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Represents a Person's next appointment date in the address book.
 * Guarantees: immutable; is always valid
 */
public class Date {
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

    /**
     * Validates the format and logical values of the provided date string.
     *
     * <p>This method checks if the given date string matches the expected format pattern of 'd/M/yyyy HHmm'
     * (e.g., '2/12/2024 1800'),
     * where 'd' is the day, 'M' is the month, 'yyyy' is the four-digit year, and 'HHmm' is the 24-hour time.
     * It first ensures the date adheres to the general format and, if so,
     * proceeds to validate the values for day, month, and year combinations, including special
     * cases like leap years and months with fewer days.</p>
     *
     * <p>If the date format or any of the values are invalid,
     * a {@code ParseException} is thrown with a detailed message indicating the issue.</p>
     *
     * @param date The date string to validate, in the format 'd/M/yyyy HHmm'.
     * @throws ParseException if the date format does not match 'd/M/yyyy HHmm', or if the values are logically invalid,
     *                        such as an incorrect day or an invalid leap year date.
     */
    public static void checkDate(String date) throws ParseException {
        if (!date.matches(DATE_PATTERN)) {
            if (!date.matches(FORMAT_PATTERN)) {
                throw new ParseException("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                        + "For example, '2/12/2024 1800'.");
            } else {
                throw new ParseException("Invalid date or time values! "
                        + "Ensure day, month, hour, and minute ranges are correct.");
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
                throw new ParseException("Invalid date: " + Month.of(month) + " "
                        + day + " is only valid in leap years.");
            } else if (day > 29) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 29 days.");
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                throw new ParseException("Invalid date: " + Month.of(month) + " cannot have more than 30 days.");
            }
        }

    }


    /**
     * Parses a date string into a {@code LocalDateTime} object.
     *
     * <p>This method first validates the date string format and values by calling {@code checkDate}.
     * Once validated, it converts the date string into a {@code LocalDateTime} object based on the specified
     * format 'd/M/yyyy HHmm'. For instance, '2/12/2024 1800' would be parsed as 2nd December 2024 at 18:00 hours.</p>
     *
     * @param date The date string to be parsed, expected in the format 'd/M/yyyy HHmm'.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException if the date format is invalid or if the date and time values are incorrect.
     */
    public static LocalDateTime parseDateTime(String date) throws ParseException {
        checkDate(date);
        return LocalDateTime.parse(date, FORMATTER);
    }
}
