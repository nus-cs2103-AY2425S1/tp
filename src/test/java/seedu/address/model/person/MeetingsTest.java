package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.TimeClashException;

public class MeetingsTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
    private LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
    private String location = "A Valid Location";
    private Name name = new Name("A Valid Name");
    private Meeting defaultMeeting = new Meeting(name, startTime, endTime, location);

    public MeetingsTest() throws CommandException {
    }

    @Test
    public void test_isClash() {
        assertFalse(new Meetings().isClash(defaultMeeting)); // empty Meeting

        Meetings meetings = new Meetings();
        meetings.addMeeting(defaultMeeting);

        // clash with existing meetings
        assertTrue(meetings.isClash(defaultMeeting));
    }

    @Test
    public void tadd_clashMeeting_throwsTimeClashException() {
        Meetings meetings = new Meetings();
        meetings.addMeeting(defaultMeeting);
        assertThrows(TimeClashException.class, () -> meetings.addMeeting(defaultMeeting));
    }

    @Test
    public void add_nullMeeting_throwsNullPointerException() {
        Meetings meetings = new Meetings();
        assertThrows(NullPointerException.class, () -> meetings.addMeeting(null));
    }

    @Test
    public void test_equals() {
        Meetings meetings = new Meetings();
        meetings.addMeeting(defaultMeeting);

        Meetings sameMeetings = new Meetings();
        sameMeetings.addMeeting(defaultMeeting);

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
        Name otherName = new Name("Other Valid Name");

        Meeting otherMeeting;

        try {
            otherMeeting = new Meeting(otherName, otherStartTime, otherEndTime, otherLocation);
        } catch (CommandException e) {
            throw new RuntimeException("Error while creating the meeting: " + e.getMessage(), e);
        }

        Meetings differentMeetings = new Meetings();
        differentMeetings.addMeeting(otherMeeting);

        // different values -> returns false
        assertFalse(meetings.equals(differentMeetings));
        assertFalse(meetings.equals(new Meetings())); // empty meetings
    }
}
