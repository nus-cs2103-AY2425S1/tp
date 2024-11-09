package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 * <p>
 * Contains information about the outcome of a command, including feedback to the user and flags indicating
 * whether certain actions should be taken by the UI, such as showing help or exiting the application.
 * It can also indicate if the command requires user confirmation before proceeding.
 * </p>
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;
    private final boolean requiresConfirmation;


    /**
     * Constructs a {@code CommandResult} with the specified fields.
     *
     * @param feedbackToUser       The feedback message to be shown to the user. Must not be null.
     * @param showHelp             {@code true} if help information should be shown to the user.
     * @param exit                 {@code true} if the application should exit.
     * @param requiresConfirmation {@code true} if the command requires user confirmation before proceeding.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean requiresConfirmation) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.requiresConfirmation = requiresConfirmation;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default values.
     *
     * @param feedbackToUser The feedback message to be shown to the user. Must not be null.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
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

    public boolean requiresConfirmation() {
        return requiresConfirmation;
    }

    /**
     * Checks whether this {@code CommandResult} is equal to another object.
     * Returns {@code true} if both objects are of the same class and all fields match.
     *
     * @param other The object to compare to.
     * @return {@code true} if the given object is equivalent to this result; {@code false} otherwise.
     */
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
                && requiresConfirmation == otherCommandResult.requiresConfirmation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, requiresConfirmation);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("requiresConfirmation", requiresConfirmation)
                .toString();
    }

}
