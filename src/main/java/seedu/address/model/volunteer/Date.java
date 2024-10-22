package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Event's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 * Throws: DateTimeParseException if an invalid date is provided
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should follow the format yyyy-mm-dd, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //@@ author Greg Bacon -reused
    // Regex "\\d{4}-\\d{2}-\\d{2}]" used to identify if a String matched the requried LocalDate format is reused
    // from the author's reply on Stack Overflow
    // source: https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    //@@ author

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) throws DateTimeParseException {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String toParsableString() {
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
