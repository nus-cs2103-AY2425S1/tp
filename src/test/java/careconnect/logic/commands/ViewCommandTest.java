package careconnect.logic.commands;

import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static careconnect.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(command.equals(command));

        // same values -> returns true
        ViewCommand otherCommand = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(command.equals(otherCommand));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different index -> returns false
        assertFalse(command.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand command = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
