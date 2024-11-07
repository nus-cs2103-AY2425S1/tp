package seedu.address.logic.commands.commandresult;

/**
 * Represents the result of a command execution with a specified keyword value.
 */
public class KeywordCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@code keyword},
     * and other fields set to their default value.
     */
    public KeywordCommandResult(String feedbackToUser, String keyword) {
        super(feedbackToUser, keyword, true, null, false, false);
    }
}
