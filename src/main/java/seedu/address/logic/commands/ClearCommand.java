package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to clear all entries in the address book.
 * This command also creates a backup of the current address book data before clearing it,
 * ensuring that users have a recovery option for the cleared data.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS =
            "ClinicBuddy has been cleared!\n\nBackup %d is created successfully.\nDescription: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book.";
    public static final String MESSAGE_BACKUP_FAILURE = "Failed to create a backup before clearing records.";

    /**
     * Executes the ClearCommand to clear the address book.
     * <p>
     * This method first attempts to create a backup of the current address book data.
     * If the backup is successful, it proceeds to clear all entries in the address book.
     * In the event of a backup failure, the clear operation will not proceed, and an exception will be thrown.
     * </p>
     *
     * @param model The model in which the command should operate. Must not be null.
     * @return CommandResult containing the result message to be displayed to the user.
     * @throws CommandException if there is an issue creating the backup.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Attempt to create a backup before clearing the address book
        String description = "clear";
        int backupIndex;
        try {
            backupIndex = model.backupData(description);
        } catch (CommandException e) {
            throw new CommandException(MESSAGE_BACKUP_FAILURE + " " + e.getMessage());
        }

        model.clearAddressBook();
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, backupIndex, description)
        );
    }
}
