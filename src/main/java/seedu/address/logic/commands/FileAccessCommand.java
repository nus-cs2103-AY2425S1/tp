package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Represents a command which saves or reads from or to the hard disk.
 */
public abstract class FileAccessCommand extends Command {
    /**
     * Executes the command and returns the result message.
     * A command should only be executed once.
     *
     * @param model {@code Model} which the command should operate on.
     * @param storage {@code Storage} where the command should save or read to or from.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, Storage storage) throws CommandException;
}
