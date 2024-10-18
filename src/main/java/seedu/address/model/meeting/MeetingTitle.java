package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Meeting's title in the meeting book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeetingTitle(String)}
 */
public class MeetingTitle {

    public static final String MESSAGE_CONSTRAINTS =
            "Titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String meetingTitle;

    /**
     * Constructs a {@code MeetingTitle}.
     *
     * @param meetingTitle A valid meetingTitle.
     */
    public MeetingTitle(String meetingTitle) {
        requireNonNull(meetingTitle);
        checkArgument(isValidMeetingTitle(meetingTitle), MESSAGE_CONSTRAINTS);
        this.meetingTitle = meetingTitle;
    }

    /**
     * Returns true if a given string is a valid meeting title.
     */
    public static boolean isValidMeetingTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return meetingTitle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingTitle)) {
            return false;
        }

        MeetingTitle otherMeetingTitle = (MeetingTitle) other;
        return meetingTitle.equals(otherMeetingTitle.meetingTitle);
    }

    @Override
    public int hashCode() {
        return meetingTitle.hashCode();
    }

}
