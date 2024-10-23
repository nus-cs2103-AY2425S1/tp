package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Resets volunteer and events display.
 */
public class ResetDisplayListsCommand extends Command {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets volunteer and event display lists.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully reset volunteer and event display lists.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetDisplayLists();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
