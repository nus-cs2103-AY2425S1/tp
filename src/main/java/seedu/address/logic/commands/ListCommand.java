package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Resets volunteer and events display.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all volunteers and events.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully listed volunteers and events.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetDisplayLists();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
