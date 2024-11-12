package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class UniquePersonListTest {

    private final UniquePersonList UNIQUE_PERSON_LIST = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(UNIQUE_PERSON_LIST.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        UNIQUE_PERSON_LIST.add(ALICE);

        assertTrue(UNIQUE_PERSON_LIST.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        UNIQUE_PERSON_LIST.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).buildBuyer();

        assertTrue(UNIQUE_PERSON_LIST.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        UNIQUE_PERSON_LIST.add(ALICE);

        assertThrows(DuplicatePersonException.class, () -> UNIQUE_PERSON_LIST.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> UNIQUE_PERSON_LIST.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        UNIQUE_PERSON_LIST.add(ALICE);
        UNIQUE_PERSON_LIST.setPerson(ALICE, ALICE);

        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        UNIQUE_PERSON_LIST.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).buildBuyer();
        UNIQUE_PERSON_LIST.setPerson(ALICE, editedAlice);

        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        UNIQUE_PERSON_LIST.add(ALICE);
        UNIQUE_PERSON_LIST.setPerson(ALICE, BOB);

        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        UNIQUE_PERSON_LIST.add(ALICE);
        UNIQUE_PERSON_LIST.add(BOB);

        assertThrows(DuplicatePersonException.class, () -> UNIQUE_PERSON_LIST.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> UNIQUE_PERSON_LIST.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        UNIQUE_PERSON_LIST.add(ALICE);
        UNIQUE_PERSON_LIST.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        UNIQUE_PERSON_LIST.add(ALICE);

        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);

        UNIQUE_PERSON_LIST.setPersons(expectedUniquePersonList);

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> UNIQUE_PERSON_LIST.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        UNIQUE_PERSON_LIST.add(ALICE);

        List<Person> personList = Collections.singletonList(BOB);
        UNIQUE_PERSON_LIST.setPersons(personList);

        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);

        assertEquals(expectedUniquePersonList, UNIQUE_PERSON_LIST);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);

        assertThrows(DuplicatePersonException.class, () -> UNIQUE_PERSON_LIST.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> UNIQUE_PERSON_LIST.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(UNIQUE_PERSON_LIST.asUnmodifiableObservableList().toString(), UNIQUE_PERSON_LIST.toString());
    }
}
