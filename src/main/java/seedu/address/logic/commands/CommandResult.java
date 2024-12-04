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

    /** The application will show list of all listings. */
    private final boolean showListings;

    /** The application will show list of all clients. */
    private final boolean showClients;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean showListings, boolean showClients) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showListings = showListings;
        this.showClients = showClients;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false,
                false, false);
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

    public boolean isShowListings() {
        return showListings;
    }

    public boolean isShowClients() {
        return showClients;
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

        boolean hasSameFeedBackToUser = feedbackToUser.equals(otherCommandResult.feedbackToUser);
        boolean hasSameShowHelp = showHelp == otherCommandResult.showHelp;
        boolean hasSameExit = exit == otherCommandResult.exit;
        boolean hasSameShowListings = showListings == otherCommandResult.showListings;
        boolean hasSameShowClients = showClients == otherCommandResult.showClients;

        return hasSameFeedBackToUser && hasSameShowHelp && hasSameExit && hasSameShowListings && hasSameShowClients;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showListings, showClients);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showListings", showListings)
                .add("showClients", showClients)
                .toString();
    }

}
