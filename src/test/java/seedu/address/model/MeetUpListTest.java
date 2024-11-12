package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_NETWORKING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.exceptions.DuplicateMeetUpException;
import seedu.address.testutil.meetup.MeetUpBuilder;

public class MeetUpListTest {

    private final MeetUpList meetUpList = new MeetUpList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), meetUpList.getMeetUpList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> meetUpList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMeetUpList_replacesData() {
        MeetUpList newData = getTypicalMeetUpList();
        meetUpList.resetData(newData);
        assertEquals(newData, meetUpList);
    }

    @Test
    public void resetData_withDuplicateMeetUps_throwsDuplicateMeetUpException() {
        // Two meet-ups with the same identity fields
        MeetUp editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withInfo(VALID_MEETUP_INFO_NETWORKING).build();
        List<MeetUp> newMeetUps = Arrays.asList(PITCH_MEETUP, editedPitchMeetUp);
        MeetUpListStub newData = new MeetUpListStub(newMeetUps);

        assertThrows(DuplicateMeetUpException.class, () -> meetUpList.resetData(newData));
    }

    @Test
    public void hasMeetUp_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> meetUpList.hasMeetUp(null));
    }

    @Test
    public void hasMeetUp_meetUpNotInMeetUpList_returnsFalse() {
        assertFalse(meetUpList.hasMeetUp(PITCH_MEETUP));
    }

    @Test
    public void hasMeetUp_meetUpInMeetUpList_returnsTrue() {
        meetUpList.addMeetUp(PITCH_MEETUP);
        assertTrue(meetUpList.hasMeetUp(PITCH_MEETUP));
    }

    @Test
    public void hasMeetUp_meetUpWithSameIdentityFieldsInMeetUpList_returnsTrue() {
        meetUpList.addMeetUp(PITCH_MEETUP);
        MeetUp editedPitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withInfo(VALID_MEETUP_INFO_NETWORKING).build();
        assertTrue(meetUpList.hasMeetUp(editedPitchMeetUp));
    }

    @Test
    public void getMeetUpList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> meetUpList.getMeetUpList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = MeetUpList.class.getCanonicalName() + "{meetUps=" + meetUpList.getMeetUpList() + "}";
        assertEquals(expected, meetUpList.toString());
    }

    /**
     * A stub ReadOnlyMeetUpList whose meetUps list can violate interface constraints.
     */
    private static class MeetUpListStub implements ReadOnlyMeetUpList {
        private final ObservableList<MeetUp> meetUps = FXCollections.observableArrayList();

        MeetUpListStub(Collection<MeetUp> meetUps) {
            this.meetUps.setAll(meetUps);
        }

        @Override
        public ObservableList<MeetUp> getMeetUpList() {
            return meetUps;
        }
    }

}
