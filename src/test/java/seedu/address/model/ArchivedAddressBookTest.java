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

public class ArchivedAddressBookTest {
    private final ArchivedAddressBook addressBook = new ArchivedAddressBook();

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
        ReadOnlyAddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(new ArchivedAddressBook(newData), addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ArchivedAddressBookTest.AddressBookStub newData = new ArchivedAddressBookTest.AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ArchivedAddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equals() {
        ArchivedAddressBook archivedFirstAddressBook = new ArchivedAddressBook();
        ArchivedAddressBook archivedFirstAddressBookCopy = new ArchivedAddressBook(archivedFirstAddressBook);

        // same object -> returns true
        assertTrue(archivedFirstAddressBook.equals(archivedFirstAddressBook));

        // same values -> returns true
        assertTrue(archivedFirstAddressBook.equals(archivedFirstAddressBookCopy));

        // different types -> returns false
        assertFalse(archivedFirstAddressBook.equals(1));

        // null -> returns false
        assertFalse(archivedFirstAddressBook.equals(null));
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
