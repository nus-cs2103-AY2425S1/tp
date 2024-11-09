package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to clear all entries in the address book.
 * <p>
 * The {@code ClearCommand} creates a backup of the current address book data before clearing it,
 * ensuring that users have a recovery option for the cleared data. If the backup fails, the clear
 * operation is aborted to prevent data loss.
 * </p>
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS =
            "ClinicBuddy has been cleared!\n\nBackup %d is created successfully.\nDescription: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book.";
    public static final String MESSAGE_BACKUP_FAILURE = "Failed to create a backup before clearing records.";

    /**
     * Executes the {@code ClearCommand} to clear the address book.
     * <p>
     * This method first attempts to create a backup of the current address book data.
     * If the backup is successful, it proceeds to clear all entries in the address book.
     * If the backup fails, the clear operation does not proceed, and an exception is thrown
     * to prevent data loss.
     * </p>
     *
     * @param model The {@code Model} in which the command operates. Must not be null.
     * @return A {@code CommandResult} containing the result message to be displayed to the user.
     * @throws CommandException If there is an issue creating the backup.
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
