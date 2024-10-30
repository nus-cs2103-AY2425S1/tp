package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Views the full profile of a patient in the database.
 */
public class ViewCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "View: %2$s, Nric: %1$s";
    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Here are the patient details.";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views full profile of identified patient in the system\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";

    private final Nric nric;

    /**
     * Creates a ViewCommand to view the patient with the specified {@code Nric}.
     */
    public ViewCommand(Nric nric) {
        this.nric = nric;
        assert nric != null : "Nric should not be null";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        // Find the patient with the given nric
        Optional<Patient> optionalPatient = lastShownList.stream()
                .filter(patient -> patient.getNric().equals(nric))
                .findFirst();

        if (!optionalPatient.isPresent()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();
        assert patient != null : "Patient should not be null after being found";

        return new CommandResult(generateSuccessMessage(patient), null, false, patient,
                true, false);
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
        return nric.equals(e.nric);
    }

    private String generateSuccessMessage(Patient patient) {
        assert patient != null : "Patient should not be null when generating success message";
        return String.format(MESSAGE_VIEW_SUCCESS, patient.getName());
    }

}
