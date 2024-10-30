package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGuests.AVA;
import static seedu.address.testutil.TypicalVendors.BRIAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(AVA));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(AVA);
        assertTrue(uniquePersonList.contains(AVA));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(AVA);
        Person editedAva = new PersonBuilder(AVA).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniquePersonList.contains(editedAva));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicateGuest_throwsDuplicatePersonException() {
        uniquePersonList.add(AVA);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(AVA));
    }

    @Test
    public void add_duplicateVendor_throwsDuplicatePersonException() {
        uniquePersonList.add(BRIAN);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(BRIAN));
    }


    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, AVA));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(AVA, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(AVA, AVA));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(AVA);
        uniquePersonList.setPerson(AVA, AVA);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(AVA);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(AVA);
        Person editedAva = new PersonBuilder(AVA).withTags(VALID_TAG_HUSBAND).build();
        uniquePersonList.setPerson(AVA, editedAva);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAva);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(AVA);
        uniquePersonList.setPerson(AVA, BRIAN);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BRIAN);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(AVA);
        uniquePersonList.add(BRIAN);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(AVA, BRIAN));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(AVA));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(AVA);
        uniquePersonList.remove(AVA);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(AVA);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BRIAN);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(AVA);
        List<Person> personList = Collections.singletonList(BRIAN);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BRIAN);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicateGuests_throwsDuplicatePersonException() {
        List<Person> listWithDuplicateGuests = Arrays.asList(AVA, AVA);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicateGuests));
    }

    @Test
    public void setPersons_listWithDuplicateVendors_throwsDuplicatePersonException() {
        List<Person> listWithDuplicateVendors = Arrays.asList(BRIAN, BRIAN);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicateVendors));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniquePersonList.asUnmodifiableObservableList().toString(), uniquePersonList.toString());
    }
}
