package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RSVP_DECLINED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.util.EditGuestDescriptor;
import seedu.address.testutil.EditGuestDescriptorBuilder;

public class EditGuestDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditGuestDescriptor descriptorWithSameValues = new EditGuestDescriptor(DESC_AMY);
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
        EditGuestDescriptor editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different rsvp -> returns false
        editedAmy = new EditGuestDescriptorBuilder(DESC_AMY).withRsvp(VALID_RSVP_DECLINED).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditGuestDescriptor editGuestDescriptor = new EditGuestDescriptor();
        String expected = EditGuestDescriptor.class.getCanonicalName() + "{name="
                + editGuestDescriptor.getName().orElse(null) + ", phone="
                + editGuestDescriptor.getPhone().orElse(null) + ", email="
                + editGuestDescriptor.getEmail().orElse(null) + ", address="
                + editGuestDescriptor.getAddress().orElse(null) + ", tags="
                + editGuestDescriptor.getTags().orElse(null) + ", RSVP="
                + editGuestDescriptor.getRsvp().orElse(null) + ", Relation="
                + editGuestDescriptor.getRelation().orElse(null) + "}";
        assertEquals(expected, editGuestDescriptor.toString());
    }
}
