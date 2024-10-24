package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.logic.Messages;
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
        if (Command.STACK.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_EXECUTABLE_COMMAND);
        }

        Command commandToExecute = Command.STACK.getLast();

        // Wrap around the command to execute
        return commandToExecute.execute(model);

    }
}
