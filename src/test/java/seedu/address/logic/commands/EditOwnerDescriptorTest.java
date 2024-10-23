package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_OWNER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_OWNER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditOwnerCommand.EditOwnerDescriptor;
import seedu.address.testutil.EditOwnerDescriptorBuilder;

public class EditOwnerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditOwnerDescriptor descriptorWithSameValues = new EditOwnerDescriptor(DESC_OWNER_AMY);
        assertTrue(DESC_OWNER_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_OWNER_AMY.equals(DESC_OWNER_AMY));

        // null -> returns false
        assertFalse(DESC_OWNER_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_OWNER_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_OWNER_AMY.equals(DESC_OWNER_BOB));

        // different name -> returns false
        EditOwnerDescriptor editedAmy = new EditOwnerDescriptorBuilder(DESC_OWNER_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_OWNER_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditOwnerDescriptorBuilder(DESC_OWNER_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_OWNER_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditOwnerDescriptorBuilder(DESC_OWNER_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_OWNER_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditOwnerDescriptorBuilder(DESC_OWNER_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_OWNER_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditOwnerDescriptor editOwnerDescriptor = new EditOwnerDescriptor();
        String expected = EditOwnerDescriptor.class.getCanonicalName() + "{name="
                + editOwnerDescriptor.getName().orElse(null) + ", phone="
                + editOwnerDescriptor.getPhone().orElse(null) + ", email="
                + editOwnerDescriptor.getEmail().orElse(null) + ", address="
                + editOwnerDescriptor.getAddress().orElse(null) + "}";
        assertEquals(expected, editOwnerDescriptor.toString());
    }
}
