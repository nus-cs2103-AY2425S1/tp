package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * deletes an appointment to both a patient and a doctor.
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "deleteA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": deletes an appointment "
            + "between the relevant doctor and patient.\n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_ID + "DOCTOR_ID "
            + PREFIX_DATE + "DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678 "
            + PREFIX_DATE + "2024-12-31 15:23";
    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Successfully "
            + "deleted appointment to a patient";
    public static final String MESSAGE_DELETE_APPOINTMENT_FAIL = "The appointment doesn't exist! "
            + "Please check again the details you have entered!";
    private static final Logger logger = Logger.getLogger(DeleteAppointmentCommand.class.getName());
    private final int patientId;
    private final int doctorId;
    private final LocalDateTime appointmentTime;

    /**
     * Creates an DeleteAppointmentCommand to add the specified patient and doctor ids
     */
    public DeleteAppointmentCommand(LocalDateTime appointmentTime, int patientId, int doctorId) {
        requireNonNull(appointmentTime);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> allPersons = model.getAllPersons();
        Person patientToAddAppointment = model.getFilteredPatientById(allPersons, patientId);
        Person doctorToAddAppointment = model.getFilteredDoctorById(allPersons, doctorId);

        if (doctorToAddAppointment == null) {
            logger.warning(String.format("Doctor with ID %d not found.", doctorId));
            throw new CommandException(MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }
        if (patientToAddAppointment == null) {
            logger.warning(String.format("Patient with ID %d not found.", patientId));
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
        boolean isDeleteSuccessful = patientToAddAppointment.deleteAppointment(appointmentTime,
                patientToAddAppointment.getId(),
                doctorToAddAppointment.getId());
        if (!isDeleteSuccessful) {
            throw new CommandException(MESSAGE_DELETE_APPOINTMENT_FAIL);
        }
        doctorToAddAppointment.deleteAppointment(appointmentTime, patientToAddAppointment.getId(),
                doctorToAddAppointment.getId());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_DELETE_APPOINTMENT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        // State check
        DeleteAppointmentCommand otherCommand = (DeleteAppointmentCommand) other;
        return appointmentTime.equals(otherCommand.appointmentTime)
                && patientId == otherCommand.patientId
                && doctorId == otherCommand.doctorId;
    }
}
