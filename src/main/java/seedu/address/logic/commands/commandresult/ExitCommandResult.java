package seedu.address.logic.commands.commandresult;

/**
 * Represents the result of a command execution with a specified exit boolean value.
 */
public class ExitCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@code keyword},
     * and other fields set to their default value.
     */
    public ExitCommandResult(String feedbackToUser, boolean exit) {
        super(feedbackToUser, null, false, null, false, exit);
    }
}
