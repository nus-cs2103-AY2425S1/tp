package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETINGDATE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETINGTITLE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETINGTITLE_BEDOK;
import static seedu.address.testutil.TypicalMeetings.MEETING_ADMIRALTY;
import static seedu.address.testutil.TypicalMeetings.MEETING_BEDOK;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MeetingBuilder;

public class MeetingTest {

    @Test
    public void isSameMeeting() {
        // same object -> returns true
        assertTrue(MEETING_ADMIRALTY.isSameMeeting(MEETING_ADMIRALTY));

        // null -> returns false
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(null));

        // same name, all other attributes different -> returns false
        Meeting editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY).withMeetingTitle(VALID_MEETINGTITLE_BEDOK)
                .withMeetingDate(VALID_MEETINGDATE_BEDOK).build();
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(editedAdmiralty));

        // different meeting title -> returns false
        editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY).withMeetingTitle(VALID_MEETINGTITLE_BEDOK).build();
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(editedAdmiralty));

        // meeting title differs in case, all other attributes same -> returns false
        editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY)
                .withMeetingTitle(VALID_MEETINGTITLE_ADMIRALTY.toLowerCase()).build();
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(editedAdmiralty));
        // meeting title has trailing spaces, all other attributes same -> returns false
        String titleWithTrailingSpaces = VALID_MEETINGTITLE_ADMIRALTY + " ";
        editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY)
                .withMeetingTitle(titleWithTrailingSpaces).build();
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(editedAdmiralty));

        // different meeting date -> returns false
        editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY).withMeetingDate(VALID_MEETINGDATE_BEDOK).build();
        assertFalse(MEETING_ADMIRALTY.isSameMeeting(editedAdmiralty));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meeting admiraltyCopy = new MeetingBuilder(MEETING_ADMIRALTY).build();
        assertTrue(MEETING_ADMIRALTY.equals(admiraltyCopy));

        // same object -> returns true
        assertTrue(MEETING_ADMIRALTY.equals(MEETING_ADMIRALTY));

        // null -> returns false
        assertFalse(MEETING_ADMIRALTY.equals(null));

        // different type -> returns false
        assertFalse(MEETING_ADMIRALTY.equals(5));

        // different person -> returns false
        assertFalse(MEETING_ADMIRALTY.equals(MEETING_BEDOK));

        // different meeting title -> returns false
        Meeting editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY)
                .withMeetingTitle(VALID_MEETINGTITLE_BEDOK).build();
        assertFalse(MEETING_ADMIRALTY.equals(editedAdmiralty));

        // different meeting date -> returns false
        editedAdmiralty = new MeetingBuilder(MEETING_ADMIRALTY).withMeetingDate(VALID_MEETINGDATE_BEDOK).build();
        assertFalse(MEETING_ADMIRALTY.equals(editedAdmiralty));
    }

    @Test
    public void toStringMethod() {
        String expected = Meeting.class.getCanonicalName() + "{meetingTitle=" + MEETING_ADMIRALTY.getMeetingTitle()
                + ", meetingDate=" + MEETING_ADMIRALTY.getMeetingDate() + "}";
        assertEquals(expected, MEETING_ADMIRALTY.toString());
    }
}
