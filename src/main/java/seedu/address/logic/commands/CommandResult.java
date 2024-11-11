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
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Person List should be shown to the user, and Event List should be hidden. */
    private final boolean showPersonList;

    /** Event List should be shown to the user, and Person List should be hidden. */
    private final boolean showEventList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp,
            boolean exit, boolean showPersonList, boolean showEventList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showPersonList = showPersonList;
        this.showEventList = showEventList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showHelp}, {@code exit} and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, true, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, true, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified
     * {@code feedbackToUser}, {@code showEventList},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showEventList) {
        this(feedbackToUser, false, false, !showEventList, showEventList);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowPersonList() {
        return showPersonList;
    }

    public boolean isShowEventList() {
        return showEventList;
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showPersonList == otherCommandResult.showPersonList
                && showEventList == otherCommandResult.showEventList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showPersonList, showEventList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showPersonList", showPersonList)
                .add("showEventList", showEventList)
                .toString();
    }

}
