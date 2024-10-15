package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meetup's ending time in the address book.
 */
public class From {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should only be in the format YYYY-MM-DD HH:mm";

    /*
     * The time format must strictly follow the above format
     */
    public static final String VALIDATION_REGEX =
            "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$";

    public final LocalDateTime value;

    /**
     * Constructs a {@code MeetUpFromType}.
     *
     * @param str A valid string that can transformed to a date.
     */
    public From(String str) {
        requireNonNull(str);
        checkArgument(isValidMeetUpFromTime(str), MESSAGE_CONSTRAINTS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.value = LocalDateTime.parse(str, formatter);
    }

    /**
     * Returns true if a given string is a valid start timing.
     */
    public static boolean isValidMeetUpFromTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = value.format(formatter);
        return formattedDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof From)) {
            return false;
        }

        From otherFrom = (From) other;
        return value.equals(otherFrom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
