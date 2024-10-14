package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meetup's starting time in the address book.
 */
public class MeetUpTo {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should only be in the format YYYY-MM-DD HH:mm";

    /*
     * The time format must strictly follow the above format
     */
    public static final String VALIDATION_REGEX =
            "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$";

    public final LocalDateTime end;

    /**
     * Constructs a {@code MeetUpFrom}.
     *
     * @param meetUpTo A valid meetup end timing.
     */
    public MeetUpTo(LocalDateTime meetUpTo) {
        requireNonNull(meetUpTo);

        this.end = meetUpTo;
    }

    /**
     * Returns true if a given string is a valid end timing.
     */
    public static boolean isValidMeetUpToTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = end.format(formatter);
        return formattedDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetUpTo)) {
            return false;
        }

        MeetUpTo otherMeetUpTo = (MeetUpTo) other;
        return end.equals(otherMeetUpTo.end);
    }

    @Override
    public int hashCode() {
        return end.hashCode();
    }
}
