package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents a Transaction's date in the transaction book. Guarantees: immutable; is valid or declared in
 * {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should be valid and in the format of DDMMYYYY";

    public static final DateTimeFormatter VALIDATION_FORMATTER = DateTimeFormatter.ofPattern("ddMMuuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    public final LocalDate date;

    /**
     * Constructs a {@code Date}
     *
     * @param date A valid date string.
     */
    public Date(String date) {
        requireNonNull(date);
        String trimmedDate = date.trim();
        AppUtil.checkArgument(isValidDate(trimmedDate), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(trimmedDate, VALIDATION_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test.trim(), VALIDATION_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String getNowDate() {
        return LocalDate.now().format(VALIDATION_FORMATTER);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date.format(DISPLAY_FORMATTER);
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
        return this.date.equals(otherDate.date);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

}
