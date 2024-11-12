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
    private final boolean isShowHelp;

    /** Full patient info should be shown to the user. */
    private final boolean isShowPatientInfo;

    /** Filtered Appointments to be displayed */
    private final boolean isShowFilteredAppts;

    /** Target patient whose info is to be displayed */
    private final Patient patient;

    /** The application should isExit. */
    private final boolean isExit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, String keyword, boolean isShowHelp, Patient patient,
            boolean isShowPatientInfo, boolean isExit, boolean isShowFilteredAppts) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.keyword = keyword;
        this.isShowHelp = isShowHelp;
        this.patient = patient;
        this.isShowPatientInfo = isShowPatientInfo;
        this.isExit = isExit;
        this.isShowFilteredAppts = isShowFilteredAppts;
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
        return isShowHelp;
    }

    public boolean isShowPatientInfo() {
        return isShowPatientInfo;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isShowFilteredAppts() {
        return isShowFilteredAppts;
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
                && isShowHelp == otherCommandResult.isShowHelp
                && isShowPatientInfo == otherCommandResult.isShowPatientInfo
                && Objects.equals(patient, otherCommandResult.patient)
                && isExit == otherCommandResult.isExit
                && isShowFilteredAppts == otherCommandResult.isShowFilteredAppts
                && Objects.equals(keyword, otherCommandResult.keyword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, keyword, isShowHelp, isShowPatientInfo,
                patient, isShowFilteredAppts, isExit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("isShowHelp", isShowHelp)
                .add("isShowPatientInfo", isShowPatientInfo)
                .add("isShowFilteredAppts", isShowFilteredAppts)
                .add("keyword", keyword)
                .add("patient", patient)
                .add("isExit", isExit)
                .toString();
    }

}
