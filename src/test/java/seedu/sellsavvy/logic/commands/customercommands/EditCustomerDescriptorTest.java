package seedu.sellsavvy.logic.commands.customercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_PHONE_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.customercommands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.sellsavvy.testutil.EditCustomerDescriptorBuilder;

public class EditCustomerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCustomerDescriptor descriptorWithSameValues = new EditCustomerDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCustomerDescriptor editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCustomerDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCustomerDescriptor editCustomerDescriptor = new EditCustomerDescriptor();
        String expected = EditCustomerDescriptor.class.getCanonicalName() + "{name="
                + editCustomerDescriptor.getName().orElse(null) + ", phone="
                + editCustomerDescriptor.getPhone().orElse(null) + ", email="
                + editCustomerDescriptor.getEmail().orElse(null) + ", address="
                + editCustomerDescriptor.getAddress().orElse(null) + ", tags="
                + editCustomerDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editCustomerDescriptor.toString());
    }
}
