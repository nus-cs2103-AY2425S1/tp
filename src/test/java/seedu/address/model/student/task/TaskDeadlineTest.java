package seedu.address.model.student.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        // valid date for comparison
        assertTrue(TaskDeadline.isValidTaskDeadline("2024-02-29")); // Should be true in a leap year
        assertTrue(TaskDeadline.isValidTaskDeadline("2023-02-28"));
        assertTrue(TaskDeadline.isValidTaskDeadline("2026-12-31"));
        assertTrue(TaskDeadline.isValidTaskDeadline("2024-01-01"));

        // invalid deadline
        assertFalse(TaskDeadline.isValidTaskDeadline(""));
        assertFalse(TaskDeadline.isValidTaskDeadline(" "));
        assertFalse(TaskDeadline.isValidTaskDeadline("tomorrow"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2024"));
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-1-1"));

        // invalid date: February 31 does not exist
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-02-31")); // Should be false

        // other invalid dates
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-04-31")); // April has only 30 days
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-06-31")); // June has only 30 days
        assertFalse(TaskDeadline.isValidTaskDeadline("2024-09-31")); // September has only 30 days
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

    @Test
    public void hashcode_equivalents() {
        TaskDeadline taskDeadline = new TaskDeadline("2024-12-25");

        // same date -> return true
        assertEquals(taskDeadline.hashCode(), (new TaskDeadline("2024-12-25")).hashCode());

        // different values -> return false
        assertFalse(taskDeadline.hashCode() == (new TaskDeadline("2023-12-25")).hashCode());
    }
}
