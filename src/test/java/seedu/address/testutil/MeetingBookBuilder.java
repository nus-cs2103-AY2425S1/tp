package seedu.address.testutil;

import seedu.address.model.MeetingBook;
import seedu.address.model.meeting.Meeting;

/**
 * A utility class to help with building Meetingbook objects.
 * Example usage: <br>
 *     {@code MeetingBook ab = new MeetingBookBuilder().withMeeting("John", "Doe").build();}
 */
public class MeetingBookBuilder {

    private MeetingBook meetingBook;

    public MeetingBookBuilder() {
        meetingBook = new MeetingBook();
    }

    public MeetingBookBuilder(MeetingBook meetingBook) {
        this.meetingBook = meetingBook;
    }

    /**
     * Adds a new {@code Meeting} to the {@code MeetingBook} that we are building.
     */
    public MeetingBookBuilder withMeeting(Meeting meeting) {
        meetingBook.addMeeting(meeting);
        return this;
    }

    public MeetingBook build() {
        return meetingBook;
    }
}
