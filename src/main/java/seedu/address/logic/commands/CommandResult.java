package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Command Type, used for deciding TAHub UI Action */
    private final CommandType commandType;

    /** Index of Tab */
    private final Index tabIndex;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbacktoUser},
     * {@code commandType} and {@code tabIndex}
     *
     * @param feedbackToUser FeedbacktoUser
     * @param commandType Command Type
     * @param tabIndex Tab Index
     */
    public CommandResult(String feedbackToUser, CommandType commandType, Index tabIndex) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.commandType = commandType;
        this.tabIndex = tabIndex;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbacktoUser},
     * {@code commandType} and other fields set to their default value.
     *
     * @param feedbackToUser FeedbacktoUser
     * @param commandType Command Type
     */
    public CommandResult(String feedbackToUser, CommandType commandType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.commandType = commandType;
        this.tabIndex = null;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Index getTabIndex() {
        return tabIndex;
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
                && commandType == otherCommandResult.commandType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, commandType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("commandType", commandType)
                .add("tabIndex", tabIndex)
                .toString();
    }

}
