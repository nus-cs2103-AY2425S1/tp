package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Deletes a person identified using it's displayed index from PawPatrol.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    private static String messageUsage;
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    public static String getMessageUsage() {
        return messageUsage;
    }

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
