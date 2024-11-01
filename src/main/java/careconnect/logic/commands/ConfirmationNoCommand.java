package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.logic.LogicManager;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.model.Model;

/**
 * Cancels the previous command.
 */
public class ConfirmationNoCommand extends Command {

    public static final String COMMAND_WORD = "n";
    public static final String MESSAGE_CANCEL_COMMAND_SUCCESS = "Command cancelled";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Cancels the command
        LogicManager.setCommandToConfirm(null);
        return new CommandResult(MESSAGE_CANCEL_COMMAND_SUCCESS);

    }
}
