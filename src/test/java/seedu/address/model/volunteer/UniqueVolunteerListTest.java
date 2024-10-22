package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.volunteer.exceptions.DuplicateVolunteerException;
import seedu.address.model.volunteer.exceptions.VolunteerNotFoundException;
import seedu.address.testutil.VolunteerBuilder;

public class UniqueVolunteerListTest {

    private final UniqueVolunteerList uniqueVolunteerList = new UniqueVolunteerList();

    @Test
    public void contains_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.contains(null));
    }

    @Test
    public void contains_volunteerNotInList_returnsFalse() {
        assertFalse(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_volunteerInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        assertTrue(uniqueVolunteerList.contains(ALICE));
    }

    @Test
    public void contains_volunteerWithSameIdentityFieldsInList_returnsTrue() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAmy = new VolunteerBuilder(ALICE).withPhone("98765432").build();
        assertTrue(uniqueVolunteerList.contains(editedAmy));
    }

    @Test
    public void add_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.add(null));
    }

    @Test
    public void add_duplicateVolunteer_throwsDuplicateVolunteerException() {
        uniqueVolunteerList.add(ALICE);
        assertThrows(DuplicateVolunteerException.class, () -> uniqueVolunteerList.add(ALICE));
    }

    @Test
    public void setVolunteer_nullTargetVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteer(null, ALICE));
    }

    @Test
    public void setVolunteer_nullEditedVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVolunteerList.setVolunteer(ALICE, null));
    }

    @Test
    public void setVolunteer_targetVolunteerNotInList_throwsVolunteerNotFoundException() {
        assertThrows(VolunteerNotFoundException.class, () -> uniqueVolunteerList.setVolunteer(ALICE, ALICE));
    }

    @Test
    public void setVolunteer_editedVolunteerIsSameVolunteer_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setVolunteer(ALICE, ALICE);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(ALICE);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteer_editedVolunteerHasSameIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        Volunteer editedAmy = new VolunteerBuilder(ALICE).withPhone("98765432").build();
        uniqueVolunteerList.setVolunteer(ALICE, editedAmy);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(editedAmy);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }

    @Test
    public void setVolunteer_editedVolunteerHasDifferentIdentity_success() {
        uniqueVolunteerList.add(ALICE);
        uniqueVolunteerList.setVolunteer(ALICE, BOB);
        UniqueVolunteerList expectedUniqueVolunteerList = new UniqueVolunteerList();
        expectedUniqueVolunteerList.add(BOB);
        assertEquals(expectedUniqueVolunteerList, uniqueVolunteerList);
    }
}
