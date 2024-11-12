package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents the date a person was last seen in the address book
 */
public class LastSeen {

    public static final String MESSAGE_CONSTRAINTS = "Date input is incorrect. Please check if the date is valid and "
            + "follows the DD-MM-YYYY format";

    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    public final LocalDate value;

    /**
     * Constructs a {@code lastSeen}.
     *
     * @param lastSeen A valid date.
     */
    public LastSeen(String lastSeen) {
        requireNonNull(lastSeen);
        checkArgument(isValidDate(lastSeen), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.parse(lastSeen, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return FORMATTER.format(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LastSeen)) {
            return false;
        }

        LastSeen otherAddress = (LastSeen) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(LastSeen otherDate) {
        return value.compareTo(otherDate.value);
    }
}
