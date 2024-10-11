package seedu.address.model.owner;

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

import seedu.address.model.owner.exceptions.DuplicateOwnerException;
import seedu.address.model.owner.exceptions.OwnerNotFoundException;
import seedu.address.testutil.OwnerBuilder;

public class UniqueOwnerListTest {

    private final UniqueOwnerList uniqueOwnerList = new UniqueOwnerList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueOwnerList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueOwnerList.add(ALICE);
        assertTrue(uniqueOwnerList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueOwnerList.add(ALICE);
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(uniqueOwnerList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueOwnerList.add(ALICE);
        assertThrows(DuplicateOwnerException.class, () -> uniqueOwnerList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.setOwner(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.setOwner(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(OwnerNotFoundException.class, () -> uniqueOwnerList.setOwner(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueOwnerList.add(ALICE);
        uniqueOwnerList.setOwner(ALICE, ALICE);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueOwnerList.add(ALICE);
        Owner editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        uniqueOwnerList.setOwner(ALICE, editedAlice);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueOwnerList.add(ALICE);
        uniqueOwnerList.setOwner(ALICE, BOB);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueOwnerList.add(ALICE);
        uniqueOwnerList.add(BOB);
        assertThrows(DuplicateOwnerException.class, () -> uniqueOwnerList.setOwner(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(OwnerNotFoundException.class, () -> uniqueOwnerList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueOwnerList.add(ALICE);
        uniqueOwnerList.remove(ALICE);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.setOwners((UniqueOwnerList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueOwnerList.add(ALICE);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        expectedUniquePersonList.add(BOB);
        uniqueOwnerList.setOwners(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOwnerList.setOwners((List<Owner>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueOwnerList.add(ALICE);
        List<Owner> personList = Collections.singletonList(BOB);
        uniqueOwnerList.setOwners(personList);
        UniqueOwnerList expectedUniquePersonList = new UniqueOwnerList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueOwnerList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Owner> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateOwnerException.class, () -> uniqueOwnerList.setOwners(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueOwnerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueOwnerList.asUnmodifiableObservableList().toString(), uniqueOwnerList.toString());
    }
}
