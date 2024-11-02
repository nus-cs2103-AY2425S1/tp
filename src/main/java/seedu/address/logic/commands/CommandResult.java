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

    /** Indicates if the command is a group-related command. */
    private final boolean isGroupCommand;

    /**
     * Constructs a {@code CommandResult} with the specified feedback, help, and exit fields.
     *
     * @param feedbackToUser The feedback message to be shown to the user.
     * @param showHelp       Whether the help information should be shown to the user.
     * @param exit           Whether the application should exit after this command.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.isGroupCommand = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified group command fields.
     *
     * @param feedbackToUser The feedback message to be shown to the user.
     * @param isGroupCommand Whether this command pertains to group-related operations.
     */
    public CommandResult(String feedbackToUser, boolean isGroupCommand) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.isGroupCommand = isGroupCommand;
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
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isGroupCommand() {
        return isGroupCommand;
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
                && isGroupCommand == otherCommandResult.isGroupCommand;
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
                .add("isGroupCommand", isGroupCommand)
                .toString();
    }

}
