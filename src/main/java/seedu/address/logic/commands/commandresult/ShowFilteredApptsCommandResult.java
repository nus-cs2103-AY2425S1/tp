package seedu.address.logic.commands.commandresult;

/**
 * Represents the result of a command execution with a specified filtered appts boolean value.
 */
public class ShowFilteredApptsCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified showFilteredAppts boolean value,
     * and other fields set to their default value.
     */
    public ShowFilteredApptsCommandResult(String feedbackToUser, boolean isShowFilteredAppts) {
        super(feedbackToUser, null, false, null, false, false, isShowFilteredAppts);
    }
}
