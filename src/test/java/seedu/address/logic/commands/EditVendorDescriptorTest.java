package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VENDOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.util.EditVendorDescriptor;
import seedu.address.testutil.EditVendorDescriptorBuilder;

public class EditVendorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditVendorDescriptor descriptorWithSameValues = new EditVendorDescriptor(DESC_VENDOR_AMY);
        assertTrue(DESC_VENDOR_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_VENDOR_AMY.equals(DESC_VENDOR_AMY));

        // null -> returns false
        assertFalse(DESC_VENDOR_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_VENDOR_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_VENDOR_AMY.equals(DESC_VENDOR_BOB));

        // different name -> returns false
        EditVendorDescriptor editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));

        // different company -> returns false
        editedAmy = new EditVendorDescriptorBuilder(DESC_VENDOR_AMY).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(DESC_VENDOR_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        String expected = EditVendorDescriptor.class.getCanonicalName() + "{name="
                + editVendorDescriptor.getName().orElse(null) + ", phone="
                + editVendorDescriptor.getPhone().orElse(null) + ", email="
                + editVendorDescriptor.getEmail().orElse(null) + ", address="
                + editVendorDescriptor.getAddress().orElse(null) + ", tags="
                + editVendorDescriptor.getTags().orElse(null) + ", company="
                + editVendorDescriptor.getCompany().orElse(null) + ", budget="
                + editVendorDescriptor.getBudget().orElse(null) + "}";
        assertEquals(expected, editVendorDescriptor.toString());
    }
}
