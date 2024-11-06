package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_ADDED_PERSON_ALEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_ADDED_PERSON_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_FROM_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_FROM_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_SUBJECT_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_SUBJECT_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_TO_NETWORKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_TO_PITCH;
import static seedu.address.testutil.meetup.TypicalMeetUps.NETWORKING_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.meetup.MeetUpBuilder;

public class MeetUpTest {

    @Test
    public void isSameMeetUp() {
        // same object -> returns true
        assertTrue(PITCH_MEETUP.isSameMeetUp(PITCH_MEETUP));

        // null -> returns false
        assertFalse(PITCH_MEETUP.isSameMeetUp(null));

        // name, from, to same but info different -> returns true
        MeetUp editedNetWorkingMeetUp = new MeetUpBuilder(NETWORKING_MEETUP)
                .withSubject(VALID_MEETUP_SUBJECT_PITCH)
                .withFrom(VALID_MEETUP_FROM_PITCH)
                .withTo(VALID_MEETUP_TO_PITCH)
                .withAddedBuyers(VALID_MEETUP_ADDED_PERSON_ALEX).build();
        assertTrue(PITCH_MEETUP.isSameMeetUp(editedNetWorkingMeetUp));

        // name differs in case, all other attributes same -> returns false
        editedNetWorkingMeetUp = new MeetUpBuilder(NETWORKING_MEETUP)
                .withSubject(VALID_MEETUP_SUBJECT_NETWORKING.toLowerCase()).build();
        assertFalse(NETWORKING_MEETUP.isSameMeetUp(editedNetWorkingMeetUp));

        // name have trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_MEETUP_SUBJECT_NETWORKING + " ";
        editedNetWorkingMeetUp = new MeetUpBuilder(NETWORKING_MEETUP).withSubject(nameWithTrailingSpaces).build();
        assertFalse(NETWORKING_MEETUP.isSameMeetUp(editedNetWorkingMeetUp));
    }

    @Test
    public void hasValidToFrom() {
        // to after from returns true
        MeetUp validMeetUp = new MeetUpBuilder(PITCH_MEETUP).build();
        assertTrue(validMeetUp.hasValidToFrom());

        // to same as from returns false
        MeetUp invalidMeetUp1 = new MeetUpBuilder(PITCH_MEETUP).withTo(VALID_MEETUP_FROM_PITCH).build();
        assertFalse(invalidMeetUp1.hasValidToFrom());

        // to before from returns false
        MeetUp invalidMeetUp2 = new MeetUpBuilder(PITCH_MEETUP).withFrom("2024-09-11 13:30").build();
        assertFalse(invalidMeetUp2.hasValidToFrom());
    }

    @Test
    public void equals() {
        // same values -> returns true
        MeetUp pitchMeetUpCopy = new MeetUpBuilder(PITCH_MEETUP).build();
        assertTrue(PITCH_MEETUP.equals(pitchMeetUpCopy));

        // same object -> returns true
        assertTrue(PITCH_MEETUP.equals(PITCH_MEETUP));

        // null -> returns false
        assertFalse(PITCH_MEETUP.equals(null));

        // different type -> returns false
        assertFalse(PITCH_MEETUP.equals(5));

        // different buyer -> returns false
        assertFalse(PITCH_MEETUP.equals(NETWORKING_MEETUP));

        // different name -> returns false
        MeetUp editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withSubject(VALID_MEETUP_SUBJECT_NETWORKING).build();
        assertFalse(PITCH_MEETUP.equals(editedPitchMeetUp));

        // different info -> returns false
        editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withInfo(VALID_MEETUP_INFO_NETWORKING).build();
        assertFalse(PITCH_MEETUP.equals(editedPitchMeetUp));

        // different from -> returns false
        editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withFrom(VALID_MEETUP_FROM_NETWORKING).build();
        assertFalse(PITCH_MEETUP.equals(editedPitchMeetUp));

        // different to -> returns false
        editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withTo(VALID_MEETUP_TO_NETWORKING).build();
        assertFalse(PITCH_MEETUP.equals(editedPitchMeetUp));

        // different tags -> returns false
        editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withAddedBuyers(VALID_MEETUP_ADDED_PERSON_BETTY).build();
        assertFalse(PITCH_MEETUP.equals(editedPitchMeetUp));
    }

    @Test
    public void toStringMethod() {
        String expected = MeetUp.class.getCanonicalName() + "{subject=" + PITCH_MEETUP.getSubject() + ", info="
                + PITCH_MEETUP.getInfo() + ", from=" + PITCH_MEETUP.getFrom() + ", to=" + PITCH_MEETUP.getTo()
                + ", addedBuyers=" + PITCH_MEETUP.getAddedBuyers() + "}";
        assertEquals(expected, PITCH_MEETUP.toString());
    }
}
