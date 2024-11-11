package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_CONTACT_DESCRIPTOR;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_VENDOR_DESCRIPTOR;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_PHONE;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.ddd.testutil.EditContactDescriptorBuilder;

public class EditContactDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        EditContactDescriptor editedDescriptor = new EditContactDescriptor(VALID_EDIT_CONTACT_DESCRIPTOR);
        assertTrue(editedDescriptor.isAnyFieldEdited());

        EditContactDescriptor uneditedDescriptor = new EditContactDescriptor();
        assertFalse(uneditedDescriptor.isAnyFieldEdited());

        EditContactDescriptor onlyIdSpecifiedDescriptor = new EditContactDescriptorBuilder()
                .withId(VALID_CLIENT_ID)
                .build();
        assertFalse(onlyIdSpecifiedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditContactDescriptor descriptorWithSameValues = new EditContactDescriptor(VALID_EDIT_CONTACT_DESCRIPTOR);
        assertTrue(VALID_EDIT_CONTACT_DESCRIPTOR.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(VALID_EDIT_CONTACT_DESCRIPTOR.equals(VALID_EDIT_CONTACT_DESCRIPTOR));

        // null -> returns false
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(null));

        // different types -> returns false
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(5));

        // different values -> returns false
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(VALID_EDIT_VENDOR_DESCRIPTOR));

        // different name -> returns false
        EditContactDescriptor editedAmy = new EditContactDescriptorBuilder(VALID_EDIT_CONTACT_DESCRIPTOR)
                .withName(VALID_VENDOR_NAME)
                .build();
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditContactDescriptorBuilder(VALID_EDIT_CONTACT_DESCRIPTOR)
                .withPhone(VALID_VENDOR_PHONE)
                .build();
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditContactDescriptorBuilder(VALID_EDIT_CONTACT_DESCRIPTOR)
                .withEmail(VALID_VENDOR_EMAIL)
                .build();
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditContactDescriptorBuilder(VALID_EDIT_CONTACT_DESCRIPTOR)
                .withAddress(VALID_VENDOR_ADDRESS)
                .build();
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditContactDescriptorBuilder(VALID_EDIT_CONTACT_DESCRIPTOR)
                .withTags(VALID_TAG_2)
                .build();
        assertFalse(VALID_EDIT_CONTACT_DESCRIPTOR.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        String expected = EditContactDescriptor.class.getCanonicalName() + "{name="
                + editContactDescriptor.getName().orElse(null) + ", phone="
                + editContactDescriptor.getPhone().orElse(null) + ", email="
                + editContactDescriptor.getEmail().orElse(null) + ", address="
                + editContactDescriptor.getAddress().orElse(null) + ", tags="
                + editContactDescriptor.getTags().orElse(null) + ", id="
                + editContactDescriptor.getId().orElse(null) + "}";
        assertEquals(expected, editContactDescriptor.toString());
    }
}
