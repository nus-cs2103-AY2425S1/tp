package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the patient with the given NRIC.
 * Format: appt dt/YYYY-MM-DDTHH:MM h/HEALTHSERVICE i/NRIC
 */
public class ApptCommand extends Command {

    public static final String MESSAGE_ARGUMENTS = "Appt: %2$s, Healthservice: %2$s, Nric: %1$s";
    public static final String COMMAND_WORD = "appt";
    public static final String MESSAGE_APPT_ADDED_SUCCESS = "Appointment added successfully";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient not found";
    public static final String MESSAGE_DUPLICATE_APPT = "Appointment already exists on this date";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records appointment times for registered patients "
            + "into the system\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";

    private final Appt appt;
    private final Nric nric;

    /**
     * @param dateTime of the appointment
     * @param healthService of the appointment
     * @param nric of the patient
     */
    public ApptCommand(Appt appt, Nric nric) {
        requireAllNonNull(appt, nric);
        this.appt = appt;
        this.nric = nric;
    }

    /**
     * Executes the command and returns the result message.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        // Find the patient with the given name
        Optional<Patient> optionalPatient = lastShownList.stream()
            .filter(patient -> patient.getNric().equals(nric))
            .findFirst();

        if (!optionalPatient.isPresent()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();

        // Check for duplicate appointments
        boolean hasDuplicate = patient.getAppts().stream()
            .anyMatch(appt -> appt.equals(this.appt));

        if (hasDuplicate) {
            throw new CommandException(MESSAGE_DUPLICATE_APPT);
        }

        // Add the appointment to the patient's list of appointments
        patient.addAppt(this.appt);

        return new CommandResult(generateSuccessMessage(patient));
    }

    /**
     * Returns true if both appt commands have the same nric and dateTime.
     * This defines a stronger notion of equality between two appt commands.
     * @param other appt command
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApptCommand)) {
            return false;
        }

        ApptCommand e = (ApptCommand) other;
        return appt.equals(e.appt)
                && nric.equals(e.nric);
    }

    /**
     * Generates a command execution success message based on whether the remark is added or deleted.
     */
    private String generateSuccessMessage(Patient patient) {
        return String.format(MESSAGE_APPT_ADDED_SUCCESS, patient.getName());
    }
}
