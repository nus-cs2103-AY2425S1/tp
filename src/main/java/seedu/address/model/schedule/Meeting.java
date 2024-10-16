package seedu.address.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Meeting {
    
    // Data Fields
    private List<Integer> contactIndexes;
    private String meetingName;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    
    public Meeting(List<Integer> contactIndexes, String meetingName, LocalDate meetingDate, LocalTime meetingTime) {
        requireAllNonNull(meetingDate, meetingTime);
        this.contactIndexes = contactIndexes;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }
    
    public List<Integer> getContactIndexes() {
        return contactIndexes;
    }
    public String convertContactIndexesToString(List<Integer> inputContactIndexes) {
        return inputContactIndexes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
    
    
    public String getMeetingName() {
        return meetingName;
    }
    
    public LocalDate getMeetingDate() {
        return meetingDate;
    }
    
    public LocalTime getMeetingTime() {
        return meetingTime;
    }
    
    public boolean hasConflictMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }
        
        return otherMeeting != null
                && otherMeeting.getMeetingDate().equals(getMeetingDate())
                && otherMeeting.getMeetingTime().equals(getMeetingTime());
    }
    
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }
    
        return otherMeeting != null
                && otherMeeting.getMeetingDate().equals(getMeetingDate())
                && otherMeeting.getMeetingTime().equals(getMeetingTime())
                && otherMeeting.getMeetingName().equals(getMeetingName());
    }
}
