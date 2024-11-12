package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.MARKING_TASK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;

public class JsonAdaptedTaskTest {
    private static final String INVALID_TASK_DESCRIPTION = " ";
    private static final String INVALID_TASK_DEADLINE = "tomorrow";

    private static final String VALID_TASK_DESCRIPTION = MARKING_TASK.getTaskDescription().toString();
    private static final String VALID_TASK_DEADLINE = MARKING_TASK.getTaskDeadline().toString();

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(MARKING_TASK);
        assertEquals(MARKING_TASK, task.toModelType());
    }

    @Test
    public void toModelType_invalidTaskDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(INVALID_TASK_DESCRIPTION, VALID_TASK_DEADLINE);
        String expectedMessage = TaskDescription.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskDescription_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_TASK_DEADLINE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTaskDeadline_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASK_DESCRIPTION, INVALID_TASK_DEADLINE);
        String expectedMessage = TaskDeadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskDeadline_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_TASK_DESCRIPTION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDeadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }
}
