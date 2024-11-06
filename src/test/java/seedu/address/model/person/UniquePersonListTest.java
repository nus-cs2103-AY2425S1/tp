package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void containsName_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.containsName(null));
    }

    @Test
    public void containsName_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.containsName(ALICE.getName()));
    }

    @Test
    public void containsName_personInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.containsName(ALICE.getName()));
    }

    @Test
    public void containsName_personWithLowerCasedNameInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        String nameString = ALICE.getName().toString();
        String lowerCasedNameString = nameString.toLowerCase();
        Name lowerCasedName = new Name(lowerCasedNameString);
        assertTrue(uniquePersonList.containsName(lowerCasedName));
    }

    @Test
    public void containsName_personWithUpperCasedNameInList_returnsTrue() {
        uniquePersonList.add(ALICE);
        String nameString = ALICE.getName().toString();
        String upperCasedNameString = nameString.toUpperCase();
        Name upperCasedName = new Name(upperCasedNameString);
        assertTrue(uniquePersonList.containsName(upperCasedName));
    }

    @Test
    public void containsName_personWithPartOfNameNotInList_returnsFalse() {
        uniquePersonList.add(ALICE);
        String nameString = ALICE.getName().toString();
        String partOfNameString = nameString.substring(0, nameString.length() - 1);
        Name partOfName = new Name(partOfNameString);
        assertFalse(uniquePersonList.containsName(partOfName));
    }

    @Test
    public void containsId_sameId_returnsTrue() {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.containsId(ALICE.getId()));
    }

    @Test
    public void containsId_differentId_returnsFalse() {
        uniquePersonList.add(ALICE);
        assertFalse(uniquePersonList.containsId(ALICE.getId() + 1));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniquePersonList.setPerson(ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(ALICE);
        uniquePersonList.setPerson(ALICE, BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void getPersonsWithName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.getPersonsWithName(null));
    }

    @Test
    public void getPersonsWithName_existingPersonName_returnsPersonList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = uniquePersonList.getPersonsWithName(ALICE.getName());
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        assertEquals(personList, resultList);
    }

    @Test
    public void getPersonsWithName_existingPersonNameLowerCased_returnsPersonList() {
        uniquePersonList.add(ALICE);
        Name lowerCasedName = new Name(ALICE.getName().toString().toLowerCase());
        List<Person> personList = uniquePersonList.getPersonsWithName(lowerCasedName);
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        assertEquals(personList, resultList);
    }

    @Test
    public void getPersonsWithName_existingPersonNameUpperCased_returnsPersonList() {
        uniquePersonList.add(ALICE);
        Name upperCasedName = new Name(ALICE.getName().toString().toUpperCase());
        List<Person> personList = uniquePersonList.getPersonsWithName(upperCasedName);
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        assertEquals(personList, resultList);
    }

    @Test
    public void getPersonsWithName_partOfExistingPersonName_returnsEmptyList() {
        uniquePersonList.add(ALICE);
        String nameString = ALICE.getName().toString();
        String partOfNameString = nameString.substring(0, nameString.length() - 1);
        Name partOfName = new Name(partOfNameString);
        List<Person> personList = uniquePersonList.getPersonsWithName(partOfName);
        List<Person> resultList = new ArrayList<>();
        assertEquals(personList, resultList);
    }

    @Test
    public void getPersonsWithName_nonExistingPersonName_returnsEmptyList() {
        List<Person> personList = uniquePersonList.getPersonsWithName(ALICE.getName());
        List<Person> resultList = new ArrayList<>();
        assertEquals(personList, resultList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void unassignEventFromAllPersons_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.unassignEventFromAllPersons(null));
    }

    @Test
    public void unassignEventFromAllPersons_personsAssignedDeletedEvent() {
        int eventId = MEETING.getEventId();
        Set<Integer> aliceEventIds = ALICE.getEventIds();
        Set<Integer> bobEventIds = BOB.getEventIds();
        ALICE.addEventId(eventId);
        BOB.addEventId(eventId);
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        uniquePersonList.unassignEventFromAllPersons(MEETING);
        assertEquals(aliceEventIds, ALICE.getEventIds());
        assertEquals(bobEventIds, BOB.getEventIds());
    }

    @Test
    public void unassignEventFromAllPersons_personsAssignedDeletedEventAndPersonsNotAssignedDeletedEvent() {
        int eventId = MEETING.getEventId();
        Set<Integer> aliceEventIds = ALICE.getEventIds();
        Set<Integer> bobEventIds = BOB.getEventIds();
        Set<Integer> bensonEventIds = BENSON.getEventIds();
        ALICE.addEventId(eventId);
        BOB.addEventId(eventId);
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        uniquePersonList.add(BENSON);
        uniquePersonList.unassignEventFromAllPersons(MEETING);
        assertEquals(aliceEventIds, ALICE.getEventIds());
        assertEquals(bobEventIds, BOB.getEventIds());
        assertEquals(bensonEventIds, BENSON.getEventIds());
    }

    @Test
    public void unassignEventFromAllPersons_personsNotAssignedDeletedEvent() {
        int eventId = MEETING.getEventId();
        Set<Integer> aliceEventIds = ALICE.getEventIds();
        Set<Integer> bobEventIds = BOB.getEventIds();
        Set<Integer> bensonEventIds = BENSON.getEventIds();
        uniquePersonList.add(ALICE);
        uniquePersonList.add(BOB);
        uniquePersonList.add(BENSON);
        uniquePersonList.unassignEventFromAllPersons(MEETING);
        assertEquals(aliceEventIds, ALICE.getEventIds());
        assertEquals(bobEventIds, BOB.getEventIds());
        assertEquals(bensonEventIds, BENSON.getEventIds());
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
