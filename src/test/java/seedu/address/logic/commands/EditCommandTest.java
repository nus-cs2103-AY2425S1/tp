package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Contains unit tests for
 * {@code EditCommand}.
 */
public class EditCommandTest {

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        EditCommandStub editCommand = new EditCommandStub(targetIndex);
        String expected = EditCommandStub.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, editCommand.toString());
    }

    private class EditCommandStub extends EditCommand {
        public EditCommandStub(Index targetIndex) {
            super(targetIndex);
        }

        @Override
        public CommandResult execute(Model model) {
            // This method is not used in the test
            return null;
        }
    }
}
