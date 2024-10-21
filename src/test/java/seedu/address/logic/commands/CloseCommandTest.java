package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CloseCommandTest {

    private Model model = new ModelManager();
    @Test
    public void execute_close_success() {
        CloseCommand closeCommand = new CloseCommand();
        CommandResult commandResult = closeCommand.execute(model);

        assertEquals(CloseCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void equals() {
        CloseCommand closeCommand = new CloseCommand();

        // same object -> returns true
        assertTrue(closeCommand.equals(closeCommand));

        // same type -> returns true
        assertTrue(closeCommand.equals(new CloseCommand()));

        // null -> returns false
        assertFalse(closeCommand.equals(null));

        // different types -> returns false
        assertFalse(closeCommand.equals(1));
    }

    @Test
    public void toStringMethod() {
        CloseCommand closeCommand = new CloseCommand();
        assertEquals(CloseCommand.COMMAND_WORD, closeCommand.toString());
    }
}
