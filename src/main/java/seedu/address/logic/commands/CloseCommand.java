package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Closes the detail view in the address book.
 */
public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String MESSAGE_SUCCESS = "Detail view closed.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof CloseCommand;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
