package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientDescriptor descriptorWithSameValues = new EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different nric -> returns false
        EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withNric(VALID_NRIC_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));


    }

    @Test
    public void toStringMethod() {
        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();
        String expected = EditPatientDescriptor.class.getCanonicalName() + "{name="
                + editPatientDescriptor.getName().orElse(null) + ", nric="
                + editPatientDescriptor.getNric().orElse(null) + ", sex="
                + editPatientDescriptor.getSex().orElse(null) + ", birthdate="
                + editPatientDescriptor.getBirthDate().orElse(null) + "}";
        assertEquals(expected, editPatientDescriptor.toString());
    }
}
