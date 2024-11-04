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
          "^(0[1-9]|[1-9]|[12][0-9]|3[01])/(0[1-9]|[1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
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

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            checkDate(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Validates a date string, ensuring it follows the expected format and adheres to logical date constraints.
     * <p>
     * Expected format: {@code 'd/M/yyyy HHmm'} where:
     * <ul>
     * <li>{@code d} - day of the month (1-31)</li>
     * <li>{@code M} - month of the year (1-12)</li>
     * <li>{@code yyyy} - 4-digit year</li>
     * <li>{@code HHmm} - time in 24-hour format (00-23 for hours, 00-59 for minutes)</li>
     * </ul>
     * Example valid input: {@code "2/12/2024 1800"}.
     * <p>
     * This method checks both format and calendar logic, including:
     * <ul>
     * <li>Day-month-year validity (e.g., February 29 is valid only in leap years).</li>
     * <li>Month-specific day limits (e.g., April cannot exceed 30 days).</li>
     * </ul>
     *
     * @param date the date string to validate, in the format {@code "d/M/yyyy HHmm"}
     * @throws ParseException if the date format is incorrect or if values exceed valid ranges:
     *      <ul>
     *          <li>{@code "Invalid date format! Please use 'd/M/yyyy HHmm'. For example, '2/12/2024 1800'"}
     *          if the format does not match.</li>
     *          <li>{@code "Invalid date or time values! Ensure day, month, hour, and minute ranges are correct."}
     *          for incorrect values.</li>
     *          <li>Specific messages for day limits in certain months,
     *          such as {@code "February cannot have more than 29 days."}</li>
     *      </ul>
     */
    public static void checkDate(String date) throws ParseException {
        if (!date.matches(DATE_PATTERN) && !date.matches(FORMAT_PATTERN)) {
            throw new ParseException("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                    + "For example, '2/12/2024 1800'.");
        }
        if (!date.matches(DATE_PATTERN)) {
            throw new ParseException("Invalid date or time values! "
                    + "Ensure day, month, hour, and minute ranges are correct.");
        }
        validateDateCalendarValue(date);
    }

    /**
     * Checks for logical calendar constraints for a given date, ensuring that day values match valid limits
     * based on month and leap year rules.
     *
     * @param date the date string to validate, expected in the format {@code "d/M/yyyy HHmm"}
     * @throws ParseException if the date contains invalid day values based on month and year.
     *      <ul>
     *          <li>
     *              Throws {@code "Invalid date: [Month] cannot have more than [day limit] days."}
     *              for months with restricted day counts.
     *          </li>
     *          <li>
     *              Throws {@code "Invalid date: February [day] is only valid in leap years."}
     *              if February 29 is specified in a non-leap year.
     *          </li>
     *      </ul>
     */
    private static void validateDateCalendarValue(String date) throws ParseException {
        String[] dateAndTimeArray = date.split(" ");
        String[] dateParts = dateAndTimeArray[0].split("/");

        int dayValue = Integer.parseInt(dateParts[0]);
        int monthValue = Integer.parseInt(dateParts[1]);
        int yearValue = Integer.parseInt(dateParts[2]);

        //Check for months with only 30 days
        if (monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11) {
            if (dayValue > 30) {
                throw new ParseException("Invalid date: " + Month.of(monthValue) + " cannot have more than 30 days.");
            }
        }
        // Check for feb day values, including leap year validation
        if (monthValue == 2) {
            if (dayValue == 29 && !Year.of(yearValue).isLeap()) {
                throw new ParseException("Invalid date: "
                        + Month.of(monthValue)
                        + " "
                        + dayValue
                        + " is only valid in leap years.");
            } else if (dayValue > 29) {
                throw new ParseException("Invalid date: "
                        + Month.of(monthValue)
                        + " cannot have more than 29 days.");
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
