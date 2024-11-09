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

    private Person personToShow;

    private Person personToEdit;

    private final boolean export;

    private boolean showCommand = false;

    private boolean isDelete = false;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean export) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personToShow = null;
        this.personToEdit = null;
        this.export = export;
    }

    public CommandResult(String feedbackToUser, boolean isDelete, Person originalPerson) {
        this(feedbackToUser, false, false, false);
        this.isDelete = isDelete;
        this.personToEdit = originalPerson;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false);
    }



    //@@author tayxuenye-reused
    //Written by ChatGPT
    /**
     * Constructs a {@code CommandResult} with a person to display in the UI.
     */
    public CommandResult(String feedbackToUser, Person personToEdit, Person personToShow, boolean showCommand) {
        this(feedbackToUser, false, false, false);
        this.personToShow = requireNonNull(personToShow);
        this.personToEdit = personToEdit;
        this.showCommand = showCommand;
    }
    //@@author

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Person getPersonToShow() {
        return personToShow;
    }

    public Person getOriginalPerson() {
        return personToEdit;
    }

    public boolean isShowCommand() {
        return showCommand;
    }

    public boolean isExport() {
        return export;
    }

    public boolean isDeleteCommand() {
        return isDelete;
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
                .add("personToEdit", personToEdit)
                .add("personToShow", personToShow)
                .toString();
    }

}
