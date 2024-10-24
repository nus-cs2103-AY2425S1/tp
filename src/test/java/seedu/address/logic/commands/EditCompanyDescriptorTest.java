package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIGTECH;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.address.testutil.EditCompanyDescriptorBuilder;

public class EditCompanyDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditCompanyDescriptor descriptorWithSameValues = new EditCompanyDescriptor(DESC_TESLA);
        assertTrue(DESC_TESLA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TESLA.equals(DESC_TESLA));

        // null -> returns false
        assertFalse(DESC_TESLA.equals(null));

        // different types -> returns false
        assertFalse(DESC_TESLA.equals(5));

        // different values -> returns false
        assertFalse(DESC_TESLA.equals(DESC_MICROSOFT));

        // different name -> returns false
        EditCompanyDescriptor editedAmy = new EditCompanyDescriptorBuilder(DESC_TESLA)
                .withName(VALID_NAME_MICROSOFT).build();
        assertFalse(DESC_TESLA.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_TESLA).withPhone(VALID_PHONE_MICROSOFT).build();
        assertFalse(DESC_TESLA.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_TESLA).withEmail(VALID_EMAIL_MICROSOFT).build();
        assertFalse(DESC_TESLA.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_TESLA).withAddress(VALID_ADDRESS_MICROSOFT).build();
        assertFalse(DESC_TESLA.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditCompanyDescriptorBuilder(DESC_TESLA).withTags(VALID_TAG_BIGTECH).build();
        assertFalse(DESC_TESLA.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCompanyDescriptor editCompanyDescriptor = new EditCompanyDescriptor();
        String expected = EditCompanyDescriptor.class.getSimpleName() + "{name="
                + editCompanyDescriptor.getName().orElse(null) + ", phone="
                + editCompanyDescriptor.getPhone().orElse(null) + ", email="
                + editCompanyDescriptor.getEmail().orElse(null) + ", address="
                + editCompanyDescriptor.getAddress().orElse(null) + ", career page url="
                + editCompanyDescriptor.getCareerPageUrl().orElse(null) + ", tags="
                + editCompanyDescriptor.getTags().orElse(null) + ", remark="
                + editCompanyDescriptor.getRemark().orElse(null) + "}";
        assertEquals(expected, editCompanyDescriptor.toString());
    }
}
