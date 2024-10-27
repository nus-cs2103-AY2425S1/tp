package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_CONTACT_DESCRIPTOR;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_VENDOR_DESCRIPTOR;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_3;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_SERVICE_2;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;

public class EditVendorDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        EditVendorDescriptor editedDescriptor = new EditVendorDescriptor(VALID_EDIT_VENDOR_DESCRIPTOR);
        assertTrue(editedDescriptor.isAnyFieldEdited());

        EditVendorDescriptor uneditedDescriptor = new EditVendorDescriptor();
        assertFalse(uneditedDescriptor.isAnyFieldEdited());

        EditVendorDescriptor onlyIdSpecifiedDescriptor = new EditVendorDescriptorBuilder()
                .withId(VALID_VENDOR_ID)
                .build();
        assertFalse(onlyIdSpecifiedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditVendorDescriptor descriptorWithSameValues = VALID_EDIT_VENDOR_DESCRIPTOR.copy();
        assertTrue(VALID_EDIT_VENDOR_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VALID_EDIT_VENDOR_DESCRIPTOR.equals(VALID_EDIT_VENDOR_DESCRIPTOR));

        // null -> returns false
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(5));

        // different values -> returns false
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(VALID_EDIT_CONTACT_DESCRIPTOR));

        // different name -> returns false
        EditVendorDescriptor editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withName(VALID_CLIENT_NAME)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));

        // different phone -> returns false
        editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withPhone(VALID_CLIENT_PHONE)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));

        // different email -> returns false
        editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withEmail(VALID_CLIENT_EMAIL)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));

        // different address -> returns false
        editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withAddress(VALID_CLIENT_ADDRESS)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));

        // different service -> returns false
        editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withService(VALID_VENDOR_SERVICE_2)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));

        // different tags -> returns false
        editedBob = new EditVendorDescriptorBuilder(VALID_EDIT_VENDOR_DESCRIPTOR)
                .withTags(VALID_TAG_3)
                .build();
        assertFalse(VALID_EDIT_VENDOR_DESCRIPTOR.equals(editedBob));
    }

    @Test
    public void toStringMethod() {
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        String expected = EditVendorDescriptor.class.getCanonicalName() + "{name="
                + editVendorDescriptor.getName().orElse(null) + ", phone="
                + editVendorDescriptor.getPhone().orElse(null) + ", email="
                + editVendorDescriptor.getEmail().orElse(null) + ", address="
                + editVendorDescriptor.getAddress().orElse(null) + ", service="
                + editVendorDescriptor.getService().orElse(null) + ", tags="
                + editVendorDescriptor.getTags().orElse(null) + ", id="
                + editVendorDescriptor.getId().orElse(null) + "}";
        assertEquals(expected, editVendorDescriptor.toString());
    }
}
