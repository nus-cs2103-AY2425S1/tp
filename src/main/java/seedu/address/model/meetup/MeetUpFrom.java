package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a meetup's ending time in the address book.
 */
public class MeetUpFrom {

//    public static final String MESSAGE_CONSTRAINTS =
//            "Time should only be in the format YYYY-MM-DD HH:mm";
//
//    /*
//     * The time format must strictly follow the above format
//     */
//    public static final String VALIDATION_REGEX =
//            "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]$";

    public final LocalDateTime start;

    /**
     * Constructs a {@code MeetUpFrom}.
     *
     * @param meetUpFrom A valid meetup start timing.
     */
    public MeetUpFrom(LocalDateTime meetUpFrom) {
        requireNonNull(meetUpFrom);
//        checkArgument(isValidMeetUpFromTime(meetUpFrom), MESSAGE_CONSTRAINTS);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        this.start = LocalDateTime.parse(meetUpFrom, formatter);
        this.start = meetUpFrom;
    }

//    /**
//     * Returns true if a given string is a valid start timing.
//     */
//    public static boolean isValidMeetUpFromTime(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = start.format(formatter);
        return formattedDateTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetUpFrom)) {
            return false;
        }

        MeetUpFrom otherMeetUpFrom = (MeetUpFrom) other;
        return start.equals(otherMeetUpFrom.start);
    }

    @Override
    public int hashCode() {
        return start.hashCode();
    }
}
