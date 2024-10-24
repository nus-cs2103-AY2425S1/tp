package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meetup's starting time in the buyer list.
 */
public class To {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should only be in the format YYYY-MM-DD HH:mm";

    /*
     * The time format must strictly follow the above format
     */
    public static final String VALIDATION_REGEX =
            "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$";

    public final LocalDateTime value;

    /**
     * Constructs a {@code MeetUpToType}.
     *
     * @param to A valid string that can transformed to a date.
     */
    public To(String to) {
        requireNonNull(to);
        checkArgument(isValidTo(to), MESSAGE_CONSTRAINTS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.value = LocalDateTime.parse(to, formatter);
    }

    /**
     * Returns true if a given string is a valid end timing.
     */
    public static boolean isValidTo(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        // Define the formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy (h:mma)");

        // Format the LocalDateTime object
        String formattedDateTime = value.format(formatter);

        // Convert "AM"/"PM" to lowercase
        formattedDateTime = formattedDateTime.replace("AM", "am").replace("PM", "pm");
        return formattedDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof To)) {
            return false;
        }

        To otherTo = (To) other;
        return value.equals(otherTo.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Getter method for LocalDateTime value.
     */
    public LocalDateTime getDateTime() {
        return value;
    }
}
