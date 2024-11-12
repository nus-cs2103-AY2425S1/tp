package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class AddressBookTest {

    private final AddressBook ADDRESS_BOOK = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ADDRESS_BOOK.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ADDRESS_BOOK.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        ADDRESS_BOOK.resetData(newData);

        assertEquals(newData, ADDRESS_BOOK);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).buildBuyer();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> ADDRESS_BOOK.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ADDRESS_BOOK.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(ADDRESS_BOOK.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        ADDRESS_BOOK.addPerson(ALICE);

        assertTrue(ADDRESS_BOOK.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        ADDRESS_BOOK.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).buildBuyer();

        assertTrue(ADDRESS_BOOK.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> ADDRESS_BOOK.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + ADDRESS_BOOK.getPersonList() + "}";

        assertEquals(expected, ADDRESS_BOOK.toString());
    }
    @Test
    public void equals() {
        assertTrue(ADDRESS_BOOK.equals(ADDRESS_BOOK));

        assertFalse(ADDRESS_BOOK.equals(null));

        assertFalse(ADDRESS_BOOK.equals(5));

        AddressBook addressBookCopy = new AddressBook();
        assertTrue(ADDRESS_BOOK.equals(addressBookCopy));

        ADDRESS_BOOK.addPerson(ALICE);
        addressBookCopy.addPerson(new PersonBuilder(ALICE).buildBuyer());
        assertTrue(ADDRESS_BOOK.equals(addressBookCopy));

        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addPerson(new PersonBuilder().withName("Bob").buildBuyer());
        assertFalse(ADDRESS_BOOK.equals(differentAddressBook));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(ADDRESS_BOOK.hashCode(), ADDRESS_BOOK.hashCode());

        AddressBook addressBookCopy = new AddressBook();
        assertEquals(ADDRESS_BOOK.hashCode(), addressBookCopy.hashCode());

        ADDRESS_BOOK.addPerson(ALICE);
        addressBookCopy.addPerson(new PersonBuilder(ALICE).buildBuyer());
        assertEquals(ADDRESS_BOOK.hashCode(), addressBookCopy.hashCode());

        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addPerson(new PersonBuilder().withName("Bob").buildBuyer());
        assertFalse(ADDRESS_BOOK.hashCode() == differentAddressBook.hashCode());
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
