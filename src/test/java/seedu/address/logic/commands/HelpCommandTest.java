package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HelpCommand helpCommand = new HelpCommand();
        assertTrue(helpCommand.equals(helpCommand));
    }

    @Test
    public void equals_differentInstanceSameType_returnsTrue() {
        HelpCommand helpCommand1 = new HelpCommand();
        HelpCommand helpCommand2 = new HelpCommand();
        assertTrue(helpCommand1.equals(helpCommand2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        Object notExitCommand = new Object();
        assertFalse(helpCommand.equals(notExitCommand));
    }

    @Test
    public void equals_null_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.equals(null));
    }
}
