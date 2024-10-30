package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all backup files available in the /backups/ directory.
 */
public class ListBackupsCommand extends Command {

    public static final String COMMAND_WORD = "listbackups";
    public static final String MESSAGE_SUCCESS = "Available backups:\n%s";
    public static final String MESSAGE_NO_BACKUPS = "No backups available.";
    public static final String MESSAGE_FAILURE = "Failed to list backups: %s";

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
