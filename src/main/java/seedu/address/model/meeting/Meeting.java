package seedu.address.model.meeting;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Meeting in the meeting book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {
    // Identity fields
    private final MeetingTitle meetingTitle;
    private final MeetingDate meetingDate;

    /**
     * Every field must be present and not null.
     */
    public Meeting(MeetingTitle meetingTitle, MeetingDate meetingDate) {
        requireAllNonNull(meetingTitle);
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
    }

    public MeetingTitle getMeetingTitle() {
        return meetingTitle;
    }
    public MeetingDate getMeetingDate() {
        return meetingDate;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two meetings.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getMeetingDate().equals(getMeetingDate())
                && otherMeeting.getMeetingTitle().equals(getMeetingTitle());
    }

    /**
     * Returns true if both meetings have the same fields.
     * This defines a stronger notion of equality between two meetings.
     */
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
        return meetingTitle.equals(otherMeeting.meetingTitle)
                && meetingDate.equals(otherMeeting.meetingDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(meetingTitle, meetingDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("meetingTitle", meetingTitle)
                .add("meetingDate", meetingDate)
                .toString();
    }
}
