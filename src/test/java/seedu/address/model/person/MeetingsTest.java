package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.TimeClashException;

public class MeetingsTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
    private LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
    private String location = "A Valid Location";
    private Meeting defaultMeeting = new Meeting(startTime, endTime, location);

    @Test
    public void isClash() {
        assertFalse(new Meetings().isClash(defaultMeeting)); // empty Meeting

        Meetings meetings = new Meetings();
        meetings.add(defaultMeeting);

        // clash with existing meetings
        assertTrue(meetings.isClash(defaultMeeting));
    }

    @Test
    public void add_clashMeeting_throwsTimeClashException() {
        Meetings meetings = new Meetings();
        meetings.add(defaultMeeting);
        assertThrows(TimeClashException.class, () -> meetings.add(defaultMeeting));
    }

    @Test
    public void add_nullMeeting_throwsNullPointerException() {
        Meetings meetings = new Meetings();
        assertThrows(NullPointerException.class, () -> meetings.add(null));
    }

    @Test
    public void equals() {
        Meetings meetings = new Meetings();
        meetings.add(defaultMeeting);

        Meetings sameMeetings = new Meetings();
        sameMeetings.add(defaultMeeting);

        // same values -> returns true
        assertTrue(meetings.equals(sameMeetings));

        // same object -> returns true
        assertTrue(meetings.equals(meetings));

        // null -> returns false
        assertFalse(meetings.equals(null));

        // different types -> returns false
        assertFalse(meetings.equals(5.0f));

        LocalDateTime otherStartTime = LocalDateTime.parse("30-01-2024 11:00", formatter);
        LocalDateTime otherEndTime = LocalDateTime.parse("30-12-2024 12:00", formatter);
        String otherLocation = "Other Valid Location";
        Meeting otherMeeting = new Meeting(otherStartTime, otherEndTime, otherLocation);
        Meetings differentMeetings = new Meetings();
        differentMeetings.add(otherMeeting);

        // different values -> returns false
        assertFalse(meetings.equals(differentMeetings));
        assertFalse(meetings.equals(new Meetings())); // empty meetings
    }
}
