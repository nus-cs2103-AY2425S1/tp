package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        task1 = new Task("Complete project");
        task2 = new Task(new Description("Submit report"));
    }

    @Test
    public void constructor_validStringDescription_success() {
        Task task = new Task("Buy groceries");
        assertEquals("[ ] Buy groceries", task.toString());
    }

    @Test
    public void constructor_validDescriptionObject_success() {
        Task task = new Task(new Description("Clean the house"));
        assertEquals("[ ] Clean the house", task.toString());
    }

    @Test
    public void markAsDone_marksTaskAsDone() {
        task1.markAsDone();
        assertTrue(task1.getIsDone());
        assertEquals("[X] Complete project", task1.toString());
    }

    @Test
    public void markAsUndone_marksTaskAsNotDone() {
        task1.markAsDone();
        task1.markAsUndone();
        assertFalse(task1.getIsDone());
        assertEquals("[ ] Complete project", task1.toString());
    }

    @Test
    public void hasKeywordInPartialDescription_keywordFound_returnsTrue() {
        assertTrue(task1.hasKeywordInPartialDescription("project"));
        assertTrue(task2.hasKeywordInPartialDescription("Submit"));
        assertTrue(task2.hasKeywordInPartialDescription("report"));
    }

    @Test
    public void hasKeywordInPartialDescription_keywordNotFound_returnsFalse() {
        assertFalse(task1.hasKeywordInPartialDescription("report"));
        assertFalse(task2.hasKeywordInPartialDescription("project"));
    }

    @Test
    public void isSameTask_sameDescription_returnsTrue() {
        Task task3 = new Task("Complete project");
        assertTrue(task1.isSameTask(task3));
    }

    @Test
    public void isSameTask_differentDescription_returnsFalse() {
        assertFalse(task1.isSameTask(task2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(task1.equals(task1));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Task task3 = new Task("Complete project");
        assertTrue(task1.equals(task3));
    }

    @Test
    public void equals_differentDescription_returnsFalse() {
        assertFalse(task1.equals(task2));
    }

    @Test
    public void equals_differentStatus_returnsFalse() {
        task1.markAsDone();
        Task task3 = new Task("Complete project"); // Task with same description but not done
        assertFalse(task1.equals(task3));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        Task task3 = new Task("Complete project");
        assertEquals(task1.hashCode(), task3.hashCode()); // Same description and status
    }

    @Test
    public void toString_correctFormatting() {
        assertEquals("[ ] Complete project", task1.toString());
        task1.markAsDone();
        assertEquals("[X] Complete project", task1.toString());
    }
}
