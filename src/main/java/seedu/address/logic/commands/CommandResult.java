package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean showClient;
    private final Client viewedClient;

    /** The client that was confirmed to be deleted. */
    private final boolean isConfirmedDeletion;
    private final Client deletedClient;

    /** The application should show a confirmation button */
    private final boolean showConfirmation;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean showClient, Client viewedClient,
                         boolean isConfirmedDeletion, Client deletedClient,
                         boolean showConfirmation) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showClient = showClient;
        this.viewedClient = viewedClient;
        this.isConfirmedDeletion = isConfirmedDeletion;
        this.deletedClient = deletedClient;
        this.showConfirmation = showConfirmation;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, null, false, null, false);
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

    public boolean isShowClient() {
        return showClient;
    }

    public Client getViewedClient() {
        return viewedClient;
    }

    public boolean isConfirmedDeletion() {
        return isConfirmedDeletion;
    }

    public Client getDeletedClient() {
        return deletedClient;
    }

    public boolean isShowConfirmation() {
        return showConfirmation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showClient == otherCommandResult.showClient
                && Objects.equals(viewedClient, otherCommandResult.viewedClient)
                && isConfirmedDeletion == otherCommandResult.isConfirmedDeletion
                && Objects.equals(deletedClient, otherCommandResult.deletedClient)
                && showConfirmation == otherCommandResult.showConfirmation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showClient, viewedClient,
                isConfirmedDeletion, deletedClient, showConfirmation);
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