package seedu.address.model.student.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskDeadlineTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskDeadline(null));
    }

    @Test
    public void constructor_invalidTaskDeadline_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDeadline(""));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> TaskDeadline.isValidTaskDeadline(null));

        // invalid deadline
        assertFalse(TaskDeadline.isValidTaskDeadline(""));
        assertFalse(TaskDeadline.isValidTaskDeadline(" "));
        assertFalse(TaskDeadline.isValidTaskDeadline("tomorrow"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2024"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-1-1"));

        // valid deadline
        assertTrue(TaskDeadline.isValidTaskDeadline("2026-12-31"));
        assertTrue(TaskDeadline.isValidTaskDeadline("2024-01-01"));
    }

    @Test
    public void equals() {
        TaskDeadline taskDeadline = new TaskDeadline("2024-01-01");

        // same values -> returns true
        assertTrue(taskDeadline.equals(new TaskDeadline("2024-01-01")));

        // same object -> returns true
        assertTrue(taskDeadline.equals(taskDeadline));

        // null -> returns false
        assertFalse(taskDeadline.equals(null));

        // different types -> returns false
        assertFalse(taskDeadline.equals(5.0f));

        // different values -> returns false
        assertFalse(taskDeadline.equals(new TaskDeadline("2023-01-01")));
    }
}
