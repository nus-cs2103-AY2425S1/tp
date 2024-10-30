package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    public static final Index NO_INDEX_TO_VIEW = null;

    private final String feedbackToUser;

    /**
     * User wants to view all information about a particular contact.
     * May or may not hold an {@code Index}, depending on whether the user has requested to view a contact.
     */
    private final Optional<Index> indexToView;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Focus Index on UI after this command */
//    private final Index focusIndex;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Index indexToView, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.indexToView = Optional.ofNullable(indexToView);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, NO_INDEX_TO_VIEW, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Optional<Index> getIndexToView() {
        return indexToView;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
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
                && indexToView == otherCommandResult.indexToView
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, indexToView, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("indexToView", indexToView)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
