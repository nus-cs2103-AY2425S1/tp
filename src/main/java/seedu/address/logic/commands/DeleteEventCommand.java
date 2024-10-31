package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an event identified using its displayed index or name from the address book.
 */
public abstract class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "del_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list "
            + "or by its name in the address book.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (must start with an alphabet)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " meeting";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
