package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DEADLINE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;

public class UpdateTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateTaskDescriptor descriptorWithSameValues = new UpdateTaskDescriptor(DESC_TASK_AMY);
        assertTrue(DESC_TASK_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TASK_AMY.equals(DESC_TASK_AMY));

        // null -> returns false
        assertFalse(DESC_TASK_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_TASK_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_TASK_AMY.equals(DESC_TASK_BOB));

        // different task description -> returns false
        UpdateTaskDescriptor updatedAmy = new UpdateTaskDescriptorBuilder(DESC_TASK_AMY)
                .withTaskDescription(VALID_TASK_DESCRIPTION_PROJECT).build();
        assertFalse(DESC_TASK_AMY.equals(updatedAmy));

        // different task deadline -> returns false
        updatedAmy = new UpdateTaskDescriptorBuilder(DESC_TASK_AMY).withTaskDeadline(VALID_TASK_DEADLINE).build();
        assertFalse(DESC_TASK_AMY.equals(updatedAmy));
    }

    @Test
    public void toStringMethod() {
        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
        String expected = UpdateTaskDescriptor.class.getCanonicalName() + "{task description="
                + updateTaskDescriptor.getTaskDescription().orElse(null) + ", task deadline="
                + updateTaskDescriptor.getTaskDeadline().orElse(null) + "}";
        assertEquals(expected, updateTaskDescriptor.toString());
    }
}
