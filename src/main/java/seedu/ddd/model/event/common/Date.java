package seedu.ddd.model.event.common;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents an event's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String[] VALID_DATE_PATTERNS = new String[] {"yyyy-MM-dd", "MM/dd/yyyy", "d MMM yyyy"};
    private static final List<DateTimeFormatter> FORMATTERS = Stream.of(VALID_DATE_PATTERNS)
            .map(DateTimeFormatter::ofPattern)
            .toList();
    public static final String MESSAGE_CONSTRAINTS = "Supported ate formats: " + Stream.of(VALID_DATE_PATTERNS)
            .collect(Collectors.joining(", "));

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        AppUtil.checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = parseDate(date);
    }

    /**
     * Returns true if a given string is a valid service.
     */
    // TODO: fix date occurrences in storage data before return false for errors.
    public static boolean isValidDate(String test) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                LocalDate.parse(test, formatter);
                return true;
            } catch (DateTimeParseException e) {
                //Ignore and try the next one
            }
        }
        return false;
    }

    /**
     * Parses the date string using available formatters.
     *
     * @param date The date string to parse.
     * @return The parsed LocalDate.
     */
    public static LocalDate parseDate(String date) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try next formatter
            }
        }
        throw new DateTimeParseException("Date could not be parsed", date, 0);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
