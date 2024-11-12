package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;
import seedu.address.ui.Ui.UiState;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** UI State should be changed accordingly. */
    private final UiState uiState;

    /** Details of student should be displayed */
    private final Student studentToView;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    private CommandResult(String feedbackToUser, boolean showHelp, boolean exit, UiState uiState,
                          Student studentToView) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiState = uiState;
        this.studentToView = studentToView;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showHelp}, and {@code exit},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, UiState.NO_CHANGE, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}
     * and {@code uiState},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, UiState uiState) {
        this(feedbackToUser, false, false, uiState, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showHelp}, and {@code exit},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Student studentToView) {
        this(feedbackToUser, false, false, UiState.SPECIFIC_DETAILS, studentToView);
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

    public UiState getUiState() {
        return uiState;
    }

    public Student getStudentToView() {
        return studentToView;
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
                && uiState == otherCommandResult.uiState
                && studentToView == otherCommandResult.studentToView;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, uiState, studentToView);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("uiState", uiState)
                .add("studentToView", studentToView)
                .toString();
    }

}
