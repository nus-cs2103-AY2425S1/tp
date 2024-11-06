package seedu.address.model.pet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.owner.Owner;
import seedu.address.model.owner.exceptions.DuplicateOwnerException;
import seedu.address.model.owner.exceptions.OwnerNotFoundException;
import seedu.address.testutil.OwnerBuilder;

public class LinkedOwnerListTest {

    private final LinkedOwnerList linkedOwnerList = new LinkedOwnerList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(linkedOwnerList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        linkedOwnerList.add(ALICE);
        assertTrue(linkedOwnerList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        linkedOwnerList.add(ALICE);
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(linkedOwnerList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        linkedOwnerList.add(ALICE);
        assertThrows(DuplicateOwnerException.class, () -> linkedOwnerList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.setOwner(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.setOwner(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(OwnerNotFoundException.class, () -> linkedOwnerList.setOwner(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        linkedOwnerList.add(ALICE);
        linkedOwnerList.setOwner(ALICE, ALICE);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        expectedlinkedPetList.add(ALICE);
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void resetList_success() {
        linkedOwnerList.add(ALICE);
        linkedOwnerList.resetList();
        assertEquals(linkedOwnerList.getList().size(), 0);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        linkedOwnerList.add(ALICE);
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        linkedOwnerList.setOwner(ALICE, editedAlice);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        expectedlinkedPetList.add(editedAlice);
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        linkedOwnerList.add(ALICE);
        linkedOwnerList.setOwner(ALICE, BOB);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        expectedlinkedPetList.add(BOB);
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(OwnerNotFoundException.class, () -> linkedOwnerList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        linkedOwnerList.add(ALICE);
        linkedOwnerList.remove(ALICE);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void setPersons_nulllinkedPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.setOwners((LinkedOwnerList) null));
    }

    @Test
    public void setPersons_linkedPersonList_replacesOwnListWithProvidedlinkedPersonList() {
        linkedOwnerList.add(ALICE);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        expectedlinkedPetList.add(BOB);
        linkedOwnerList.setOwners(expectedlinkedPetList);
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> linkedOwnerList.setOwners((List<Owner>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        linkedOwnerList.add(ALICE);
        List<Owner> personList = Collections.singletonList(BOB);
        linkedOwnerList.setOwners(personList);
        LinkedOwnerList expectedlinkedPetList = new LinkedOwnerList();
        expectedlinkedPetList.add(BOB);
        assertEquals(expectedlinkedPetList, linkedOwnerList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Owner> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateOwnerException.class, () -> linkedOwnerList.setOwners(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> linkedOwnerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(linkedOwnerList.asUnmodifiableObservableList().toString(), linkedOwnerList.toString());
    }
}
