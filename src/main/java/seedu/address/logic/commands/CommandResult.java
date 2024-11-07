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

    /** List of concert contacts should be shown to the user. */
    private final boolean showConcertContacts;

    /** The full details of a {@code Person} should be shown to the user. */
    private final boolean showFullPerson;

    /** The full details of a {@code Concert} should be shown to the user. */
    private final boolean showFullConcert;

    /** The full details of a {@code ConcertContact} should be shown to the user. */
    private final boolean showFullConcertContact;

    /** The full details of a {@code Person} should be hidden from the user. */
    private final boolean hideFullPerson;

    /** The full details of a {@code Concert} should be hidden from the user. */
    private final boolean hideFullConcert;

    /** The full details of a {@code ConcertContact} should be hidden from the user. */
    private final boolean hideFullConcertContact;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showConcertContacts,
             boolean showFullPerson, boolean showFullConcert, boolean showFullConcertContact,
             boolean hideFullPerson, boolean hideFullConcert, boolean hideFullConcertContact) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showConcertContacts = showConcertContacts;
        this.showFullPerson = showFullPerson;
        this.showFullConcert = showFullConcert;
        this.showFullConcertContact = showFullConcertContact;
        this.hideFullPerson = hideFullPerson;
        this.hideFullConcert = hideFullConcert;
        this.hideFullConcertContact = hideFullConcertContact;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false,
                false, false, false,
                false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showConcertContacts) {
        this(feedbackToUser, showHelp, exit, showConcertContacts,
                false, false, false,
                false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser,
             boolean showFullPerson, boolean showFullConcert, boolean showFullConcertContact,
             boolean hideFullPerson, boolean hideFullConcert, boolean hideFullConcertContact) {
        this(feedbackToUser, false, false, false,
                showFullPerson, showFullConcert, showFullConcertContact,
                hideFullPerson, hideFullConcert, hideFullConcertContact);
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

    public boolean isShowConcertContacts() {
        return showConcertContacts;
    }

    public boolean isShowFullPerson() {
        return showFullPerson;
    }

    public boolean isShowFullConcert() {
        return showFullConcert;
    }

    public boolean isShowFullConcertContact() {
        return showFullConcertContact;
    }

    public boolean isHideFullPerson() {
        return hideFullPerson;
    }

    public boolean isHideFullConcert() {
        return hideFullConcert;
    }

    public boolean isHideFullConcertContact() {
        return hideFullConcertContact;
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
                && showConcertContacts == otherCommandResult.showConcertContacts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showConcertContacts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("showConcertContacts", showConcertContacts)
                .toString();
    }

}
