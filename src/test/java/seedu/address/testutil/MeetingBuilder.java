package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import seedu.address.model.schedule.Meeting;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {
    // Default values
    public static final String DEFAULT_MEETING_NAME = "Team Sync";
    public static final LocalDate DEFAULT_DATE = LocalDate.now();
    public static final LocalTime DEFAULT_TIME = LocalTime.of(10, 0); // 10:00 AM
    public static final List<UUID> DEFAULT_CONTACT_UIDS = new ArrayList<>();

    private String meetingName;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private List<UUID> contactUids;


    /**
     * Constructs a {@code MeetingBuilder} with default values.
     * <ul>
     *     <li>meetingName is set to {@code DEFAULT_MEETING_NAME} ("Team Sync")</li>
     *     <li>meetingDate is set to {@code DEFAULT_DATE} (current date)</li>
     *     <li>meetingTime is set to {@code DEFAULT_TIME} (10:00 AM)</li>
     *     <li>contactUids is set to an empty {@code List<UUID>}</li>
     * </ul>
     */
    public MeetingBuilder() {
        meetingName = DEFAULT_MEETING_NAME;
        meetingDate = DEFAULT_DATE;
        meetingTime = DEFAULT_TIME;
        contactUids = DEFAULT_CONTACT_UIDS;
    }

    /**
     * Initializes the MeetingBuilder with the data of an existing Meeting.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        meetingName = meetingToCopy.getMeetingName();
        meetingDate = meetingToCopy.getMeetingDate();
        meetingTime = meetingToCopy.getMeetingTime();
        contactUids = new ArrayList<>(meetingToCopy.getContactUids());
    }

    /**
     * Sets the {@code meetingName} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withName(String name) {
        this.meetingName = name;
        return this;
    }

    /**
     * Sets the {@code meetingDate} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDate(LocalDate date) {
        this.meetingDate = date;
        return this;
    }

    /**
     * Sets the {@code meetingTime} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTime(LocalTime time) {
        this.meetingTime = time;
        return this;
    }

    /**
     * Sets the {@code contactUids} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withContactUids(List<UUID> contactUids) {
        this.contactUids = new ArrayList<>(contactUids);
        return this;
    }

    /**
     * Builds and returns a {@code Meeting} with the current attributes.
     */
    public Meeting build() {
        return new Meeting(contactUids, meetingName, meetingDate, meetingTime);
    }
}
