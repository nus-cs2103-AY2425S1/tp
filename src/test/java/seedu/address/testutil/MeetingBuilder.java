package seedu.address.testutil;

import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_MEETINGTITLE = "Client Property Viewing Orchard Road Condo";
    public static final String DEFAULT_MEETINGDATE = "31-01-2024";

    private MeetingTitle meetingTitle;
    private MeetingDate meetingDate;

    /**
     * Creates a {@code MeetingBuilder} with the default details.
     */
    public MeetingBuilder() {
        meetingTitle = new MeetingTitle(DEFAULT_MEETINGTITLE);
        meetingDate = new MeetingDate(DEFAULT_MEETINGDATE);
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        meetingTitle = meetingToCopy.getMeetingTitle();
        meetingDate = meetingToCopy.getMeetingDate();
    }

    /**
     * Sets the {@code MeetingTitle} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withMeetingTitle(String meetingTitle) {
        this.meetingTitle = new MeetingTitle(meetingTitle);
        return this;
    }
    /**
     * Sets the {@code MeetingDate} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withMeetingDate(String meetingDate) {
        this.meetingDate = new MeetingDate(meetingDate);
        return this;
    }

    public Meeting build() {
        return new Meeting(meetingTitle, meetingDate);
    }

}

