package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.DeleteCommandDescriptor;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

public class DeleteCommandDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        DeleteCommandDescriptor deleteCommandDescriptor = new DeleteCommandDescriptor();
        deleteCommandDescriptor.setEmergencyContactIndex(INDEX_FIRST_PERSON);

        DeleteCommandDescriptor differentDeleteCommandDesciptor = new DeleteCommandDescriptor();
        differentDeleteCommandDesciptor.setEmergencyContactIndex(INDEX_THIRD_PERSON);

        DeleteCommandDescriptor descriptorCopy = new DeleteCommandDescriptor(deleteCommandDescriptor);
        assertTrue(descriptorCopy.equals(deleteCommandDescriptor));

        // same object -> returns true
        assertTrue(deleteCommandDescriptor.equals(deleteCommandDescriptor));

        // null -> returns false
        assertFalse(deleteCommandDescriptor.equals(null));

        // different types -> returns false
        assertFalse(deleteCommandDescriptor.equals(5));

        // different values -> returns false
        assertFalse(deleteCommandDescriptor.equals(differentDeleteCommandDesciptor));

    }

    @Test
    public void toStringMethod() {
        DeleteCommandDescriptor deleteCommandDescriptor = new DeleteCommandDescriptor();
        String expected = DeleteCommandDescriptor.class.getCanonicalName() + "{emergency contact index="
                + deleteCommandDescriptor.getEmergencyContactIndex().orElse(null) + "}";
        assertEquals(expected, deleteCommandDescriptor.toString());
    }
}
