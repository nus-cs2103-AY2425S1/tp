package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * The types of results, used by {@code MainWindow} to determine how the result should be interpreted.
     */
    public enum Type {
        ORDINARY,
        SHOW_HELP,
        EXIT,
        PROMPT,
        IMPORT_DATA,
        EXPORT_DATA;
    }

    private final String feedbackToUser;

    private final Type type;

    /**
     * Command history should be shown to user.
     */
    private String history;

    /**
     * Function that should be run after prompting the user for confirmation.
     */
    private final Supplier<CommandResult> continuationFunction;

    /**
     * Function that should be run after prompting the user for a file.
     */
    private final Function<Boolean, CommandResult> fileProcessor;

    private CommandResult(String feedbackToUser, Type type, String history,
                          Supplier<CommandResult> continuationFunction,
                          Function<Boolean, CommandResult> fileProcessor) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = type;
        this.history = history;
        this.continuationFunction = continuationFunction;
        this.fileProcessor = fileProcessor;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String history) {
        this(feedbackToUser,
                showHelp ? Type.SHOW_HELP : exit ? Type.EXIT : Type.ORDINARY,
                history, null, null);
        assert (!showHelp || !exit); // No commands result in showing help window AND exiting the app
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, Type.ORDINARY, "", null, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * setting command history,
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String history) {
        this(feedbackToUser, Type.ORDINARY, history, null, null);
    }

    /**
     * Constructs a {@code CommandResult} that represents a request to prompt user for confirmation.
     *
     * @param feedbackToUser message that is displayed to user during prompt
     * @param continuationFunction function that will be applied if user confirms the prompt
     */
    public CommandResult(String feedbackToUser, Supplier<CommandResult> continuationFunction) {
        this(feedbackToUser, Type.PROMPT, "", continuationFunction, null);
    }

    public CommandResult(String feedbackToUser, boolean writeToFile,
                         Function<Boolean, CommandResult> fileProcessor) {
        this(feedbackToUser, writeToFile ? Type.EXPORT_DATA : Type.IMPORT_DATA, "", null, fileProcessor);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Type getType() {
        return type;
    }

    /**
     * Applies the continuation function.
     * @return {@code CommandResult} that is returned by the continuation function
     */
    public CommandResult confirmPrompt() {
        return continuationFunction.get();
    }

    /**
     * Processes the {@code file} and returns the result.
     */
    public CommandResult processFile(boolean success) {
        return fileProcessor.apply(success);
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
                && type == otherCommandResult.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, type);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("type", type)
                .add("history", history)
                .toString();
    }

}
