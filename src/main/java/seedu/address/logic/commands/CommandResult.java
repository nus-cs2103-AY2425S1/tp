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

    /**
     * Which view to switch to.
     */
    public enum SwitchView {
        PERSON,
        WEDDING,
        NONE
    }

    private final SwitchView switchView;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, SwitchView view) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        switchView = view;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, SwitchView.NONE);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code showHelp}, and
     * {@code exit} with {@code switchView} set to none.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, SwitchView.NONE);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, the view the user wants
     * to switch to and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, SwitchView view) {
        this(feedbackToUser, false, false, view);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    /**
     * Checks if the command requires switching view.
     */
    public boolean isSwitchView() {
        return switch (switchView) {
        case PERSON, WEDDING -> true;
        default -> false;
        };
    }

    public SwitchView getView() {
        return switchView;
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && switchView == otherCommandResult.switchView;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, switchView);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("switchView", switchView)
                .toString();
    }

}
