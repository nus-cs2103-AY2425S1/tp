package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MeetingDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingDate(null));
    }

    @Test
    public void constructor_invalidMeetingDate_throwsIllegalArgumentException() {
        String invalidMeetingDate = "invalid-date";
        assertThrows(IllegalArgumentException.class, () -> new MeetingDate(invalidMeetingDate));
    }

    @Test
    public void isValidMeetingDate() {
        // null meeting date
        assertThrows(NullPointerException.class, () -> MeetingDate.isValidMeetingDate(null));

        // invalid meeting dates
        assertFalse(MeetingDate.isValidMeetingDate("")); // empty string
        assertFalse(MeetingDate.isValidMeetingDate(" ")); // spaces only
        assertFalse(MeetingDate.isValidMeetingDate("12-34-5678")); // invalid date components
        assertFalse(MeetingDate.isValidMeetingDate("31-02-2030")); // invalid date (Feb 31st)
        assertFalse(MeetingDate.isValidMeetingDate("01-01-1999")); // invalid date (in the past)
        assertFalse(MeetingDate.isValidMeetingDate("01/01/2030")); // wrong format (slashes)
        assertFalse(MeetingDate.isValidMeetingDate("2030-01-01")); // wrong format (year first)
        assertFalse(MeetingDate.isValidMeetingDate("1-1-2030")); // incorrect format (single digits)

        // valid meeting dates
        assertTrue(MeetingDate.isValidMeetingDate("01-01-2035")); // exactly in dd-MM-yyyy format
        assertTrue(MeetingDate.isValidMeetingDate("29-02-2032")); // leap year check (valid date)
        assertTrue(MeetingDate.isValidMeetingDate("31-12-2030")); // valid date
    }

    @Test
    public void equals() {
        MeetingDate meetingDate = new MeetingDate("01-01-2023");

        // same values -> returns true
        assertTrue(meetingDate.equals(new MeetingDate("01-01-2023")));

        // same object -> returns true
        assertTrue(meetingDate.equals(meetingDate));

        // null -> returns false
        assertFalse(meetingDate.equals(null));

        // different types -> returns false
        assertFalse(meetingDate.equals(5.0f));

        // different values -> returns false
        assertFalse(meetingDate.equals(new MeetingDate("02-01-2023")));
    }
}

