package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.ConflictMeetingException;
import seedu.address.model.person.exceptions.DuplicateMeetingException;
import seedu.address.model.person.exceptions.MeetingNotFoundException;
import seedu.address.testutil.MeetingBuilder;

public class UniqueMeetingListTest {

    private final UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();
    private final MeetingBuilder meetingBuilder = new MeetingBuilder();
    private final Meeting meeting = meetingBuilder.build();
    private final Meeting conflictingMeeting = meetingBuilder
            .withName("Conflicting Meeting").build();
    private final Meeting noConflictMeeting = meetingBuilder
            .withName("No Conflict Meeting")
            .withDate(meeting.getMeetingDate().plusDays(1)).build();

    {
        uniqueMeetingList.add(meeting);
    }

    @Test
    public void add_alreadyContainsMeeting_throwsDuplicateMeetingException() {
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList.add(meeting));
    }

    @Test
    public void add_conflictMeeting_throwsConflictMeetingException() {
        assertThrows(ConflictMeetingException.class, () -> uniqueMeetingList
                .add(conflictingMeeting));
    }

    @Test
    public void setMeeting_targetMeetingNotInList_throwsMeetingNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () -> uniqueMeetingList
                .setMeeting(conflictingMeeting, meeting));
    }

    @Test
    public void setMeeting_editedMeetingExistsInList_throwsDuplicateMeetingException() {
        uniqueMeetingList.add(noConflictMeeting);
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList
                .setMeeting(meeting, noConflictMeeting));
    }

    @Test
    public void remove_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList.remove(null));
    }

    @Test
    public void remove_meetingNotInList_throwsMeetingNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () -> uniqueMeetingList
                .remove(conflictingMeeting));
    }

    @Test
    public void remove_meetingInList_success() {
        uniqueMeetingList.remove(meeting);
    }

    @Test
    public void setMeetings_nullTargetMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetingList
                .setMeeting(null, meeting));
    }

    @Test
    public void setMeetings_duplicateMeetingsInList_throwsDuplicateMeetingException() {
        assertThrows(DuplicateMeetingException.class, () -> uniqueMeetingList
                .setMeetings(List.of(meeting, meeting)));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equals_false() {
        // different types -> returns false
        assertFalse(uniqueMeetingList.equals(1));
        // null -> returns false
        assertFalse(uniqueMeetingList.equals(null));
        // different list -> returns false
        UniqueMeetingList secondUniqueMeetingList = new UniqueMeetingList();
        secondUniqueMeetingList.add(meeting);
        secondUniqueMeetingList.add(noConflictMeeting);
        assertFalse(uniqueMeetingList.equals(secondUniqueMeetingList));
    }

    @Test
    public void equals_true() {
        // same object -> returns true
        assertTrue(uniqueMeetingList.equals(uniqueMeetingList));
        // same values -> returns true
        UniqueMeetingList secondUniqueMeetingList = new UniqueMeetingList();
        secondUniqueMeetingList.add(meeting);
        assertTrue(uniqueMeetingList.equals(secondUniqueMeetingList));
        // hashCode equals
        assertTrue(uniqueMeetingList.hashCode() == secondUniqueMeetingList.hashCode());
    }
}
