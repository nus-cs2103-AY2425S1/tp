package careconnect.logic.commands;

import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static careconnect.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import careconnect.commons.core.index.Index;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {
    @Test
    public void equals() {
        ViewCommand command = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand secondCommand = new ViewCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(command, command);

        // same values -> returns true
        ViewCommand otherCommand = new ViewCommand(INDEX_FIRST_PERSON);
        assertEquals(command, otherCommand);

        // different types -> returns false
        assertNotEquals(1, otherCommand);

        // null -> returns false
        assertNotEquals(null, otherCommand);

        // different index -> returns false
        assertNotEquals(command, secondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand command = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
