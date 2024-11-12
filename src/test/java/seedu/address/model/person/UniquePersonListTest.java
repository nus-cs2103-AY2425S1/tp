package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.PERSON_BOB;
import static seedu.address.testutil.TypicalPersons.TEACHER_ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TeacherBuilder;

public class UniquePersonListTest {

    private final UniquePersonList<Person> uniquePersonList = new UniquePersonList<>();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(TEACHER_ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(TEACHER_ALICE);
        assertTrue(uniquePersonList.contains(TEACHER_ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(TEACHER_ALICE);
        Person editedAlice = new PersonBuilder(TEACHER_ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(TEACHER_ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(TEACHER_ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, TEACHER_ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(TEACHER_ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(TEACHER_ALICE, TEACHER_ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(TEACHER_ALICE);
        uniquePersonList.setPerson(TEACHER_ALICE, TEACHER_ALICE);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(TEACHER_ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(TEACHER_ALICE);
        Teacher editedAlice = new TeacherBuilder(TEACHER_ALICE).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        uniquePersonList.setPerson(TEACHER_ALICE, editedAlice);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(TEACHER_ALICE);
        uniquePersonList.setPerson(TEACHER_ALICE, PERSON_BOB);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(PERSON_BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(TEACHER_ALICE);
        uniquePersonList.add(PERSON_BOB);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(TEACHER_ALICE, PERSON_BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(TEACHER_ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(TEACHER_ALICE);
        uniquePersonList.remove(TEACHER_ALICE);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList<Person>) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(TEACHER_ALICE);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(PERSON_BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(TEACHER_ALICE);
        List<Person> personList = Collections.singletonList(PERSON_BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList<Person> expectedUniquePersonList = new UniquePersonList<>();
        expectedUniquePersonList.add(PERSON_BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(TEACHER_ALICE, TEACHER_ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
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
