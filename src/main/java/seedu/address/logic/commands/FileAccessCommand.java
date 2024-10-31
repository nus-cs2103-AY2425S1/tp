package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Represents a command which saves or reads from or to the hard disk.
 */
public abstract class FileAccessCommand extends Command {
    public static final String FILE_ACCESS_COMMAND_INVALID_EXECUTE_ERROR_FORMAT =
            "Ran incorrect execute variant for File Access Commands.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // This version of execute should never be run
        assert false;
        throw new CommandException(FILE_ACCESS_COMMAND_INVALID_EXECUTE_ERROR_FORMAT);
    }

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
