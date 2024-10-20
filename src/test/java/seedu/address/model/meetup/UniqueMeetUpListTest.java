package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_NETWORKING;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.meetup.TypicalMeetUps.NETWORKING_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.PITCH_MEETUP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.meetup.exceptions.DuplicateMeetUpException;
import seedu.address.model.meetup.exceptions.MeetUpNotFoundException;
import seedu.address.testutil.meetup.MeetUpBuilder;

public class UniqueMeetUpListTest {

    private final UniqueMeetUpList uniqueMeetUpList = new UniqueMeetUpList();

    @Test
    public void contains_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.contains(null));
    }

    @Test
    public void contains_meetUpNotInList_returnsFalse() {
        assertFalse(uniqueMeetUpList.contains(PITCH_MEETUP));
    }

    @Test
    public void contains_meetUpInList_returnsTrue() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        assertTrue(uniqueMeetUpList.contains(PITCH_MEETUP));
    }

    @Test
    public void contains_meetUpWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        MeetUp editedpitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withInfo(VALID_MEETUP_INFO_NETWORKING).build();
        assertTrue(uniqueMeetUpList.contains(editedpitchMeetUp));
    }

    @Test
    public void add_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.add(null));
    }

    @Test
    public void add_duplicateMeetUp_throwsDuplicateMeetUpException() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        assertThrows(DuplicateMeetUpException.class, () -> uniqueMeetUpList.add(PITCH_MEETUP));
    }

    @Test
    public void setMeetUp_nullTargetMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.setMeetUp(null, PITCH_MEETUP));
    }

    @Test
    public void setMeetUp_nullEditedMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.setMeetUp(PITCH_MEETUP, null));
    }

    @Test
    public void setMeetUp_targetMeetUpNotInList_throwsMeetUpNotFoundException() {
        assertThrows(MeetUpNotFoundException.class, () -> uniqueMeetUpList.setMeetUp(PITCH_MEETUP, PITCH_MEETUP));
    }

    @Test
    public void setMeetUp_editedMeetUpIsSameMeetUp_success() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        uniqueMeetUpList.setMeetUp(PITCH_MEETUP, PITCH_MEETUP);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        expectedUniqueMeetUpList.add(PITCH_MEETUP);
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUp_editedMeetUpHasSameIdentity_success() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        MeetUp editedpitchMeetUp = new MeetUpBuilder(PITCH_MEETUP).withInfo(VALID_MEETUP_INFO_NETWORKING).build();
        uniqueMeetUpList.setMeetUp(PITCH_MEETUP, editedpitchMeetUp);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        expectedUniqueMeetUpList.add(editedpitchMeetUp);
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUp_editedMeetUpHasDifferentIdentity_success() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        uniqueMeetUpList.setMeetUp(PITCH_MEETUP, NETWORKING_MEETUP);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        expectedUniqueMeetUpList.add(NETWORKING_MEETUP);
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUp_editedMeetUpHasNonUniqueIdentity_throwsDuplicateMeetUpException() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        uniqueMeetUpList.add(NETWORKING_MEETUP);
        assertThrows(DuplicateMeetUpException.class, () -> uniqueMeetUpList.setMeetUp(PITCH_MEETUP, NETWORKING_MEETUP));
    }

    @Test
    public void remove_nullMeetUp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.remove(null));
    }

    @Test
    public void remove_meetUpDoesNotExist_throwsMeetUpNotFoundException() {
        assertThrows(MeetUpNotFoundException.class, () -> uniqueMeetUpList.remove(PITCH_MEETUP));
    }

    @Test
    public void remove_existingMeetUp_removesMeetUp() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        uniqueMeetUpList.remove(PITCH_MEETUP);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUps_nullUniqueMeetUpList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.setMeetUps((UniqueMeetUpList) null));
    }

    @Test
    public void setMeetUps_uniqueMeetUpList_replacesOwnListWithProvidedUniqueMeetUpList() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        expectedUniqueMeetUpList.add(NETWORKING_MEETUP);
        uniqueMeetUpList.setMeetUps(expectedUniqueMeetUpList);
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUps_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMeetUpList.setMeetUps((List<MeetUp>) null));
    }

    @Test
    public void setMeetUps_list_replacesOwnListWithProvidedList() {
        uniqueMeetUpList.add(PITCH_MEETUP);
        List<MeetUp> meetUpList = Collections.singletonList(NETWORKING_MEETUP);
        uniqueMeetUpList.setMeetUps(meetUpList);
        UniqueMeetUpList expectedUniqueMeetUpList = new UniqueMeetUpList();
        expectedUniqueMeetUpList.add(NETWORKING_MEETUP);
        assertEquals(expectedUniqueMeetUpList, uniqueMeetUpList);
    }

    @Test
    public void setMeetUps_listWithDuplicateMeetUps_throwsDuplicateMeetUpException() {
        List<MeetUp> listWithDuplicateMeetUps = Arrays.asList(PITCH_MEETUP, PITCH_MEETUP);
        assertThrows(DuplicateMeetUpException.class, () -> uniqueMeetUpList.setMeetUps(listWithDuplicateMeetUps));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueMeetUpList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueMeetUpList.asUnmodifiableObservableList().toString(), uniqueMeetUpList.toString());
    }
}
