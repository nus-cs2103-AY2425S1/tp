package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_1A;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueSortedPersonListTest {

    private final UniqueSortedPersonList uniqueSortedPersonList = new UniqueSortedPersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueSortedPersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueSortedPersonList.add(ALICE);
        assertTrue(uniqueSortedPersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSortedPersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withStudyGroupTags(VALID_STUDY_GROUP_TAG_1A)
                .build();
        assertTrue(uniqueSortedPersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueSortedPersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueSortedPersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueSortedPersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueSortedPersonList.add(ALICE);
        uniqueSortedPersonList.setPerson(ALICE, ALICE);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        expectedUniqueSortedPersonList.add(ALICE);
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueSortedPersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withStudyGroupTags(VALID_STUDY_GROUP_TAG_1A)
                .build();
        uniqueSortedPersonList.setPerson(ALICE, editedAlice);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        expectedUniqueSortedPersonList.add(editedAlice);
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueSortedPersonList.add(ALICE);
        uniqueSortedPersonList.setPerson(ALICE, BOB);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        expectedUniqueSortedPersonList.add(BOB);
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueSortedPersonList.add(ALICE);
        uniqueSortedPersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueSortedPersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueSortedPersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueSortedPersonList.add(ALICE);
        uniqueSortedPersonList.remove(ALICE);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList
                .setPersons((UniqueSortedPersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueSortedPersonList.add(ALICE);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        expectedUniqueSortedPersonList.add(BOB);
        uniqueSortedPersonList.setPersons(expectedUniqueSortedPersonList);
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSortedPersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueSortedPersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueSortedPersonList.setPersons(personList);
        UniqueSortedPersonList expectedUniqueSortedPersonList = new UniqueSortedPersonList();
        expectedUniqueSortedPersonList.add(BOB);
        assertEquals(expectedUniqueSortedPersonList, uniqueSortedPersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueSortedPersonList
                .setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueSortedPersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueSortedPersonList
                .asUnmodifiableObservableList().toString(), uniqueSortedPersonList.toString());
    }
}
