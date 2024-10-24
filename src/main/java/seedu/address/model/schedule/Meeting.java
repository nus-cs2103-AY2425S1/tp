package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a Meeting in the schedule.
 * Guarantees: details are present and not null, and fields are validated.
 */
public class Meeting {

    // Data Fields
    private final List<UUID> contactUids;
    private final String meetingName;
    private final LocalDate meetingDate;
    private final LocalTime meetingTime;

    /**
     * Constructs a {@code Meeting}.
     *
     * @param contactUids A list of indexes representing the contacts involved in the meeting.
     * @param meetingName Name of the meeting.
     * @param meetingDate Date of the meeting. Cannot be null.
     * @param meetingTime Time of the meeting. Cannot be null.
     */
    public Meeting(List<UUID> contactUids, String meetingName, LocalDate meetingDate, LocalTime meetingTime) {
        requireAllNonNull(meetingDate, meetingTime);
        this.contactUids = contactUids;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }

    /**
     * Returns the list of contact indexes involved in the meeting.
     *
     * @return A list of contact indexes.
     */
    public List<UUID> getContactUids() {
        return contactUids;
    }

    /**
     * Converts the list of contact indexes to a string, with indexes separated by commas.
     *
     * @return A comma-separated string of contact indexes.
     */
    public String convertContactUidsToString() {
        return contactUids.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
    }

    /**
     * Returns the name of the meeting.
     *
     * @return Meeting name.
     */
    public String getMeetingName() {
        return meetingName;
    }

    /**
     * Returns the date of the meeting.
     *
     * @return Meeting date.
     */
    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    /**
     * Returns the time of the meeting.
     *
     * @return Meeting time.
     */
    public LocalTime getMeetingTime() {
        return meetingTime;
    }

    /**
     * Checks if the current meeting has a time conflict with another meeting.
     * Meetings are considered conflicting if they occur at the same date and time.
     *
     * @param otherMeeting Another meeting to compare against.
     * @return True if the meetings conflict, false otherwise.
     */
    public boolean hasConflictMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getMeetingDate().equals(getMeetingDate())
                && otherMeeting.getMeetingTime().equals(getMeetingTime())
                && otherMeeting.getContactUids().equals((getContactUids()));
    }

    /**
     * Checks if two meetings are the same.
     * Meetings are considered the same if they have the same date, time, and name.
     *
     * @param otherMeeting Another meeting to compare against.
     * @return True if the meetings are the same, false otherwise.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getMeetingDate().equals(getMeetingDate())
                && otherMeeting.getMeetingTime().equals(getMeetingTime())
                && otherMeeting.getMeetingName().equals(getMeetingName())
                && otherMeeting.getContactUids().equals((getContactUids()));
    }

    /**
     * @return if the meeting has the person with the given uid
     */
    public boolean hasPerson(UUID uid) {
        for (UUID contactUid : contactUids) {
            if (contactUid.equals(uid)) {
                return true;
            }
        }
        return false;
    }
}
