package seedu.address.logic.commands.commandresult;

import seedu.address.model.patient.Patient;

/**
 * Represents the result of a command execution with a specified patient and
 * specified show patient information boolean value.
 */
public class ShowPatientInfoCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@code patient},
     * and other fields set to their default value.
     */
    public ShowPatientInfoCommandResult(String feedbackToUser, Patient patient, boolean isShowPatientInfo) {
        super(feedbackToUser, null, false, patient, isShowPatientInfo, false, false);
    }
}
