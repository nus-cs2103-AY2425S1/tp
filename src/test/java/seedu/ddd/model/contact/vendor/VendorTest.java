package seedu.ddd.model.contact.vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.ddd.testutil.contact.VendorBuilder;

public class VendorTest {

    @Test
    public void isSameContact() {
        Vendor copied = new VendorBuilder(BENSON).build();
        assertTrue(BENSON.isSameContact(copied));

        assertFalse(DANIEL.isSameContact(copied));
        copied = new VendorBuilder(copied)
            .withName(DANIEL.getName().fullName)
            .build();
        assertTrue(DANIEL.isSameContact(copied));
    }

    @Test
    public void equals() {
        // different vendor
        assertNotEquals(BENSON, DANIEL);

        // different types
        assertNotEquals(BENSON, 1);
        assertNotEquals(BENSON, ALICE);

        // copied should be same
        Vendor copied = new VendorBuilder(BENSON).build();
        assertEquals(BENSON, copied);

        // different name
        copied = new VendorBuilder(BENSON)
            .withName(DANIEL.getName().fullName)
            .build();
        assertNotEquals(BENSON, copied);

        // different phone
        copied = new VendorBuilder(BENSON)
            .withPhone(DANIEL.getPhone().value)
            .build();
        assertNotEquals(BENSON, copied);

        // different email
        copied = new VendorBuilder(BENSON)
            .withEmail(DANIEL.getEmail().value)
            .build();
        assertNotEquals(BENSON, copied);

        // different address
        copied = new VendorBuilder(BENSON)
            .withAddress(DANIEL.getAddress().value)
            .build();
        assertNotEquals(BENSON, copied);

        // different service
        copied = new VendorBuilder(BENSON)
            .withService(DANIEL.getService().value)
            .build();
        assertNotEquals(BENSON, copied);

        // different service
        copied = new VendorBuilder(BENSON)
            .withTags(VALID_TAG_1, VALID_TAG_2)
            .build();
        assertNotEquals(BENSON, copied);
    }

    @Test
    public void toStringMethod() {
        String expectedVendorString = Vendor.class.getCanonicalName() + "{name=" + VALID_VENDOR.getName()
                + ", phone=" + VALID_VENDOR.getPhone() + ", email=" + VALID_VENDOR.getEmail()
                + ", address=" + VALID_VENDOR.getAddress() + ", service=" + VALID_VENDOR.getService()
                + ", tags=" + VALID_VENDOR.getTags() + ", id=" + VALID_VENDOR.getId() + "}";
        assertEquals(expectedVendorString, VALID_VENDOR.toString());
    }
}
