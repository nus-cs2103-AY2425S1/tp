package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

//@@author gho7sie

public class TaskTest {
    private static final Task FIRST_TASK = new Task(new TaskName("First task"), new Deadline(LocalDateTime.of(2024,
        10, 15, 12, 0)));
    private static final Task SECOND_TASK = new Task(new TaskName("Second task"), new Deadline(LocalDateTime.of(2024,
        10, 15, 12, 0)));
    private static final Task THIRD_TASK = new Task(new TaskName("First task"), new Deadline(LocalDateTime.of(2024,
        10, 14, 12, 0)));

    @Test
    public void testIsSameTask() {
        // different task name, same deadline -> false
        assertFalse(FIRST_TASK.isSameTask(SECOND_TASK));
        // same task name, different deadline -> false
        assertFalse(FIRST_TASK.isSameTask(THIRD_TASK));
        // null -> false
        assertFalse(SECOND_TASK.isSameTask(null));
        // same object
        assertTrue(FIRST_TASK.isSameTask(FIRST_TASK));
        // copy of same object
        Task copyFirstTask = new Task(FIRST_TASK.getTaskName(), FIRST_TASK.getDeadline());
        assertTrue(FIRST_TASK.isSameTask(copyFirstTask));
    }

    @Test
    public void testEquals() {
        // null -> false
        assertFalse(FIRST_TASK.equals(null));
        // different task -> false
        assertFalse(FIRST_TASK.equals(SECOND_TASK));
        // different type
        assertFalse(FIRST_TASK.equals(1));
        // same object
        assertTrue(FIRST_TASK.equals(FIRST_TASK));
        // different object but same fields
        assertTrue(FIRST_TASK.equals(new Task(FIRST_TASK.getTaskName(), FIRST_TASK.getDeadline())));
    }

    @Test
    public void testToString() {
        String expected = new StringBuilder()
            .append(Task.class.getCanonicalName())
            .append("{name=")
            .append(FIRST_TASK.getTaskName())
            .append(", deadline=")
            .append(FIRST_TASK.getDeadline())
            .append(", status=")
            .append(FIRST_TASK.getStatus())
            .append("}")
            .toString();
        assertEquals(expected, FIRST_TASK.toString());
    }

}
