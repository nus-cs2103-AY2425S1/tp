package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_CONTACT_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_SERVICE_OTHER;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;

public class EditVendorDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        EditVendorDescriptor editedDescriptor = new EditVendorDescriptor(DESC_VENDOR_BOB);
        assertTrue(editedDescriptor.isAnyFieldEdited());

        EditVendorDescriptor uneditedDescriptor = new EditVendorDescriptor();
        assertFalse(uneditedDescriptor.isAnyFieldEdited());

        EditVendorDescriptor onlyIdSpecifiedDescriptor = new EditVendorDescriptorBuilder()
                .withId(VALID_ID_BOB).build();
        assertFalse(onlyIdSpecifiedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditVendorDescriptor descriptorWithSameValues = DESC_VENDOR_BOB.copy();
        assertTrue(DESC_VENDOR_BOB.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_VENDOR_BOB.equals(DESC_VENDOR_BOB));

        // null -> returns false
        assertFalse(DESC_VENDOR_BOB.equals(null));

        // different types -> returns false
        assertFalse(DESC_VENDOR_BOB.equals(5));

        // different values -> returns false
        assertFalse(DESC_VENDOR_BOB.equals(DESC_CONTACT_AMY));

        // different name -> returns false
        EditVendorDescriptor editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB)
                .withName(VALID_NAME_AMY).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));

        // different phone -> returns false
        editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB).withPhone(VALID_PHONE_AMY).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));

        // different email -> returns false
        editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB).withEmail(VALID_EMAIL_AMY).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));

        // different address -> returns false
        editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB).withAddress(VALID_ADDRESS_AMY).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));

        // different service -> returns false
        editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB).withService(VALID_SERVICE_OTHER).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));

        // different tags -> returns false
        editedBob = new EditVendorDescriptorBuilder(DESC_VENDOR_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_VENDOR_BOB.equals(editedBob));
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
