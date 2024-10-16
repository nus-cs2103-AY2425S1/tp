package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;
import seedu.address.model.schedule.UniqueMeetingList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ScheduleList implements ReadOnlyScheduleList{
    private final UniqueMeetingList meetings;
    
    {
        meetings = new UniqueMeetingList();
    }
    
    public ScheduleList() {};
    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }
    
    public void setMeetings(List<Meeting> meetings) {
        this.meetings.setMeetings(meetings);
    }
    
    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);
        setMeetings(newData.getMeetingList());
    }
    
    /**
     * Returns true if a meeting with the same MeetingDate, MeetingTime and MeetingName as {@code Meeting} exists in the address book.
     */
    public boolean hasMeeting(Meeting meeting) {
        requireNonNull(meeting);
        return meetings.contains(meeting);
    }
    
    /**
     * Adds a meeting to the schedule list.
     * The meeting must not already exist in the schedule list.
     */
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
    
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireNonNull(editedMeeting);
        
        meetings.setMeeting(target, editedMeeting);
    }
    
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeMeeting(Meeting key) {
        meetings.remove(key);
    }
    
    //// util methods
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("meetings", meetings)
                .toString();
    }
    
    @Override
    public ObservableList<Meeting> getMeetingList() {
        return meetings.asUnmodifiableObservableList();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        
        // instanceof handles nulls
        if (!(other instanceof ScheduleList)) {
            return false;
        }
        
        ScheduleList otherScheduleList = (ScheduleList) other;
        return meetings.equals(otherScheduleList.meetings);
    }
    
    @Override
    public int hashCode() {
        return meetings.hashCode();
    }
}

