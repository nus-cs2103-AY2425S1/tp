package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.ui.DisplayType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Enum indicating which list should be shown to the user for the top panel. */
    private final DisplayType topPanelDisplayType;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, DisplayType topPanelDisplayType, boolean showHelp,
            boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.topPanelDisplayType = topPanelDisplayType;
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, DisplayType.SAME_AS_PREVIOUS, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code topPanelDisplayType}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, DisplayType topPanelDisplayType) {
        this(feedbackToUser, topPanelDisplayType, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayType getTopPanelDisplayType() {
        return topPanelDisplayType;
    }

    public boolean isShowHelp() {
        return showHelp;
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
                && topPanelDisplayType == otherCommandResult.topPanelDisplayType
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, topPanelDisplayType, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("topPanelDisplayType", topPanelDisplayType)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
