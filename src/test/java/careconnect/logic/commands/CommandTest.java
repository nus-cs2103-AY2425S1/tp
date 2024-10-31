package careconnect.logic.commands;

import org.junit.jupiter.api.Test;

import careconnect.logic.Messages;
import careconnect.model.ModelManager;

public class CommandTest {
    @Test
    public void execute_requireConfirmableCommand_throwsException() {
        Command.commandToConfirm = null;
        CommandTestUtil.assertCommandFailure(
                new ConfirmationYesCommand(), new ModelManager(), Messages.MESSAGE_NO_EXECUTABLE_COMMAND
        );
    }

    @Test
    public void execute_requireNoUnconfirmedCommand_throwsException() {
        Command.commandToConfirm = new ClearCommand();
        CommandTestUtil.assertCommandFailure(
                new ClearCommand(), new ModelManager(), Messages.MESSAGE_UNCOMFIRMED_COMMAND
        );
        // Reset command to null
        Command.commandToConfirm = null;
    }
}
