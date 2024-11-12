package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalMeetings;
import seedu.address.testutil.TypicalPersons;

public class ScheduleListTest {

    private final ScheduleList scheduleList = (ScheduleList) TypicalMeetings.getTypicalMeetings();

    @Test
    public void removeMeeting_expected() {
        ScheduleList expectedScheduleList = new ScheduleList();
        expectedScheduleList.addMeeting(TypicalMeetings.TEAM_SYNC);
        expectedScheduleList.addMeeting(TypicalMeetings.PROJECT_DISCUSSION);
        scheduleList.removeMeeting(TypicalMeetings.CLIENT_REVIEW);
        assertEquals(expectedScheduleList, scheduleList);
    }

    @Test
    public void equals_true() {
        // same object -> returns true
        assertTrue(scheduleList.equals(scheduleList));
        // same values -> returns true
        ScheduleList scheduleListCopy = (ScheduleList) TypicalMeetings.getTypicalMeetings();
        assertTrue(scheduleList.equals(scheduleListCopy));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals_false() {
        // different types -> returns false
        assertFalse(scheduleList.equals(1));
        // null -> returns false
        assertFalse(scheduleList.equals(null));
        // different date -> returns false
        ScheduleList otherScheduleList = new ScheduleList();
        otherScheduleList.addMeeting(TypicalMeetings.TEAM_SYNC);
        assertFalse(scheduleList.equals(otherScheduleList));
    }

    @Test
    public void hashCode_equals() {
        ScheduleList scheduleListCopy = (ScheduleList) TypicalMeetings.getTypicalMeetings();
        assertEquals(scheduleList.hashCode(), scheduleListCopy.hashCode());
    }

    @Test
    public void hasPerson_personInList_returnsTrue() {
        assertTrue(scheduleList.hasPersonInMeeting(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personNotInList_returnsFalse() {
        assertFalse(scheduleList.hasPersonInMeeting(TypicalPersons.IDA));
    }
}
