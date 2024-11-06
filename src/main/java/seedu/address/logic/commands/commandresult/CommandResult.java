package seedu.address.logic.commands.commandresult;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.patient.Patient;

/**
 * Represents the result of a command execution.
 */
public abstract class CommandResult {

    private final String feedbackToUser;

    private final String keyword;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** Full patient info should be shown to the user. */
    private final boolean showPatientInfo;

    /** Target patient whose info is to be displayed */
    private final Patient patient;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, String keyword, boolean showHelp, Patient patient,
            boolean showPatientInfo, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.keyword = keyword;
        this.showHelp = showHelp;
        this.patient = patient;
        this.showPatientInfo = showPatientInfo;
        this.exit = exit;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public Patient getPatient() {
        return patient;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowPatientInfo() {
        return showPatientInfo;
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
                && showPatientInfo == otherCommandResult.showPatientInfo
                && Objects.equals(patient, otherCommandResult.patient)
                && exit == otherCommandResult.exit
                && Objects.equals(keyword, otherCommandResult.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, keyword, showHelp, showPatientInfo, patient, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("showPatientInfo", showPatientInfo)
                .add("keyword", keyword)
                .add("patient", patient)
                .add("exit", exit)
                .toString();
    }

}
