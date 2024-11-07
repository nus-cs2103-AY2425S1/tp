package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.command.Commands;
import seedu.address.model.statistics.AddressBookStatistics;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;
    private AddressBookStatistics addressBookStatistics;
    private Commands commandList;
    private boolean isStatisticsPresent = false;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code AddressBookStatistics}.
     * CommandResult will thus be able to display statistics.
     */
    public CommandResult(String feedbackToUser, AddressBookStatistics addressBookStatistics) {
        this(feedbackToUser, false, false);
        this.isStatisticsPresent = true;
        this.addressBookStatistics = addressBookStatistics;
    }

    /**
     * Constructs a {@code CommandResult} with specifies {@code feedbackToUser} and {@code Commands}
     * @param feedbackToUser The feedback to be displayed to the user
     * @param commandList The list of commands which has to be displayed to the user
     */
    public CommandResult(String feedbackToUser, Commands commandList) {
        this(feedbackToUser, false, false);
        this.commandList = commandList;
    }

    /**
     * Returns {@code addressBookStatistics}.
     * This method should only be called when if overloaded constructor had initialised {@code addressBookStatistics}.
     */
    public AddressBookStatistics getAddressBookStatistics() {
        assert addressBookStatistics != null : "addressBookStatistics should not be null";
        return this.addressBookStatistics;
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

    public boolean isStatisticsPresent() {
        return isStatisticsPresent;
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
                .toString();
    }

}
