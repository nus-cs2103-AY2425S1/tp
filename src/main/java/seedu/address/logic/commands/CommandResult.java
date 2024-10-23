package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private boolean isView = false;

    private Person person = null;

    private boolean profileSwitched = false;

    /**
     * Constructs a {@code CommandResult} that's specifically a 'view' command
     * @param feedbackToUser
     * @param isView boolean value that's always initialised to 'true'
     * @param person Person object initialised to 'person' field
     */
    public CommandResult(String feedbackToUser, boolean isView, Person person) {
        this.feedbackToUser = feedbackToUser;
        this.isView = isView;
        this.showHelp = false;
        this.exit = false;
        this.person = person;
    }

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

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isView() {
        return this.isView;
    }

    public Person getViewPerson() {
        return this.person;
    }

    public boolean isProfileSwitched() {
        return this.profileSwitched;
    }

    /**
     * Marks that a profile switch has occurred and returns the updated {@code CommandResult}.
     *
     * @return The updated {@code CommandResult} with the profile switch marked.
     */
    public CommandResult markProfileSwitched() {
        this.profileSwitched = true;
        return this;
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
