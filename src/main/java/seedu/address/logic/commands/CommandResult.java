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

    /** The application should list the logs. */
    private final boolean list;

    // ^ NEW: might not the best as well, but there is a need to bring out more information
    // to the GUI layer so we can update and reflect the sessionlog. please think of a better way

    /** The application should use the index returned. */ // NEW: I dont think this is a good implementation.
    private final int personIndex;

    /** The previous command prompts the user for confirmation */
    private final boolean prompt;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean isPrompt, boolean list, int personIndex) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.list = list;
        this.personIndex = personIndex;
        this.prompt = isPrompt;
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, -1);
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

    public boolean isList() {
        return list;
    }

    // might need more validation to check if personIndex > -1 before retrieving?
    public int getPersonIndex() {
        return personIndex;
    }

    public boolean isPrompt() {
        return prompt;
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
                && list == otherCommandResult.list
                && personIndex == otherCommandResult.personIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, list, personIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("prompt", prompt)
                .add("list", list)
                .add("personIndex", personIndex)
                .toString();
    }

}
