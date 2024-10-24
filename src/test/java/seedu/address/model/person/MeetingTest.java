package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class MeetingTest {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
    private LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
    private String location = "A Valid Location";
    private Name name = new Name("A Valid Name");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Meeting(name, startTime, endTime, null));
        assertThrows(NullPointerException.class, () -> new Meeting(name, startTime, null, location));
        assertThrows(NullPointerException.class, () -> new Meeting(name, null, endTime, location));
        assertThrows(NullPointerException.class, () -> new Meeting(null, startTime, endTime, location));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(CommandException.class, () -> new Meeting(name, startTime, endTime, invalidLocation));
    }

    @Test
    public void test_isValidStartAndEndTime() {
        LocalDateTime otherStartTime = LocalDateTime.parse("20-12-2024 00:00", formatter);
        LocalDateTime otherEndTime = LocalDateTime.parse("20-12-2024 01:00", formatter);

        // invalid endTime before startTime
        assertFalse(Meeting.isValidStartAndEndTime(otherEndTime, otherStartTime)); // place endTime before startTime

        // valid startTime and endTime
        assertTrue(Meeting.isValidStartAndEndTime(otherStartTime, otherEndTime)); // place endTime before startTime
    }

    @Test
    public void test_isOverlap() {
        LocalDateTime time1030 = LocalDateTime.parse("30-07-2024 10:30", formatter);
        LocalDateTime time1045 = LocalDateTime.parse("30-07-2024 10:45", formatter);
        LocalDateTime time1100 = LocalDateTime.parse("30-07-2024 11:00", formatter);
        LocalDateTime time1130 = LocalDateTime.parse("30-07-2024 11:30", formatter);
        LocalDateTime time1145 = LocalDateTime.parse("30-07-2024 11:45", formatter);
        LocalDateTime time1200 = LocalDateTime.parse("30-07-2024 12:00", formatter);

        try {
            Meeting defaultMeeting = new Meeting(name, time1045, time1145, location);

            // overlapping meeting timings
            assertTrue(defaultMeeting.isOverlap(new Meeting(name, time1030, time1100, location)));
            assertTrue(defaultMeeting.isOverlap(new Meeting(name, time1130, time1200, location)));
            assertTrue(defaultMeeting.isOverlap(new Meeting(name, time1100, time1130, location)));
            assertTrue(defaultMeeting.isOverlap(new Meeting(name, time1030, time1145, location)));
            assertTrue(defaultMeeting.isOverlap(new Meeting(name, time1045, time1200, location)));

            // valid timings
            assertFalse(defaultMeeting.isOverlap(new Meeting(name, time1030, time1045, location))); // after
            assertFalse(defaultMeeting.isOverlap(new Meeting(name, time1145, time1200, location))); //  before
        } catch (CommandException e) {
            throw new RuntimeException("Error while creating the meeting: " + e.getMessage(), e);
        }

    }

    @Test
    public void test_isValidLocation() {
        // null location
        assertThrows(NullPointerException.class, () -> Meeting.isValidLocation(null));

        // invalid location
        assertFalse(Meeting.isValidLocation("")); // empty string
        assertFalse(Meeting.isValidLocation(" ")); // spaces only

        // valid location
        assertTrue(Meeting.isValidLocation("Whitley Road, Scoots Street, #01-355"));
        assertTrue(Meeting.isValidLocation("-")); // one character
        assertTrue(Meeting.isValidLocation("Re Inc, 4321 Vendor's St; Gum Gum Island SBY 345000; ID")); // long address
    }

    @Test
    public void test_equals() {
        try {
            Meeting meeting = new Meeting(name, startTime, endTime, location);

            // same values -> returns true
            assertTrue(meeting.equals(new Meeting(name, startTime, endTime, location)));

            // same object -> returns true
            assertTrue(meeting.equals(meeting));

            // null -> returns false
            assertFalse(meeting.equals(null));

            // different types -> returns false
            assertFalse(meeting.equals(5.0f));

            LocalDateTime otherStartTime = LocalDateTime.parse("30-01-2024 11:00", formatter);
            LocalDateTime otherEndTime = LocalDateTime.parse("30-12-2024 12:00", formatter);
            String otherLocation = "Other Valid Location";
            Name otherName = new Name("Other Valid Name");

            // different values -> returns false
            assertFalse(meeting.equals(new Meeting(otherName, startTime, endTime, location)));
            assertFalse(meeting.equals(new Meeting(name, otherStartTime, endTime, location)));
            assertFalse(meeting.equals(new Meeting(name, startTime, otherEndTime, location)));
            assertFalse(meeting.equals(new Meeting(name, startTime, endTime, otherLocation)));

        } catch (CommandException e) {
            throw new RuntimeException("Error while creating the meeting: " + e.getMessage(), e);
        }
    }
}
