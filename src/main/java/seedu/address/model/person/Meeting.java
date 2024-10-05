package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Person's meetings in the Meetings field.
 * Guarantees: immutable
 */
public class Meeting {
    public static final String MESSAGE_CONSTRAINTS = "Location can take any values, and it should not be blank";
    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String location;
    public final Date startTime;
    public final Date endTime;

    /**
     * Constructs an {@code Meeting}.
     *
     * @param startTime A valid starting time of the meeting.
     * @param endTime A valid ending time of the meeting.
     * @param location A valid location.
     */
    public Meeting(Date startTime, Date endTime, String location) {
        requireNonNull(location);
        requireNonNull(startTime);
        requireNonNull(endTime);

        this.startTime = startTime;
        this.endTime = endTime;

        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.location = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given meeting overlaps with another meeting.
     */
    public boolean isOverlap(Meeting other) {
        boolean isStartBeforeOtherEnd = this.startTime.before(other.endTime);
        boolean isEndAfterOtherStart = this.endTime.after(other.startTime);

        // If both conditions are true, meetings overlap
        return isStartBeforeOtherEnd && isEndAfterOtherStart;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a, d MMMM yyyy");
        return formatter.format(startTime) + " - " + location;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return location.equals(otherMeeting.location) && startTime.equals(otherMeeting.startTime)
                && endTime.equals(otherMeeting.endTime);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

}
