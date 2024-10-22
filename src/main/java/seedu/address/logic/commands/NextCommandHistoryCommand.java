package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Navigate to the next command. Detected via down-arrow key.
 */
public class NextCommandHistoryCommand extends Command {
    public static final String COMMAND_WORD = "downCommand";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the next command entered. ";

    public static final String MESSAGE_SUCCESS = "Next command is shown";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String nextCommand = model.getNextCommand();
        return new CommandResult(nextCommand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NextCommandHistoryCommand that = (NextCommandHistoryCommand) o;

        return true;
    }
}
