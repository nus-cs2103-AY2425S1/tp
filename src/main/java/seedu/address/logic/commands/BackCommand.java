package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

/**
 * Goes back to main window from inspect window. Nothing happens if already in main window.
 */
public class BackCommand extends Command {
    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to the main window if in inspect window. "
            + "Nothing happens if in the main window.";

    public static final String MESSAGE_SUCCESS = "Returned to main window";

    public static final String MESSAGE_INVALID_COMMAND = "This command cannot be ran in the main window!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String message = MESSAGE_SUCCESS;

        if (!AddressBookParser.getInspect()) {
            message = MESSAGE_INVALID_COMMAND;
        }

        AddressBookParser.setInspect(false);
        return new CommandResult(message, null, false, false, false, true);
    }
}
