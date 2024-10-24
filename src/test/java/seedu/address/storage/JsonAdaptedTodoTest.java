package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Todo;

public class JsonAdaptedTodoTest {

    private static final String VALID_DESCRIPTION = "Buy groceries";
    private static final String INVALID_DESCRIPTION = ""; // Invalid due to being empty
    private static final boolean IS_DONE = false;

    @Test
    public void toModelType_validTodoDetails_returnsTodo() throws Exception {
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(VALID_DESCRIPTION, IS_DONE);
        Todo expectedTodo = new Todo(VALID_DESCRIPTION);
        assertEquals(expectedTodo, jsonAdaptedTodo.toModelType());
    }

    @Test
    public void toModelType_invalidTodoDescription_throwsIllegalValueException() {
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(INVALID_DESCRIPTION, IS_DONE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedTodo::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(null, IS_DONE);
        String expectedMessage = String.format(JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT, "Description");
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedTodo::toModelType);
    }

    @Test
    public void toModelType_validTodoWithDone_returnsCorrectly() throws Exception {
        JsonAdaptedTodo jsonAdaptedTodo = new JsonAdaptedTodo(VALID_DESCRIPTION, true);
        Todo expectedTodo = new Todo(VALID_DESCRIPTION, true);
        assertEquals(expectedTodo, jsonAdaptedTodo.toModelType());
    }
}
