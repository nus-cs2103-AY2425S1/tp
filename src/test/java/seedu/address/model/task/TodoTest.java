package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TodoTest {

    private static final String VALID_DESCRIPTION = "Buy groceries";
    private static final String VALID_DESCRIPTION_TRIMMED = "Clean the house";
    private static final Description DESCRIPTION_OBJ = new Description("Complete homework");

    @Test
    public void constructor_validStringDescription_success() {
        Todo todo = new Todo(VALID_DESCRIPTION);
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void constructor_validDescriptionObject_success() {
        Todo todo = new Todo(DESCRIPTION_OBJ);
        assertEquals("[T][ ] Complete homework", todo.toString());
    }

    @Test
    public void constructor_withDoneStatus_success() {
        Todo todo = new Todo(VALID_DESCRIPTION, true);
        assertEquals("[T][X] Buy groceries", todo.toString());
    }

    @Test
    public void constructor_withDescriptionObjectAndDoneStatus_success() {
        Todo todo = new Todo(DESCRIPTION_OBJ, true);
        assertEquals("[T][X] Complete homework", todo.toString());
    }

    @Test
    public void markAsDone_marksTaskAsDone() {
        Todo todo = new Todo(VALID_DESCRIPTION);
        todo.markAsDone();
        assertTrue(todo.getIsDone());
        assertEquals("[T][X] Buy groceries", todo.toString());
    }

    @Test
    public void markAsUndone_marksTaskAsUndone() {
        Todo todo = new Todo(VALID_DESCRIPTION, true);
        todo.markAsUndone();
        assertFalse(todo.getIsDone());
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void equals_sameTodo_returnsTrue() {
        Todo todo1 = new Todo(VALID_DESCRIPTION);
        Todo todo2 = new Todo(VALID_DESCRIPTION);
        assertTrue(todo1.equals(todo2));
    }

    @Test
    public void equals_differentTodo_returnsFalse() {
        Todo todo1 = new Todo(VALID_DESCRIPTION);
        Todo todo2 = new Todo("Complete homework");
        assertFalse(todo1.equals(todo2));
    }

    @Test
    public void hashCode_sameTodo_returnsSameHashCode() {
        Todo todo1 = new Todo(VALID_DESCRIPTION);
        Todo todo2 = new Todo(VALID_DESCRIPTION);
        assertEquals(todo1.hashCode(), todo2.hashCode());
    }

    @Test
    public void hashCode_differentTodo_returnsDifferentHashCode() {
        Todo todo1 = new Todo(VALID_DESCRIPTION);
        Todo todo2 = new Todo("Complete homework");
        assertFalse(todo1.hashCode() == todo2.hashCode());
    }

    @Test
    public void toString_trimsWhitespaceCorrectly() {
        Todo todo = new Todo(VALID_DESCRIPTION_TRIMMED.trim());
        assertEquals("[T][ ] Clean the house", todo.toString());
    }
}
