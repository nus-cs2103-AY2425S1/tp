package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

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

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommandStub deleteCommand = new DeleteCommandStub(targetIndex);
        String expected = DeleteCommandStub.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
