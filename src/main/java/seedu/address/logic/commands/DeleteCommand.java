package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Deletes a person identified using it's displayed index from PawPatrol.
 */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the owner or pet identified by the index number used in the displayed list.\n"
        + "To delete owner: " + COMMAND_WORD + " oOWNER_INDEX\n"
        + "To delete pet: " + COMMAND_WORD + " pPET_INDEX\n"
        + "Example: " + COMMAND_WORD + " o1";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
