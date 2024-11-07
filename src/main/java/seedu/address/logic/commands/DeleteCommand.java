package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.DefaultCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " usage: delete [NRIC]\n"
            + "Input \"help " + COMMAND_WORD + "\" for detailed description and usage of this command";

    public static final String MESSAGE_DELETE_PATIENT_SUCCESS = "Deleted Patient: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Nric targetNric;

    public DeleteCommand(Nric targetNric) {
        this.targetNric = targetNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();
        Patient patientToDelete = lastShownList.stream()
                .filter(patient -> patient.getNric().equals(targetNric))
                .findFirst()
                .orElse(null);
        if (patientToDelete == null) {
            logger.warning("Patient with NRIC " + targetNric + " not found");
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PATIENT_NRIC, targetNric));
        }

        model.deletePatient(patientToDelete);
        logger.info("Deleted patient: " + patientToDelete);

        return new DefaultCommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS,
                Messages.format(patientToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetNric.equals(otherDeleteCommand.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .toString();
    }
}
