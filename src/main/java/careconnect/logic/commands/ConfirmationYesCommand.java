package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.logic.LogicManager;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.model.Model;

/**
 * Confirms the previous command.
 */
public class ConfirmationYesCommand extends Command {

    public static final String COMMAND_WORD = "y";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Command commandToExecute = LogicManager.getCommandToConfirm();
        LogicManager.setCommandToConfirm(null);

        // Wrap around the command to execute
        return commandToExecute.execute(model);
    }
}
