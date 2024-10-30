package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command to manually create a backup of the current data.
 * Allows an optional action description for the backup.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a backup with an optional action description.\n"
            + "Example: " + COMMAND_WORD + " myBackup";
    public static final String MESSAGE_SUCCESS = "Backup %d is created successfully.";

    private final String actionDescription;

    public BackupCommand(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        int backupIndex = model.backupData(actionDescription); // Get the index used
        String message = String.format(MESSAGE_SUCCESS, backupIndex);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof BackupCommand)) {
            return false;
        } else {
            BackupCommand otherCommand = (BackupCommand) other;
            return Objects.equals(this.actionDescription, otherCommand.actionDescription);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionDescription);
    }
}
