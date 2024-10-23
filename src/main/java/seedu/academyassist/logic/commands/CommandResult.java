package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.academyassist.commons.util.ToStringBuilder;
import seedu.academyassist.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The specific student detail should be shown to the user. */
    private final boolean showDetailWindow;

    /** The specific student to display his or her detail */
    private final Person personToShow;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showDetailWindow,
                         Person personToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);

        if (showDetailWindow) {
            requireNonNull(personToShow, "Person cannot be null when showing detail window");
        }

        this.showHelp = showHelp;
        this.exit = exit;
        this.showDetailWindow = showDetailWindow;
        this.personToShow = personToShow;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false,
                false, null);
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

    public boolean isShowDetailWindow() {
        return showDetailWindow;
    }

    public Person getPersonToShow() {
        return personToShow;
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
                && showDetailWindow == otherCommandResult.showDetailWindow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showDetailWindow);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showDetailWindow", showDetailWindow)
                .add("personToShow", personToShow)
                .toString();
    }

}
