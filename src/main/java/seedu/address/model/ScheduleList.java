package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;
import seedu.address.model.schedule.UniqueMeetingList;

/**
 * Represents a list of scheduled meetings.
 * Ensures that no duplicate or conflicting meetings are added.
 */
public class ScheduleList implements ReadOnlyScheduleList {
    private final UniqueMeetingList meetings;

    {
        meetings = new UniqueMeetingList();
    }

    /**
     * Constructs an empty {@code ScheduleList}.
     */
    public ScheduleList() {
    }

    /**
     * Constructs a {@code ScheduleList} with the data from {@code toBeCopied}.
     *
     * @param toBeCopied The data to copy into this {@code ScheduleList}.
     */
    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the current list of meetings with {@code meetings}.
     *
     * @param meetings A list of meetings to set.
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings.setMeetings(meetings);
    }

    /**
     * Resets the current data with {@code newData}.
     *
     * @param newData The new data to reset the schedule list.
     */
    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);
        setMeetings(newData.getMeetingList());
    }

    /**
     * Returns true if a meeting with the same details as {@code meeting} exists in the schedule list.
     *
     * @param newMeeting The meeting to check.
     * @return True if the meeting exists, false otherwise.
     */
    public boolean hasMeeting(Meeting newMeeting) {
        // Iterate over the unmodifiable list of meetings
        for (Meeting existingMeeting : meetings.asUnmodifiableObservableList()) {
            if (newMeeting.hasConflictMeeting(existingMeeting)) {
                return true; // Conflict detected
            }
        }
        return false; // No conflict
    }

    /**
     * Adds a meeting to the schedule list.
     * The meeting must not already exist in the list or conflict with an existing meeting.
     *
     * @param meeting The meeting to add.
     */
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Replaces the {@code target} meeting in the list with {@code editedMeeting}.
     * {@code target} must exist in the schedule list.
     * The identity of {@code editedMeeting} must not be the same as another existing meeting.
     *
     * @param target        The existing meeting to be replaced.
     * @param editedMeeting The new meeting to replace the target.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireNonNull(editedMeeting);
        meetings.setMeeting(target, editedMeeting);
    }

    /**
     * Removes the specified {@code key} meeting from the schedule list.
     * {@code key} must exist in the list.
     *
     * @param key The meeting to remove.
     */
    public void removeMeeting(Meeting key) {
        meetings.remove(key);
    }

    /**
     * Returns the schedule list as an unmodifiable {@code ObservableList}.
     *
     * @return The unmodifiable list of meetings.
     */
    @Override
    public ObservableList<Meeting> getMeetingList() {
        return meetings.asUnmodifiableObservableList();
    }

    /**
     * Returns the string representation of the schedule list.
     *
     * @return The string representation of the schedule list.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("meetings", meetings)
                .toString();
    }

    /**
     * Checks if the current {@code ScheduleList} is equal to another object.
     *
     * @param other The other object to compare.
     * @return True if both are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduleList)) {
            return false;
        }

        ScheduleList otherScheduleList = (ScheduleList) other;
        return meetings.equals(otherScheduleList.meetings);
    }

    /**
     * Returns the hash code of the schedule list.
     *
     * @return The hash code of the schedule list.
     */
    @Override
    public int hashCode() {
        return meetings.hashCode();
    }

	public boolean hasPersonInMeeting(Person person) {
        Iterator<Meeting> iterator = meetings.iterator();
        while (iterator.hasNext()) {
            Meeting meeting = iterator.next();
                if (meeting.hasPerson(person.getUid())) {
                    return true;
                }
        }
        return false;
	}
}
