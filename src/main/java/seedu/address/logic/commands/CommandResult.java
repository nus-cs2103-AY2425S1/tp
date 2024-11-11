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

    private final boolean showDetailedPerson;

    private final boolean showPieChart;

    private final boolean showBarChart;


    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp,
                         boolean showPieChart, boolean showBarChart, boolean showDetailedPerson, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.showPieChart = showPieChart;
        this.showBarChart = showBarChart;
        this.showDetailedPerson = showDetailedPerson;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser,
                false, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowPieChart() {
        return showPieChart;
    }

    public boolean isShowBarChart() {
        return showBarChart;
    }

    public boolean isShowDetailedPerson() {
        return showDetailedPerson;
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
                && showPieChart == otherCommandResult.showPieChart
                && showBarChart == otherCommandResult.showBarChart
                && showDetailedPerson == otherCommandResult.showDetailedPerson
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
                .add("showPieChart", showPieChart)
                .add("showBarChart", showBarChart)
                .add("showDetailedPerson", showDetailedPerson)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
