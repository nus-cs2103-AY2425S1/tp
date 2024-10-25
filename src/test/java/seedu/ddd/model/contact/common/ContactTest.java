package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.ClientBuilder;
import seedu.ddd.testutil.VendorBuilder;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact person = new ClientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE.isSameContact(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameContact(null));

        // same name, all other attributes different -> returns true
        Contact editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Contact editedBob = new VendorBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameContact(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new VendorBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameContact(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        Client clientBob = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        Vendor vendorBob = new VendorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(clientBob.isSameContact(clientBob));
        assertTrue(vendorBob.isSameContact(vendorBob));
        assertFalse(clientBob.isSameContact(vendorBob));
        assertFalse(vendorBob.isSameContact(clientBob));
    }

    /*
    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
    */

    @Test
    public void toStringMethod() {
        String expectedClientString = Client.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", id=" + ALICE.getId() + "}";
        assertEquals(expectedClientString, ALICE.toString());

        String expectedVendorString = Vendor.class.getCanonicalName() + "{name=" + BOB.getName()
                + ", phone=" + BOB.getPhone() + ", email=" + BOB.getEmail()
                + ", address=" + BOB.getAddress() + ", service=" + BOB.getService()
                + ", tags=" + BOB.getTags() + ", id=" + BOB.getId() + "}";
        assertEquals(expectedVendorString, BOB.toString());
    }
}
