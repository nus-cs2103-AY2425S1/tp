package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isHelpSelected;

    /** The application should exit. */
    private final boolean isExitSelected;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isHelpSelected, boolean isExitSelected) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isHelpSelected = isHelpSelected;
        this.isExitSelected = isExitSelected;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return isHelpSelected;
    }

    public boolean isExit() {
        return isExitSelected;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isHelpSelected == otherCommandResult.isHelpSelected
                && isExitSelected == otherCommandResult.isExitSelected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isHelpSelected, isExitSelected);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", isHelpSelected)
                .add("exit", isExitSelected)
                .toString();
    }

}
