package seedu.ddd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalContacts.ALICE;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.ClientBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getContactList());
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
    public void resetData_withDuplicateContacts_throwsDuplicateContactException() {
        // TODO: fix this
        // // Two persons with the same identity fields
        // Contact editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
        //         .build();
        // List<Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        // AddressBookStub newData = new AddressBookStub(newContacts);

        // assertThrows(DuplicateContactException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        addressBook.addContact(ALICE);
        assertTrue(addressBook.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addContact(ALICE);
        Contact editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasContact(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getContactList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{contacts=" + addressBook.getContactList() + ","
                + " events=" + addressBook.getEventList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Contact> persons = FXCollections.observableArrayList();
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

        AddressBookStub(Collection<Contact> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Event> getEventList() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
