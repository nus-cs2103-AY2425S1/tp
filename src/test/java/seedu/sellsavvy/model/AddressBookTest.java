package seedu.sellsavvy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalPersons.ALICE;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.model.person.exceptions.DuplicatePersonException;
import seedu.sellsavvy.model.person.exceptions.PersonNotFoundException;
import seedu.sellsavvy.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void findEquivalentPerson_modelContainsEquivalentPerson() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        AddressBook addressBook2 = addressBook1.createCopy();
        Person selectedPerson = addressBook1.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person selectedPersonCopy = addressBook2.findEquivalentPerson(selectedPerson);
        assertNotSame(selectedPersonCopy, selectedPerson);
        assertEquals(selectedPersonCopy, selectedPerson);
    }

    @Test
    public void findEquivalentPerson_modelDoesNotContainsEquivalentPerson() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        Person selectedPerson = addressBook1.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person differentPerson = new PersonBuilder(selectedPerson).withName(VALID_NAME_BOB).build();
        assertThrows(PersonNotFoundException.class, () -> addressBook.findEquivalentPerson(differentPerson));
    }

    @Test
    public void findEquivalentPerson_nullInput_throwsAssertionError() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        assertThrows(AssertionError.class, () -> addressBook1.findEquivalentPerson(null));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

    }

}
