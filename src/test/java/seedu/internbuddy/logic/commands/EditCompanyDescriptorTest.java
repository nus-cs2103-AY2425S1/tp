package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtil.DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_SOFTWARE;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.internbuddy.testutil.EditCompanyDescriptorBuilder;

public class EditCompanyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCompanyDescriptor descriptorWithSameValues = new EditCompanyDescriptor(DESC_GOOGLE);
        assertTrue(DESC_GOOGLE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GOOGLE.equals(DESC_GOOGLE));

        // null -> returns false
        assertFalse(DESC_GOOGLE.equals(null));

        // different types -> returns false
        assertFalse(DESC_GOOGLE.equals(5));

        // different values -> returns false
        assertFalse(DESC_GOOGLE.equals(DESC_MICROSOFT));

        // different name -> returns false
        EditCompanyDescriptor editedAmy = new EditCompanyDescriptorBuilder(DESC_GOOGLE).withName(VALID_NAME_MICROSOFT)
                .build();
        assertFalse(DESC_GOOGLE.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_GOOGLE).withPhone(VALID_PHONE_MICROSOFT).build();
        assertFalse(DESC_GOOGLE.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_GOOGLE).withEmail(VALID_EMAIL_MICROSOFT).build();
        assertFalse(DESC_GOOGLE.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_GOOGLE).withAddress(VALID_ADDRESS_MICROSOFT).build();
        assertFalse(DESC_GOOGLE.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_GOOGLE).withTags(VALID_TAG_SOFTWARE).build();
        assertFalse(DESC_GOOGLE.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCompanyDescriptor editCompanyDescriptor = new EditCompanyDescriptor();
        String expected = EditCompanyDescriptor.class.getCanonicalName() + "{name="
                + editCompanyDescriptor.getName().orElse(null) + ", phone="
                + editCompanyDescriptor.getPhone().orElse(null) + ", email="
                + editCompanyDescriptor.getEmail().orElse(null) + ", address="
                + editCompanyDescriptor.getAddress().orElse(null) + ", tags="
                + editCompanyDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editCompanyDescriptor.toString());
    }
}
