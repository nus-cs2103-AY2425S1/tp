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
    private final boolean listLogs;

    /** The application should list the person list. */
    private final boolean isPersonList;

    /** The application should use the index returned. */
    private final int personIndex;

    /** The application should refresh the session log accordingly. */
    private final boolean isAddLog;

    /** The previous command prompts the user for confirmation */
    private final boolean hasPrompt;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean isPrompt,
                         boolean list, int personIndex, boolean isPersonList, boolean isAddLog) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.listLogs = list;
        this.personIndex = personIndex;
        this.hasPrompt = isPrompt;
        this.isPersonList = isPersonList;
        this.isAddLog = isAddLog;
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, -1, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value except {@code isPersonList} set to false.
     */
    public CommandResult(String feedbackToUser, boolean isPersonList) {
        this(feedbackToUser, false, false, false, false, -1, isPersonList, false);
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


    public boolean isListLogs() {
        return listLogs;
    }

    public int getPersonIndex() {
        return personIndex;
    }

    public boolean isAddLog() {
        return isAddLog;
    }

    public boolean isPersonList() {
        return isPersonList;
    }


    public boolean hasPrompt() {
        return hasPrompt;
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
                && listLogs == otherCommandResult.listLogs
                && personIndex == otherCommandResult.personIndex
                && hasPrompt == otherCommandResult.hasPrompt
                && isPersonList == otherCommandResult.isPersonList
                && isAddLog == otherCommandResult.isAddLog;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, listLogs, personIndex, hasPrompt, isPersonList, isAddLog);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("prompt", hasPrompt)
                .add("list", listLogs)
                .add("personIndex", personIndex)
                .add("isPersonList", isPersonList)
                .add("isAddLog", isAddLog)
                .toString();
    }

}
