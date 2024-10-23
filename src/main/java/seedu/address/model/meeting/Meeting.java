package seedu.address.model.meeting;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Name;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;

/**
 * Represents a Meeting in the meeting book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {
    // Identity fields
    private final MeetingTitle meetingTitle;
    private final MeetingDate meetingDate;
    private final Name buyer;
    private final Name seller;
    private final Type type;
    private final PostalCode postalCode;

    /**
     * Every field must be present and not null.
     */
    public Meeting(MeetingTitle meetingTitle, MeetingDate meetingDate, Name buyer, Name seller,
                   Type type, PostalCode postalCode) {
        requireAllNonNull(meetingTitle);
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        this.buyer = buyer;
        this.seller = seller;
        this.type = type;
        this.postalCode = postalCode;
    }

    public MeetingTitle getMeetingTitle() {
        return meetingTitle;
    }
    public MeetingDate getMeetingDate() {
        return meetingDate;
    }

    public Name getBuyer() {
        return this.buyer;
    }
    public Name getSeller() {
        return this.seller;
    }
    public Type getType() {
        return this.type;
    }

    public PostalCode getPostalCode() {
        return this.postalCode;
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
                .add("buyer", buyer)
                .add("seller", seller)
                .add("type", type)
                .add("postalCode", postalCode)
                .toString();
    }
}
