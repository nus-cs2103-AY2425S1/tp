package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class PersonListTest {

    private final PersonList personList = new PersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(personList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        personList.add(ALICE);
        assertTrue(personList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        personList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(personList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.add(null));
    }

    @Test
    public void add_duplicatePerson_success() {
        personList.add(ALICE);
        personList.add(ALICE);

        assertEquals(personList.asUnmodifiableObservableList(), Arrays.asList(new Person[]{ALICE, ALICE}));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> personList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        personList.add(ALICE);
        personList.setPerson(ALICE, ALICE);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(ALICE);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        personList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        personList.setPerson(ALICE, editedAlice);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(editedAlice);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        personList.add(ALICE);
        personList.setPerson(ALICE, BOB);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_success() {
        personList.add(ALICE);
        personList.add(BOB);

        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        expectedPersonList.add(BOB);

        personList.setPerson(ALICE, BOB);

        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> personList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        personList.add(ALICE);
        personList.remove(ALICE);
        PersonList expectedPersonList = new PersonList();
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPersons_nullPersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPersons((PersonList) null));
    }

    @Test
    public void setPersons_personList_replacesOwnListWithProvidedPersonList() {
        personList.add(ALICE);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        personList.setPersons(expectedPersonList);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        personList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        this.personList.setPersons(personList);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        assertEquals(expectedPersonList, this.personList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_success() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        personList.setPersons(listWithDuplicatePersons);

        assertEquals(listWithDuplicatePersons, personList.asUnmodifiableObservableList());
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> personList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(personList.asUnmodifiableObservableList().toString(), personList.toString());
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(personList.asUnmodifiableObservableList().hashCode(), personList.hashCode());
    }

    @Test
    public void equals() {
        assertTrue(personList.equals(personList));
        assertFalse(personList.equals(null));
    }
}
