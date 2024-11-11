package seedu.ddd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.CARL;
import static seedu.ddd.testutil.contact.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.exceptions.DuplicateContactException;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.testutil.contact.ClientBuilder;

public class AddressBookTest {

    private AddressBook addressBook;

    @BeforeEach
    public void setUp() {
        addressBook = new AddressBook();
    }

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
        // editedAlice has the same name and ID
        Contact editedAlice = new ClientBuilder(ALICE)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
        List<Contact> newContacts = List.of(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> addressBook.resetData(newData));
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
        Contact editedAlice = new ClientBuilder(ALICE)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        assertTrue(addressBook.hasContact(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getContactList().remove(0));
    }

    @Test
    public void hasClientId_validClientId_returnsTrue() {
        addressBook.addContact(ALICE);
        assertTrue(addressBook.hasClientId(ALICE.getId()));
    }

    @Test
    public void hasClientId_invalidClientId_returnsFalse() {
        // empty addressbook
        assertFalse(addressBook.hasClientId(ALICE.getId()));

        // wrong client
        addressBook.addContact(CARL);
        assertFalse(addressBook.hasClientId(ALICE.getId()));

        // wrong contact type
        addressBook.addContact(BENSON);
        assertFalse(addressBook.hasClientId(BENSON.getId()));
    }

    @Test
    public void hasVendorId_validVendorId_returnsTrue() {
        addressBook.addContact(BENSON);
        assertTrue(addressBook.hasVendorId(BENSON.getId()));
    }

    @Test
    public void hasVendorId_invalidVendorId_returnsFalse() {
        // empty addressbook
        assertFalse(addressBook.hasVendorId(BENSON.getId()));

        // wrong vendor
        addressBook.addContact(DANIEL);
        assertFalse(addressBook.hasVendorId(BENSON.getId()));

        // wrong contact type
        addressBook.addContact(ALICE);
        assertFalse(addressBook.hasVendorId(ALICE.getId()));
    }

    @Test
    public void hasEvent_validEvent_returnsTrue() {
        addressBook = getTypicalAddressBook();
        assertTrue(addressBook.hasEvent(WEDDING_A));
    }

    @Test
    public void hasEvent_invalidEvent_returnsFalse() {
        // empty addressbook
        assertFalse(addressBook.hasEvent(WEDDING_A));

        addressBook = getTypicalAddressBook();
        assertTrue(addressBook.hasEvent(WEDDING_A));
        assertFalse(addressBook.hasEvent(VALID_EVENT));
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
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Contact> contacts) {
            this.contacts.setAll(contacts);
        }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }

}
