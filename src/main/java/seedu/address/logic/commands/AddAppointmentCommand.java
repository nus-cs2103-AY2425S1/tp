package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MIXED_SEQUENCE_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_DOCTOR_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to both a patient and a doctor.
 */

public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment for "
            + "the relevant doctor and patient. \n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_ID + "DOCTOR_ID "
            + PREFIX_DATE + "DATE_TIME "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678 "
            + PREFIX_DATE + "2024-12-31 15:23 "
            + PREFIX_REMARK + "third physiotherapy session";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Successfully added appointment to a patient";
    public static final String MESSAGE_UNAVAILABLE_SLOT = "The patient or doctor already has another appointment!";
    private static final Logger logger = Logger.getLogger(AddAppointmentCommand.class.getName());
    private final int patientId;
    private final int doctorId;
    private final LocalDateTime appointmentTime;
    private final String remarks;

    /**
     * Creates an AddAppointmentCommand to add the specified patient and doctor ids
     */
    public AddAppointmentCommand(LocalDateTime appointmentTime, int patientId, int doctorId, String remarks) {
        requireNonNull(appointmentTime);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.remarks = remarks;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing AddAppointmentCommand...");

        requireNonNull(model);
        ObservableList<Person> allPersons = model.getAllPersons();

        Person patientToAddAppointment = model.getFilteredPatientById(allPersons, patientId);
        Person doctorToAddAppointment = model.getFilteredDoctorById(allPersons, doctorId);

        checkForPersonsExistence(patientToAddAppointment, doctorToAddAppointment);
        checkForInputId(patientId, doctorId);
        checkForAvailability(patientToAddAppointment, doctorToAddAppointment);

        logger.info(String.format("Added appointment for patient ID %d and doctor ID %d at time %s with remarks: %s",
                patientId, doctorId, appointmentTime.toString(), remarks));

        return new CommandResult(MESSAGE_ADD_APPOINTMENT_SUCCESS);
    }

    /**
     * Checks for the existence of patient and doctor that the user enters
     */
    public void checkForPersonsExistence(Person patient, Person doctor) throws CommandException {
        if (doctor == null) {
            logger.warning(String.format("Doctor with ID %d not found.", doctorId));
            throw new CommandException(MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }

        if (patient == null) {
            logger.warning(String.format("Patient with ID %d not found.", patientId));
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks that the user enters a patient's ID then a doctor ID
     */
    public void checkForInputId(int patientId, int doctorId) throws CommandException {
        if (patientId % 2 == 0 && doctorId % 2 == 0) {
            logger.warning("The user enter two patientId");
            throw new CommandException(MESSAGE_MULTIPLE_PATIENT_ID);
        } else if (patientId % 2 != 0 && doctorId % 2 != 0) {
            logger.warning("The user enter two doctorId");
            throw new CommandException(MESSAGE_MULTIPLE_DOCTOR_ID);
        } else if (patientId % 2 != 0) {
            logger.warning("The user enters doctorId and patientId in wrong sequence");
            throw new CommandException(MESSAGE_MIXED_SEQUENCE_ID);
        }
    }

    /**
     * Checks for the availability of both patient and doctor
     */
    public void checkForAvailability(Person patient, Person doctor) throws CommandException {
        boolean isPatientFree = patient.addAppointment(appointmentTime, patient.getId(),
                doctor.getId(), remarks);
        boolean isDoctorFree = doctor.addAppointment(appointmentTime, patient.getId(),
                doctor.getId(), remarks);

        if (!isPatientFree || !isDoctorFree) {
            throw new CommandException(MESSAGE_UNAVAILABLE_SLOT);
        }
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        // State check
        AddAppointmentCommand otherCommand = (AddAppointmentCommand) other;
        return appointmentTime.equals(otherCommand.appointmentTime)
                && patientId == otherCommand.patientId
                && doctorId == otherCommand.doctorId
                && remarks.equals(otherCommand.remarks);
    }
}
