package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;

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
    private final boolean list;

    // ^ NEW: might not the best as well, but there is a need to bring out more information
    // to the GUI layer so we can update and reflect the sessionlog. please think of a better way

    /** The application should use the index returned. */ // NEW: I dont think this is a good implementation.
    private final int personIndex;

    /** The previous command prompts the user for confirmation */
    private final boolean hasPrompt;

    /** The application should show a popup */
    private final boolean isPopup;

    private final IdentityNumber identityNumber;
    private final AppointmentDate appointmentDate;
    private final String logEntry;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean isPrompt,
                         boolean list, int personIndex, boolean isPopup, IdentityNumber identityNumber,
                         AppointmentDate appointmentDate, String logEntry) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.list = list;
        this.personIndex = personIndex;
        this.hasPrompt = isPrompt;
        // To encapsulate this as a class
        this.isPopup = isPopup;
        this.identityNumber = identityNumber;
        this.appointmentDate = appointmentDate;
        this.logEntry = logEntry;
    }

    /**
     * Factory method that constructs a {@code CommandResult} with feedback to user.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false,
                false, -1, false, null, null, null);
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


    public boolean isList() {
        return list;
    }

    // might need more validation to check if personIndex > -1 before retrieving?
    public int getPersonIndex() {
        return personIndex;
    }


    public boolean hasPrompt() {
        return hasPrompt;
    }

    public boolean isPopup() {
        return isPopup;
    }

    public IdentityNumber getIdentityNumber() {
        return identityNumber;
    }

    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getLogEntry() {
        return logEntry;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && list == otherCommandResult.list
                && personIndex == otherCommandResult.personIndex
                && hasPrompt == otherCommandResult.hasPrompt
                && isPopup == otherCommandResult.isPopup
                // Uses null safe equals method since the following fields can be null
                && Objects.equals(identityNumber, otherCommandResult.identityNumber)
                && Objects.equals(appointmentDate, otherCommandResult.appointmentDate)
                && Objects.equals(logEntry, otherCommandResult.logEntry);
    }


    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, list, personIndex, hasPrompt);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("prompt", hasPrompt)
                .add("list", list)
                .add("personIndex", personIndex)
                .add("isPopup", isPopup)
                .add("identityNumber", identityNumber)
                .add("appointmentDate", appointmentDate)
                .add("logEntry", logEntry)
                .toString();
    }

}
