package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_CONTACT_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.EditCommand.EditContactDescriptor;
import seedu.ddd.testutil.EditContactDescriptorBuilder;

public class EditContactDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        EditContactDescriptor editedDescriptor = new EditContactDescriptor(DESC_CONTACT_AMY);
        assertTrue(editedDescriptor.isAnyFieldEdited());

        EditContactDescriptor uneditedDescriptor = new EditContactDescriptor();
        assertFalse(uneditedDescriptor.isAnyFieldEdited());

        EditContactDescriptor onlyIdSpecifiedDescriptor = new EditContactDescriptorBuilder()
                .withId(VALID_ID_AMY).build();
        assertFalse(onlyIdSpecifiedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditContactDescriptor descriptorWithSameValues = new EditContactDescriptor(DESC_CONTACT_AMY);
        assertTrue(DESC_CONTACT_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CONTACT_AMY.equals(DESC_CONTACT_AMY));

        // null -> returns false
        assertFalse(DESC_CONTACT_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_CONTACT_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_CONTACT_AMY.equals(DESC_VENDOR_BOB));

        // different name -> returns false
        EditContactDescriptor editedAmy = new EditContactDescriptorBuilder(DESC_CONTACT_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_CONTACT_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_CONTACT_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_CONTACT_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_CONTACT_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_CONTACT_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_CONTACT_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_CONTACT_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_CONTACT_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_CONTACT_AMY.equals(editedAmy));
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
