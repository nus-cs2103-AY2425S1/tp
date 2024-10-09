package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Confirms whether user wants to clear the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_FAILURE = "Address book has not been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear all your contacts? This is an "
            + "irreversible action. (Y/Yes or N/No)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_CONFIRMATION, false, false, true);
    }
}
