package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Views the full profile of a patient in the database.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Here are the patient details\n"
            + "Input \"home\" to return to home page";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient with the specified NRIC not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views full profile of identified patient in the system\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";
    private static final Logger logger = Logger.getLogger(ViewCommand.class.getName());

    private final Nric targetNric;

    /**
     * Creates a ViewCommand to view the patient with the specified {@code Nric}.
     */
    public ViewCommand(Nric targetNric) {
        this.targetNric = targetNric;
        assert targetNric != null : "Nric should not be null";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        // Find the patient with the given nric
        Optional<Patient> optionalPatient = lastShownList.stream()
                .filter(patient -> patient.getNric().equals(targetNric))
                .findFirst();

        if (!optionalPatient.isPresent()) {
            logger.warning("Patient with NRIC " + targetNric + " not found");
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();
        assert patient != null : "Patient should not be null after being found";

        logger.info("Retrieved patient info of : " + patient);
        return new ShowPatientInfoCommandResult(generateSuccessMessage(patient), patient, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand e = (ViewCommand) other;
        return targetNric.equals(e.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .toString();
    }

    private String generateSuccessMessage(Patient patient) {
        assert patient != null : "Patient should not be null when generating success message";
        return String.format(MESSAGE_VIEW_SUCCESS, patient.getName());
    }

}
