package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class HistoricalAddressBookTest {

    private final HistoricalAddressBook historicalAddressBook = new HistoricalAddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), historicalAddressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> historicalAddressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        HistoricalAddressBook newData = new HistoricalAddressBook(getTypicalAddressBook());
        assertEquals(newData.getCurrentAddressBook(), getTypicalAddressBook());
        historicalAddressBook.resetData(newData);
        assertEquals(newData.getCurrentAddressBook(), historicalAddressBook.getCurrentAddressBook());
        // Check save()
        assertEquals(newData.getAddressBookHistory().pop(), historicalAddressBook.getCurrentAddressBook());
    }

    @Test
    public void wrapperFunctions_withSimulatedUserInput_valuesAsExpected() {
        HistoricalAddressBook newData = new HistoricalAddressBook(getTypicalAddressBook());
        // ================ SCENARIO: USER WEAVES ADD, REMOVE, CLEAR, UNDO, REDO ===========================
        newData.addPerson(TypicalPersons.HOON);
        newData.addPerson(TypicalPersons.BOB);
        assertTrue(newData.hasPerson(TypicalPersons.HOON));
        assertTrue(newData.hasPerson(TypicalPersons.BOB));
        // User clears
        newData.resetData(new AddressBook());
        assertFalse(newData.hasPerson(TypicalPersons.HOON));
        assertFalse(newData.hasPerson(TypicalPersons.BOB));
        // User undo - undo clear
        newData.undo();
        assertTrue(newData.hasPerson(TypicalPersons.HOON));
        assertTrue(newData.hasPerson(TypicalPersons.BOB));
        // User undo x 2 - remove 2 persons
        newData.undo();
        newData.undo();
        assertEquals(getTypicalAddressBook(), newData.getCurrentAddressBook());
        // Make sure canUndo() is false
        assertFalse(newData.canUndo());
        // User redo - add HOON
        newData.redo();
        assertTrue(newData.hasPerson(TypicalPersons.HOON));
        // User redo - add BOB
        newData.redo();
        assertTrue(newData.hasPerson(TypicalPersons.BOB));
        // User deletes HOON - save() should clear addressBookHistory
        newData.removePerson(TypicalPersons.HOON);
        // Make sure canRedo() is false
        assertFalse(newData.canRedo());
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> historicalAddressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> historicalAddressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(historicalAddressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        historicalAddressBook.addPerson(ALICE);
        assertTrue(historicalAddressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        historicalAddressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(historicalAddressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> historicalAddressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = HistoricalAddressBook.class.getCanonicalName()
                + "{addressBookHistory=" + historicalAddressBook.getAddressBookHistory() + ", "
                + "addressBookUndoHistory=" + historicalAddressBook.getAddressBookUndoHistory() + ", "
                + "currentAddressBook=" + historicalAddressBook.getCurrentAddressBook() + "}";
        assertEquals(expected, historicalAddressBook.toString());
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
