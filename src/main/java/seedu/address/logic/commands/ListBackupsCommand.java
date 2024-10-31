package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to list all backup files available in the `/backups/` directory.
 * This command retrieves and displays a list of all existing backup files, or informs the user if no backups are found.
 * If an error occurs during retrieval, an appropriate error message is displayed.
 */
public class ListBackupsCommand extends Command {

    public static final String COMMAND_WORD = "listbackups";
    public static final String MESSAGE_SUCCESS = "Available backups:\n%s";
    public static final String MESSAGE_NO_BACKUPS = "No backups available.";
    public static final String MESSAGE_FAILURE = "Failed to list backups: %s";

    /**
     * Executes the ListBackupsCommand, retrieving the list of backup files from the model.
     * Displays the list of backups or a message indicating no backups are available.
     * If an error occurs, a CommandException is thrown with the error details.
     *
     * @param model The model in which the backup files are managed.
     * @return CommandResult containing the message with the list of backups or an appropriate status.
     * @throws CommandException If an error occurs while listing backups.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String backupsList = model.listAllBackups();
            if (backupsList.isEmpty()) {
                return new CommandResult(MESSAGE_NO_BACKUPS);
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS, backupsList));
            }
        } catch (Exception e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()), e);
        }
    }
}
