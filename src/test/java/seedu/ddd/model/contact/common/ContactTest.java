package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_PHONE;
import static seedu.ddd.testutil.contact.TypicalContacts.ELLE;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.event.exceptions.EventNotFoundException;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.VendorBuilder;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact person = new ClientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void removeEvent_missingEvent_throwsEventNotFoundException() {
        Contact contact = new ClientBuilder(ELLE).build();
        assert !contact.getEvents().contains(WEDDING_A);

        assertThrows(EventNotFoundException.class, () -> contact.removeEvent(WEDDING_A));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(VALID_CLIENT.isSameContact(VALID_CLIENT));

        // null -> returns false
        assertFalse(VALID_CLIENT.isSameContact(null));

        // same name, all other attributes different -> returns true
        Contact otherClient = new ClientBuilder(VALID_CLIENT)
                .withPhone(VALID_VENDOR_PHONE)
                .withEmail(VALID_VENDOR_EMAIL)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        assertTrue(VALID_CLIENT.isSameContact(otherClient));

        // same phone, all other attributes different -> returns true
        otherClient = new ClientBuilder(VALID_CLIENT)
                .withName(VALID_VENDOR_NAME)
                .withEmail(VALID_VENDOR_EMAIL)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withTags(VALID_TAG_1)
                .build();
        assertTrue(VALID_CLIENT.isSameContact(otherClient));

        // different name and phone, all other attributes same -> returns false
        otherClient = new ClientBuilder(VALID_CLIENT)
                .withName(VALID_VENDOR_NAME)
                .withPhone(VALID_VENDOR_PHONE)
                .build();
        assertFalse(VALID_CLIENT.isSameContact(otherClient));

        // name differs in case and different phone, all other attributes same -> returns true
        Contact otherVendor = new VendorBuilder(VALID_VENDOR)
                .withName(VALID_VENDOR_NAME.toLowerCase())
                .withPhone(VALID_CLIENT_PHONE)
                .build();
        assertTrue(VALID_VENDOR.isSameContact(otherVendor));

        // name has trailing spaces and different phone, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_VENDOR_NAME + " ";
        otherVendor = new VendorBuilder(VALID_VENDOR)
                .withName(nameWithTrailingSpaces)
                .withPhone(VALID_CLIENT_PHONE)
                .build();
        assertTrue(VALID_VENDOR.isSameContact(otherVendor));

        // should equal self
        assertTrue(VALID_CLIENT.isSameContact(VALID_CLIENT));
        assertTrue(VALID_VENDOR.isSameContact(VALID_VENDOR));
        assertFalse(VALID_CLIENT.isSameContact(VALID_VENDOR));
        assertFalse(VALID_VENDOR.isSameContact(VALID_CLIENT));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ClientBuilder(VALID_CLIENT).build();
        assertTrue(VALID_CLIENT.equals(aliceCopy));

        // same object -> returns true
        assertTrue(VALID_CLIENT.equals(VALID_CLIENT));

        // null -> returns false
        assertFalse(VALID_CLIENT.equals(null));

        // different type -> returns false
        assertFalse(VALID_CLIENT.equals(5));

        // different person -> returns false
        assertFalse(VALID_CLIENT.equals(VALID_VENDOR));

        // different name -> returns false
        Contact editedAlice = new ClientBuilder(VALID_CLIENT).withName(VALID_VENDOR_NAME).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withPhone(VALID_VENDOR_PHONE).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withEmail(VALID_VENDOR_EMAIL).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withAddress(VALID_VENDOR_ADDRESS).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withTags(VALID_TAG_2).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));
    }

}
