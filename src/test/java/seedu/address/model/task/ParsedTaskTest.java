package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParsedTaskTest {

    private static final String VALID_TASK_TYPE = "todo";
    private static final String VALID_TASK_DETAILS = "Buy groceries";
    private static final String DIFFERENT_TASK_TYPE = "event";
    private static final String DIFFERENT_TASK_DETAILS = "Team meeting";

    @Test
    public void constructor_validTaskTypeAndDetails_success() {
        ParsedTask parsedTask = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertEquals(VALID_TASK_TYPE, parsedTask.getTaskType());
        assertEquals(VALID_TASK_DETAILS, parsedTask.getTaskDetails());
    }

    @Test
    public void getTaskType_returnsCorrectTaskType() {
        ParsedTask parsedTask = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertEquals(VALID_TASK_TYPE, parsedTask.getTaskType());
    }

    @Test
    public void getTaskDetails_returnsCorrectTaskDetails() {
        ParsedTask parsedTask = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertEquals(VALID_TASK_DETAILS, parsedTask.getTaskDetails());
    }

    @Test
    public void equals_sameParsedTask_returnsTrue() {
        ParsedTask parsedTask1 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        ParsedTask parsedTask2 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertTrue(parsedTask1.equals(parsedTask2));
    }

    @Test
    public void equals_differentParsedTask_returnsFalse() {
        ParsedTask parsedTask1 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        ParsedTask parsedTask2 = new ParsedTask(DIFFERENT_TASK_TYPE, DIFFERENT_TASK_DETAILS);
        assertFalse(parsedTask1.equals(parsedTask2));
    }

    @Test
    public void equals_nullParsedTask_returnsFalse() {
        ParsedTask parsedTask = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertFalse(parsedTask.equals(null));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        ParsedTask parsedTask = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertFalse(parsedTask.equals("some string"));
    }
    @Test
    public void hashCode_sameParsedTask_returnsSameHashCode() {
        ParsedTask parsedTask1 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        ParsedTask parsedTask2 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        assertEquals(parsedTask1.hashCode(), parsedTask2.hashCode());
    }

    @Test
    public void hashCode_differentParsedTask_returnsDifferentHashCode() {
        ParsedTask parsedTask1 = new ParsedTask(VALID_TASK_TYPE, VALID_TASK_DETAILS);
        ParsedTask parsedTask2 = new ParsedTask(DIFFERENT_TASK_TYPE, DIFFERENT_TASK_DETAILS);
        assertFalse(parsedTask1.hashCode() == parsedTask2.hashCode());
    }
}
