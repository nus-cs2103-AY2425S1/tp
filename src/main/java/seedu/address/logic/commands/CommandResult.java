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

    /** Change view to MeetUpList */
    private final boolean showMeetUpList;

    /** Change view to Budget Book */
    private final boolean showBuyerList;

    /** Change view to Property List */
    private final boolean showPropertyList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
            boolean showMeetUpList, boolean showBuyerList, boolean showPropertyList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showMeetUpList = showMeetUpList;
        this.showBuyerList = showBuyerList;
        this.showPropertyList = showPropertyList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * buyer list to true, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false);
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

    public boolean isShowMeetUpList() {
        return showMeetUpList;
    }

    public boolean isShowBuyerList() {
        return showBuyerList;
    }

    public boolean isShowPropertyList() {
        return showPropertyList;
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
                && showMeetUpList == otherCommandResult.showMeetUpList
                && showBuyerList == otherCommandResult.showBuyerList
                && showPropertyList == otherCommandResult.showPropertyList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
