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

    /** The application should display pet list */
    private boolean isPetListCommand;

    /** The application should display owner list */
    private boolean isOwnerListCommand;

    /** The application should display owner and pet lists */
    private boolean isCombinedListCommand;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        // insert assertion that the booleans start off as false
        setListType(feedbackToUser);
        /*
        this.isPetListCommand = false;
        this.isOwnerListCommand = false;
        this.isCombinedListCommand = false;
         */
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

    public void setListType(String feedbackToUser) {
        //insert assertion here that i is 0, 1 or 2
        if (feedbackToUser.equals(ListPetCommand.MESSAGE_SUCCESS)) {
            this.isPetListCommand = true;
        } else if (feedbackToUser.equals(ListOwnerCommand.MESSAGE_SUCCESS)) {
            this.isOwnerListCommand = true;
        } else if (feedbackToUser.equals(ListBothCommand.MESSAGE_SUCCESS)) {
            this.isCombinedListCommand = true;
        } else {
            //do nothing because command was not a successful list command
        }
    }

    public boolean isPetListCommand() {
        return this.isPetListCommand;
    }

    public boolean isOwnerListCommand() {
        return this.isOwnerListCommand;
    }

    public boolean isCombinedListCommand() {
        return this.isCombinedListCommand;
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && isPetListCommand == otherCommandResult.isPetListCommand
                && isOwnerListCommand == otherCommandResult.isOwnerListCommand
                && isCombinedListCommand == otherCommandResult.isCombinedListCommand;
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
