package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_CLIENT_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_DATE_OTHER;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.EditCommand.EditClientDescriptor;
import seedu.ddd.testutil.EditClientDescriptorBuilder;

public class EditClientDescriptorTest {

    @Test
    public void isAnyFieldEdited() {
        EditClientDescriptor editedDescriptor = new EditClientDescriptor(DESC_CLIENT_AMY);
        assertTrue(editedDescriptor.isAnyFieldEdited());

        EditClientDescriptor uneditedDescriptor = new EditClientDescriptor();
        assertFalse(uneditedDescriptor.isAnyFieldEdited());

        EditClientDescriptor onlyIdSpecifiedDescriptor = new EditClientDescriptorBuilder()
                .withId(VALID_ID_AMY).build();
        assertFalse(onlyIdSpecifiedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditClientDescriptor descriptorWithSameValues = DESC_CLIENT_AMY.copy();
        assertTrue(DESC_CLIENT_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CLIENT_AMY.equals(DESC_CLIENT_AMY));

        // null -> returns false
        assertFalse(DESC_CLIENT_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_CLIENT_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_CLIENT_AMY.equals(DESC_VENDOR_BOB));

        // different name -> returns false
        EditClientDescriptor editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY).withDate(VALID_DATE_OTHER).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditClientDescriptorBuilder(DESC_CLIENT_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_CLIENT_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        String expected = EditClientDescriptor.class.getCanonicalName() + "{name="
                + editClientDescriptor.getName().orElse(null) + ", phone="
                + editClientDescriptor.getPhone().orElse(null) + ", email="
                + editClientDescriptor.getEmail().orElse(null) + ", address="
                + editClientDescriptor.getAddress().orElse(null) + ", date="
                + editClientDescriptor.getDate().orElse(null) + ", tags="
                + editClientDescriptor.getTags().orElse(null) + ", id="
                + editClientDescriptor.getId().orElse(null) + "}";
        assertEquals(expected, editClientDescriptor.toString());
    }
}
