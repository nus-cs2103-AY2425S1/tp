package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.*;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.VendorBuilder;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact person = new ClientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
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

        // different name, all other attributes same -> returns false
        otherClient = new ClientBuilder(VALID_CLIENT).withName(VALID_VENDOR_NAME).build();
        assertFalse(VALID_CLIENT.isSameContact(otherClient));

        // name differs in case, all other attributes same -> returns false
        Contact otherVendor = new VendorBuilder(VALID_VENDOR)
                .withName(VALID_VENDOR_NAME.toLowerCase())
                .build();
        assertFalse(VALID_VENDOR.isSameContact(otherVendor));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_VENDOR_NAME + " ";
        otherVendor = new VendorBuilder(VALID_VENDOR).withName(nameWithTrailingSpaces).build();
        assertFalse(VALID_VENDOR.isSameContact(otherVendor));

        // should equal self
        assertTrue(VALID_CLIENT.isSameContact(VALID_CLIENT));
        assertTrue(VALID_VENDOR.isSameContact(VALID_VENDOR));
        assertFalse(VALID_CLIENT.isSameContact(VALID_VENDOR));
        assertFalse(VALID_VENDOR.isSameContact(VALID_CLIENT));
    }

    /*
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
        assertFalse(VALID_CLIENT.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ClientBuilder(VALID_CLIENT).withName(VALID_NAME_BOB).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withPhone(VALID_PHONE_BOB).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(VALID_CLIENT).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(VALID_CLIENT.equals(editedAlice));
    }
    */

    @Test
    public void toStringMethod() {
        String expectedClientString = Client.class.getCanonicalName() + "{name=" + VALID_CLIENT.getName()
                + ", phone=" + VALID_CLIENT.getPhone() + ", email=" + VALID_CLIENT.getEmail()
                + ", address=" + VALID_CLIENT.getAddress() + ", tags=" + VALID_CLIENT.getTags()
                + ", id=" + VALID_CLIENT.getId() + "}";
        assertEquals(expectedClientString, VALID_CLIENT.toString());

        String expectedVendorString = Vendor.class.getCanonicalName() + "{name=" + VALID_VENDOR.getName()
                + ", phone=" + VALID_VENDOR.getPhone() + ", email=" + VALID_VENDOR.getEmail()
                + ", address=" + VALID_VENDOR.getAddress() + ", service=" + VALID_VENDOR.getService()
                + ", tags=" + VALID_VENDOR.getTags() + ", id=" + VALID_VENDOR.getId() + "}";
        assertEquals(expectedVendorString, VALID_VENDOR.toString());
    }
}
