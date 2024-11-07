package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Represents a Person's next appointment date and time in the address book.
 * Guarantees: immutable; is always valid
 */
public class Date {
    public static final Date NO_DATE = new Date(LocalDateTime.MIN);
    private static final String DATE_AND_TIME_PATTERN =
          "^(0[1-9]|[1-9]|[12][0-9]|3[01])/(0[1-9]|[1-9]|1[0-2])/\\d{4} ([01][0-9]|2[0-3])[0-5][0-9]$";
    private static final String DATE_PATTERN =
            "^([1-9]|0[1-9]|[12][0-9]|3[01])/(0[1-9]|[1-9]|1[0-2])/\\d{4}$";
    private static final String FORMAT_PATTERN_DATE_AND_TIME = "^\\d{1,2}/\\d{1,2}/\\d{4} \\d{4}$";
    private static final String FORMAT_PATTERN_DATE = "^\\d{1,2}/\\d{1,2}/\\d{4}$";
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
     * @param date the date string to validate, in the format {@code "d/M/yyyy HHmm"}.
     * @throws ParseException if the date format is incorrect or if values exceed valid ranges:
     *      <ul>
     *          <li>{@code "Invalid date format! Please use 'd/M/yyyy HHmm'. For example, '2/12/2024 1800'"}
     *          if the format does not match.</li>
     *          <li>{@code "Invalid date or time values! Ensure day, month, hour, and minute ranges are correct."}
     *          for incorrect values.</li>
     *          <li>Specific messages for day limits in certain months,
     *          such as {@code "February cannot have more than 29 days."} for incorrect day counts.</li>
     *      </ul>
     * @throws NullPointerException if the provided date string is {@code null}.
     */
    public static void checkDateAndTime(String date) throws ParseException {
        if (!date.matches("[0-9/ ]+")) {
            throw new ParseException("Invalid date and time characters detected! "
                    + "Only numbers, '/' and spaces are allowed.");
        }

        if (!date.matches(DATE_AND_TIME_PATTERN) && !date.matches(FORMAT_PATTERN_DATE_AND_TIME)) {
            throw new ParseException("Invalid date and time format! Please use 'd/M/yyyy HHmm'. "
                    + "For example, '2/12/2024 1800'.");
        }
        if (!date.matches(DATE_AND_TIME_PATTERN)) {
            throw new ParseException("Invalid date or time values! "
                    + "Ensure day, month, hour, and minute ranges are correct.");
        }

        String[] dateAndTimeArray = date.split(" ");
        validateDateCalendarValue(dateAndTimeArray[0]);
    }


    /**
     * Validates the format and values of a date string specifically for date in Schedule Command.
     * <p>
     * This method checks if the input date string matches the expected format
     * of "d/M/yyyy" (e.g., "2/12/2024") and whether the individual day,
     * month, and year values are valid according to the Gregorian calendar.
     * If the format or values are invalid, a {@link ParseException} is thrown
     * with a descriptive message indicating the nature of the error.
     * </p>
     *
     * @param date the date string to validate, expected in the format "d/M/yyyy"
     *             (e.g., "2/12/2024"). The string may include spaces but must
     *             adhere to the specified format.
     * @throws ParseException if the date format is invalid or the date values
     *                        are out of range (e.g., day is not between 1 and 31,
     *                        month is not between 1 and 12, or the year is
     *                        negative).
     * @throws NullPointerException if the provided date string is {@code null}.
     */
    public static void checkDate(String date) throws ParseException {
        if (!date.matches("[0-9/ ]+")) {
            throw new ParseException("Invalid date characters detected! "
                    + "Only numbers and '/' are allowed.");
        }

        if (!date.matches(DATE_PATTERN) && !date.matches(FORMAT_PATTERN_DATE)) {
            throw new ParseException("Invalid date format! Please use 'd/M/yyyy'. "
                    + "For example, '2/12/2024'.");
        }
        if (!date.matches(DATE_PATTERN)) {
            throw new ParseException("Invalid date values! "
                    + "Ensure day and month ranges are correct.");
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
        String[] dateParts = date.split("/");

        int dayValue = Integer.parseInt(dateParts[0]);
        int monthValue = Integer.parseInt(dateParts[1]);
        int yearValue = Integer.parseInt(dateParts[2]);

        // Allow year 1 but reject years before it (e.g., 0000)
        if (yearValue < 1) {
            throw new ParseException("Invalid year: " + yearValue
                    + " is not supported. Year must be greater than or equal to 1.");
        }

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
        checkDateAndTime(date);
        return LocalDateTime.parse(date, FORMATTER);
    }

    /**
     * Parses a date string into a {@code LocalDateTime} object specifically for date in Schedule command.
     *
     * <p>This method first validates the date string format and values by calling {@code checkDate}.
     * Once validated, it converts the date string into a {@code LocalDateTime} object based on the specified
     * format 'd/M/yyyy HHmm'. For instance, '2/12/2024 1800' would be parsed as 2nd December 2024 at 18:00 hours.</p>
     *
     * @param date The date string to be parsed, expected in the format 'd/M/yyyy HHmm'.
     * @return A {@code LocalDateTime} object representing the parsed date and time.
     * @throws ParseException if the date format is invalid or if the date and time values are incorrect.
     */
    public static LocalDateTime parseDate(String date) throws ParseException {
        checkDate(date);
        String dateWithDefaultTime = date + " 0000";
        return LocalDateTime.parse(dateWithDefaultTime, FORMATTER);
    }

    @Override
    public String toString() {
        return value.equals(Date.NO_DATE.value) ? "" : value.format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
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

    public LocalDate getDateOnly() {
        return value.toLocalDate();
    }


}
