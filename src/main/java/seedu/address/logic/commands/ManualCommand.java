package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full instructions for every command for display.
 */
public class ManualCommand extends Command {

    public static final String COMMAND_WORD = "manual";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the user manual.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NOT_IMPLEMENTED = "Manual command not implemented";

    public static final String SHOWING_MANUAL_MESSAGE = "Opened user manual.";

    public ManualCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED);
    }
}
