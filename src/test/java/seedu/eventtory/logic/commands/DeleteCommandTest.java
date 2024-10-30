package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.model.Model;

/**
 * Contains unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommandStub deleteCommand = new DeleteCommandStub(targetIndex);
        String expected = DeleteCommandStub.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    private class DeleteCommandStub extends DeleteCommand {
        public DeleteCommandStub(Index targetIndex) {
            super(targetIndex);
        }

        @Override
        public CommandResult execute(Model model) {
            // This method is not used in the test
            return null;
        }
    }
}
