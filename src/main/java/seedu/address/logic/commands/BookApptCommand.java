package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the patient with the given NRIC.
 * Format: bookappt NRIC dt/YYYY-MM-DD HH:MM h/HEALTHSERVICE
 */
public class BookApptCommand extends Command {

    public static final String COMMAND_WORD = "bookappt";
    public static final String MESSAGE_APPT_ADDED_SUCCESS = "Appointment added successfully for %1$s\n"
            + "Input \"home\" to return to home page";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient not found";
    public static final String MESSAGE_PAST_DATETIME = "Appointment date and time cannot be in the past";
    public static final String MESSAGE_DUPLICATE_APPT = "Appointment already exists on this date and time";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records appointments under a specified health service "
            + " for registered patients\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";
    private static final Logger logger = Logger.getLogger(BookApptCommand.class.getName());

    private final Appt appt;
    private final Nric nric;

    /**
     * @param nric {@code Nric} of the patient
     * @param appt {@code Appt} of the patient
     */
    public BookApptCommand(Nric nric, Appt appt) {
        requireAllNonNull(appt, nric);
        assert nric != null : "NRIC cannot be null";
        assert appt != null : "Appointment cannot be null";
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
        assert model != null : "Model cannot be null";
        logger.info("Executing BookApptCommand");
        List<Patient> lastShownList = model.getFilteredPatientList();

        // Find the patient with the given name
        Optional<Patient> optionalPatient = lastShownList.stream()
            .filter(patient -> patient.getNric().equals(nric))
            .findFirst();

        if (optionalPatient.isEmpty()) {
            logger.warning(MESSAGE_PATIENT_NOT_FOUND);
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = optionalPatient.get();

        // Check for duplicate appointments
        boolean hasDuplicate = patient.getAppts().stream()
            .anyMatch(appt -> appt.equals(this.appt));

        if (hasDuplicate) {
            logger.warning("Duplicate appointment found");
            throw new CommandException(MESSAGE_DUPLICATE_APPT);
        }

        // Check for past date and time
        if (!this.appt.isAfterOrOn(LocalDateTime.now())) {
            logger.warning("Past date and time found");
            throw new CommandException(MESSAGE_PAST_DATETIME);
        }

        // Add the appointment to the patient's list of appointments
        patient.addAppt(this.appt);
        logger.info("Appointment added successfully for patient: " + patient.getName());

        return new ShowPatientInfoCommandResult(generateSuccessMessage(patient), patient, true);
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
        if (!(other instanceof BookApptCommand)) {
            return false;
        }

        BookApptCommand e = (BookApptCommand) other;
        return nric.equals(e.nric)
                && appt.equals(e.appt);
    }

    /**
     * Generates a command execution success message based on whether the remark is added or deleted.
     */
    private String generateSuccessMessage(Patient patient) {
        return String.format(MESSAGE_APPT_ADDED_SUCCESS, patient.getName());
    }
}
