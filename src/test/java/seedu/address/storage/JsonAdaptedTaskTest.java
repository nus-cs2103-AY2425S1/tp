package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Todo;

public class JsonAdaptedTaskTest {

    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_DEADLINE_DATE = "32-12-2023";
    private static final String INVALID_EVENT_DATE = "invalid-date";

    private static final String VALID_TODO_DESCRIPTION = "Buy groceries";
    private static final String VALID_DEADLINE_DESCRIPTION = "Submit assignment";
    private static final String VALID_DEADLINE_DATE = "2023-12-31";
    private static final String VALID_EVENT_DESCRIPTION = "Team meeting";
    private static final String VALID_EVENT_START_DATE = "2023-10-01";
    private static final String VALID_EVENT_END_DATE = "2023-10-02";

    @Test
    public void toModelType_validTodoDetails_returnsTodo() throws Exception {
        JsonAdaptedTodo todo = new JsonAdaptedTodo(VALID_TODO_DESCRIPTION, false);
        Todo expectedTodo = new Todo(VALID_TODO_DESCRIPTION);
        assertEquals(expectedTodo, todo.toModelType());
    }

    @Test
    public void toModelType_invalidTodoDescription_throwsIllegalValueException() {
        JsonAdaptedTodo todo = new JsonAdaptedTodo(INVALID_DESCRIPTION, false);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_DEADLINE_DESCRIPTION,
                false, VALID_DEADLINE_DATE);
        Deadline expectedDeadline = new Deadline(VALID_DEADLINE_DESCRIPTION, VALID_DEADLINE_DATE);
        assertEquals(expectedDeadline, deadline.toModelType());
    }
    @Test
    public void toModelType_invalidDeadlineDate_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_DEADLINE_DESCRIPTION,
                false, INVALID_DEADLINE_DATE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_DESCRIPTION,
                false, VALID_EVENT_START_DATE, VALID_EVENT_END_DATE);
        Event expectedEvent = new Event(VALID_EVENT_DESCRIPTION, VALID_EVENT_START_DATE, VALID_EVENT_END_DATE);
        assertEquals(expectedEvent, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventDates_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_DESCRIPTION,
                false, INVALID_EVENT_DATE, INVALID_EVENT_DATE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTodo todo = new JsonAdaptedTodo(null, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description");
        assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }
}
