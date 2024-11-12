package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ExitCommand exitCommand = new ExitCommand();
        assertTrue(exitCommand.equals(exitCommand));
    }

    @Test
    public void equals_differentInstanceSameType_returnsTrue() {
        ExitCommand exitCommand1 = new ExitCommand();
        ExitCommand exitCommand2 = new ExitCommand();
        assertTrue(exitCommand1.equals(exitCommand2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ExitCommand exitCommand = new ExitCommand();
        Object notExitCommand = new Object();
        assertFalse(exitCommand.equals(notExitCommand));
    }

    @Test
    public void equals_null_returnsFalse() {
        ExitCommand exitCommand = new ExitCommand();
        assertFalse(exitCommand.equals(null));
    }

}
