package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false,
                false, false, null);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpCommand = new HelpCommand();

        // same object -> returns true
        assertTrue(helpCommand.equals(helpCommand));

        // null -> returns false
        assertFalse(helpCommand.equals(null));

        // different types -> returns false
        assertFalse(helpCommand.equals(1));
    }
}
