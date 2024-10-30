package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

/**
 * Represents a Date for an Event in EventTory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS = """
            Date must be in any of the following formats:
            1. dd-MM-uuuu
            2. uuuu-MM-dd
            3. dd MMM uuuu
            4. dd MMMM uuuu
            """;

    // The 'uuuu' format is used instead of 'yyyy' to avoid errors when parsing a year  with the strict revolver style.
    private static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-uuuu"))
            .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("dd MMM uuuu", Locale.ENGLISH))
            .appendOptional(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.ENGLISH))
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A date string in any of the accepted formats.
     */
    public Date(String date) {
        requireAllNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, INPUT_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, INPUT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.format(OUTPUT_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

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

