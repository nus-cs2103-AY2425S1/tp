package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WEDDING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_BIRTHDAY);
        assertTrue(DESC_BIRTHDAY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BIRTHDAY.equals(DESC_BIRTHDAY));

        // null -> returns false
        assertFalse(DESC_BIRTHDAY.equals(null));

        // different types -> returns false
        assertFalse(DESC_BIRTHDAY.equals(5));

        // different values -> returns false
        assertFalse(DESC_BIRTHDAY.equals(DESC_WEDDING));

        // different name -> returns false
        EditEventDescriptor editedBirthday = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withName(VALID_NAME_WEDDING).build();
        assertFalse(DESC_BIRTHDAY.equals(editedBirthday));

        // different date -> returns false
        editedBirthday = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withDate(VALID_DATE_WEDDING).build();
        assertFalse(DESC_BIRTHDAY.equals(editedBirthday));
    }

    @Test
    public void toStringMethod() {
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        String expected = EditEventDescriptor.class.getCanonicalName() + "{name="
                + editEventDescriptor.getName().orElse(null) + ", date="
                + editEventDescriptor.getDate().orElse(null) + "}";
        assertEquals(expected, editEventDescriptor.toString());
    }
}
