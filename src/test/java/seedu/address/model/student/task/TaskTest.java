package seedu.address.model.student.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null, null));
        assertThrows(NullPointerException.class, () -> new Task(new TaskDescription("Mark work"), null));
        assertThrows(NullPointerException.class, () -> new Task(null, new TaskDeadline("2024-01-01")));
    }

    @Test
    public void equals() {
        Task task = new Task(new TaskDescription("Valid description"), new TaskDeadline("2024-01-01"));

        // same values -> returns true
        assertTrue(task.equals(new Task(new TaskDescription("Valid description"), new TaskDeadline("2024-01-01"))));

        // same object -> returns true
        assertTrue(task.equals(task));

        // null -> returns false
        assertFalse(task.equals(null));

        // different types -> returns false
        assertFalse(task.equals(5.0f));

        // different values -> returns false
        assertFalse(task.equals(new Task(new TaskDescription("different"), new TaskDeadline("2024-01-01"))));
        assertFalse(task.equals(new Task(new TaskDescription("Valid description"), new TaskDeadline("2023-01-01"))));
        assertFalse(task.equals(new Task(new TaskDescription("different"), new TaskDeadline("2023-01-01"))));

    }
}
