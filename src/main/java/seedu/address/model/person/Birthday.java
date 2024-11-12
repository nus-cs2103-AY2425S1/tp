package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's birthday in the address book.
 */
public class Birthday {

    public static final String MESSAGE_CONSTRAINTS =
            "Birthdays should be in the format 'yyyy-MM-dd', "
                    + "must be a valid date, and must be a date before today's date.";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final String value;

    private final LocalDate date;


    /**
     * Constructs a {@code Birthday}.
     *
     * @param dateStr A valid date string.
     */
    public Birthday(String dateStr) {
        requireNonNull(dateStr); // check entry is not null
        // check valid birthday input
        checkArgument(isValidBirthday(dateStr), MESSAGE_CONSTRAINTS);
        date = parseDate(dateStr);
        this.value = date.format(FORMATTER);
    }

    /**
     * Parses the given date string into a {@code LocalDate}.
     *
     * @param dateStr The date string to parse.
     * @return A {@code LocalDate} object representing the date.
     */
    private static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid birthday date.
     */
    public static boolean isValidBirthday(String input) {
        requireNonNull(input);
        try {
            LocalDate testBirthday = LocalDate.parse(input, FORMATTER);
            // compare current date with birthday. If birthday exceeds current, throw an exception
            return testBirthday.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        // This method made use of ChatGPT to ensure its correctness when comparing the birthday object
        return this == other
                || (other instanceof Birthday
                && date.equals(((Birthday) other).date));
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }
}
