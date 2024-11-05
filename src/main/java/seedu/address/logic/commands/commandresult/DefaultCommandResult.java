package seedu.address.logic.commands.commandresult;

/**
 * Represents the result of a command execution with default values.
 */
public class DefaultCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public DefaultCommandResult(String feedbackToUser) {
        super(feedbackToUser, null, false, null, false, false);
    }
}
