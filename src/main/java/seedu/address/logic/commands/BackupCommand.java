package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to manually create a backup of the current data.
 * Allows an optional file name for the backup, otherwise generates a backup with a default timestamp-based name.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_SUCCESS = "Manual backup completed successfully.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a backup with an optional file name.\n"
            + "Example: " + COMMAND_WORD + " myBackup";

    private final String fileName;

    public BackupCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.backupData(fileName); // Pass filename to model
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof BackupCommand)) {
            return false;
        } else {
            BackupCommand otherCommand = (BackupCommand) other;
            return Objects.equals(this.fileName, otherCommand.fileName);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
