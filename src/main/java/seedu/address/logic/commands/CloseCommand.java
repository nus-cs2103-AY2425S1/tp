package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandCommons.EMPTY_PERSON;

import seedu.address.model.Model;

/**
 * Closes the detail view in the address book.
 */
public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String MESSAGE_SUCCESS = "Detailed view closed.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(
                MESSAGE_SUCCESS,
                false,
                false,
                false,
                EMPTY_PERSON,
                false
        );
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
