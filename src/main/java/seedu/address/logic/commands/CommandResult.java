package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Command history should be shown to user.
     */
    private String history;

    /**
     * Function that should be run after prompting the user for confirmation.
     */
    private final Supplier<CommandResult> continuationFunction;

    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String history,
                          Supplier<CommandResult> continuationFunction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.history = history;
        this.continuationFunction = continuationFunction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String history) {
        this(feedbackToUser, showHelp, exit, history, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, "", null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * setting command history,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String history) {
        this(feedbackToUser, false, false, history, null);
    }

    /**
     * Constructs a {@code CommandResult} that represents a request to prompt user for confirmation.
     *
     * @param feedbackToUser message that is displayed to user during prompt
     * @param continuationFunction function that will be applied if user confirms the prompt
     */
    public CommandResult(String feedbackToUser, Supplier<CommandResult> continuationFunction) {
        this(feedbackToUser, false, false, "", continuationFunction);
    };

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Checks if the user should be prompted for confirmation.
     */
    public boolean isPromptConfirmation() {
        return continuationFunction != null;
    }

    /**
     * Applies the continuation function.
     * @return {@code CommandResult} that is returned by the continuation function
     */
    public CommandResult confirmPrompt() {
        return continuationFunction.get();
    }

    public String getHistory() {
        return history;
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
                && exit == otherCommandResult.exit;
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
                .add("history", history)
                .add("promptConfirmation", isPromptConfirmation())
                .toString();
    }

}
