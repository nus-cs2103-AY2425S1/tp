package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MeetingTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingTitle(null));
    }

    @Test
    public void constructor_invalidMeetingTitle_throwsIllegalArgumentException() {
        String invalidMeetingTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new MeetingTitle(invalidMeetingTitle));
    }

    @Test
    public void isValidMeetingTitle() {
        // null meeting title
        assertThrows(NullPointerException.class, () -> MeetingTitle.isValidMeetingTitle(null));

        // invalid meeting title
        assertFalse(MeetingTitle.isValidMeetingTitle("")); // empty string
        assertFalse(MeetingTitle.isValidMeetingTitle(" ")); // spaces only
        assertFalse(MeetingTitle.isValidMeetingTitle("^")); // only non-alphanumeric characters
        assertFalse(MeetingTitle.isValidMeetingTitle("Client Viewing - Admiralty HDB"));
        // contains non-alphanumeric characters

        // valid meeting title
        assertTrue(MeetingTitle.isValidMeetingTitle("clementi apartment lease agreement discussion"));
        // alphabets only
        assertTrue(MeetingTitle.isValidMeetingTitle("12345")); // numbers only
        assertTrue(MeetingTitle.isValidMeetingTitle("12 marine parade rd viewing")); // alphanumeric characters
        assertTrue(MeetingTitle.isValidMeetingTitle("Clementi Apartment Lease Agreement Discussion"));
        // with capital letters
    }

    @Test
    public void equals() {
        MeetingTitle meetingTitle = new MeetingTitle("Valid Meeting Title");

        // same values -> returns true
        assertTrue(meetingTitle.equals(new MeetingTitle("Valid Meeting Title")));

        // same object -> returns true
        assertTrue(meetingTitle.equals(meetingTitle));

        // null -> returns false
        assertFalse(meetingTitle.equals(null));

        // different types -> returns false
        assertFalse(meetingTitle.equals(5.0f));

        // different values -> returns false
        assertFalse(meetingTitle.equals(new MeetingTitle("Other Valid Meeting Title")));
    }
}
