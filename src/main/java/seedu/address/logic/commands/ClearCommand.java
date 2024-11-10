package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Confirms whether user wants to clear the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to permanently clear all your contacts? "
            + "\nType 'Y'/'Yes' (case-insensitive) to confirm, or any other input to cancel";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_CONFIRMATION, false, false, true);
    }
}
