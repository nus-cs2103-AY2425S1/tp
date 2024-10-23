package seedu.address.model.concert;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Concert's date and time in the address book. Guarantees: immutable; is valid as
 * declared in {@link #isValidDate(String, DateTimeFormatter)}
 */
public class ConcertDate {

    public static final String MESSAGE_CONSTRAINTS =
            "ConcertDate should only contain a date in YYYY-MM-DD hhmm format, and it should not be blank";

    /*
     * The ConcertDate input should be a string in YYYY-MM-DD hhmm format.
     */
    public static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HHmm");

    /*
     * The ConcertDate stores the date in 'D MMM YYYY HHmm format.
     */
    public static final DateTimeFormatter OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMM yyyy HHmm");

    public final String concertDate;

    /**
     * Constructs a {@code ConcertDate}.
     *
     * @param date A valid concert date.
     */
    public ConcertDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date, INPUT_DATE_FORMATTER), MESSAGE_CONSTRAINTS);
        concertDate = processDate(date, INPUT_DATE_FORMATTER, OUTPUT_DATE_FORMATTER);
    }

    /**
     * Checks whether given string is a valid concert date in YYYY-MM-DD HHmm format
     *
     * @param test A date to test.
     * @return true if the given string is a valid concert date.
     */
    public static boolean isValidDate(String test, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the date in the output format specified.
     *
     * @param date A valid concert date.
     * @param intputFormat The format of the date param.
     * @param outputFormat The desired format for the returned date.
     * @return The date formmatted according the the outputFormat.
     */
    public static String processDate(String date, DateTimeFormatter intputFormat,
            DateTimeFormatter outputFormat) {
        LocalDateTime ldt = LocalDateTime.parse(date, intputFormat);
        return ldt.format(outputFormat);
    }

    @Override
    public String toString() {
        return concertDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConcertDate)) {
            return false;
        }

        ConcertDate otherName = (ConcertDate) other;
        return concertDate.equals(otherName.concertDate);
    }

    @Override
    public int hashCode() {
        return concertDate.hashCode();
    }

}
