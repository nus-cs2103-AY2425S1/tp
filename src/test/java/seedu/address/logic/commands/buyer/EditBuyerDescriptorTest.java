package seedu.address.logic.commands.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;

public class EditBuyerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditBuyerDescriptor descriptorWithSameValues = new EditBuyerDescriptor(DESC_AMY);
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
        EditBuyerDescriptor editedAmy = new EditBuyerDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditBuyerDescriptor editBuyerDescriptor = new EditBuyerDescriptor();
        String expected = EditBuyerDescriptor.class.getCanonicalName() + "{name="
                + editBuyerDescriptor.getName().orElse(null) + ", phone="
                + editBuyerDescriptor.getPhone().orElse(null) + ", email="
                + editBuyerDescriptor.getEmail().orElse(null) + ", address="
                + editBuyerDescriptor.getAddress().orElse(null) + ", type="
                + editBuyerDescriptor.getBuyerType().orElse(null) + ", tags="
                + editBuyerDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editBuyerDescriptor.toString());
    }
}
