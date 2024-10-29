package seedu.address.model.student.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskDescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDescription(null));
    }

    @Test
    public void constructor_invalidTaskDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDescription(""));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> TaskDescription.isValidTaskDescription(null));

        // invalid description
        assertFalse(TaskDescription.isValidTaskDescription(""));
        assertFalse(TaskDescription.isValidTaskDescription(" "));

        // valid description
        assertTrue(TaskDescription.isValidTaskDescription("Mark work"));
        assertTrue(TaskDescription.isValidTaskDescription("-"));
    }

    @Test
    public void equals() {
        TaskDescription taskDescription = new TaskDescription("Mark work");

        // same values -> returns true
        assertTrue(taskDescription.equals(new TaskDescription("Mark work")));

        // same object -> returns true
        assertTrue(taskDescription.equals(taskDescription));

        // null -> returns false
        assertFalse(taskDescription.equals(null));

        // different types -> returns false
        assertFalse(taskDescription.equals(5.0f));

        // different values -> returns false
        assertFalse(taskDescription.equals(new TaskDescription("Grade assignment")));
    }
}
