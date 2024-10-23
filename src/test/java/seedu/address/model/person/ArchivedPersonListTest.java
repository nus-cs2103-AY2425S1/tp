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

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class ArchivedPersonListTest {

    private final ArchivedPersonList archivedPersonList = new ArchivedPersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedPersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(archivedPersonList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        archivedPersonList.addArchivedPerson(ALICE);
        assertTrue(archivedPersonList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        archivedPersonList.addArchivedPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(archivedPersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedPersonList.addArchivedPerson(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        archivedPersonList.addArchivedPerson(ALICE);
        assertThrows(DuplicatePersonException.class, () -> archivedPersonList.addArchivedPerson(ALICE));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedPersonList.removeArchivedPerson(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> archivedPersonList.removeArchivedPerson(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        archivedPersonList.addArchivedPerson(ALICE);
        archivedPersonList.removeArchivedPerson(ALICE);
        ArchivedPersonList expectedArchivedPersonList = new ArchivedPersonList();
        assertEquals(expectedArchivedPersonList, archivedPersonList);
    }

    @Test
    public void setArchivedPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archivedPersonList.setArchivedPersons(null));
    }

    @Test
    public void setArchivedPersons_list_replacesOwnListWithProvidedList() {
        archivedPersonList.addArchivedPerson(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        archivedPersonList.setArchivedPersons(personList);
        ArchivedPersonList expectedArchivedPersonList = new ArchivedPersonList();
        expectedArchivedPersonList.addArchivedPerson(BOB);
        assertEquals(expectedArchivedPersonList, archivedPersonList);
    }

    @Test
    public void setArchivedPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () ->
                archivedPersonList.setArchivedPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> archivedPersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(archivedPersonList.asUnmodifiableObservableList().toString(), archivedPersonList.toString());
    }
}
