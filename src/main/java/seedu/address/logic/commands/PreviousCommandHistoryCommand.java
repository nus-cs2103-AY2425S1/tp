package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Navigate to the previous command. Detected via up-arrow key.
 */
public class PreviousCommandHistoryCommand extends Command {
    public static final String COMMAND_WORD = "upCommand";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the previous command entered. ";

    public static final String MESSAGE_SUCCESS = "Previous command is shown";
    public static final String MESSAGE_FAILURE = "There are no more previous commands.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String previousCommand = model.getPreviousCommand();
        return new CommandResult(previousCommand);
    }
}
