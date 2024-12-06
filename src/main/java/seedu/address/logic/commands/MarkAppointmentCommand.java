package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MIXED_SEQUENCE_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_DOCTOR_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_PATIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * marks an appointment as complete.
 */
public class MarkAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment of a doctor "
            + "with a patient as complete. \n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_ID + "DOCTOR_ID "
            + PREFIX_DATE + "DATE_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678 "
            + PREFIX_DATE + "2024-12-31 15:23";
    public static final String MESSAGE_MARK_APPOINTMENT_SUCCESS = "Successfully "
            + "marked appointment as complete";
    private final int patientId;
    private final int doctorId;
    private final LocalDateTime appointmentTime;

    /**
     * Creates an MarkAppointmentCommand to mark an appointment as complete
     */
    public MarkAppointmentCommand(LocalDateTime appointmentTime, int patientId, int doctorId) {
        requireNonNull(appointmentTime);
        requireNonNull(patientId);
        requireNonNull(doctorId);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;

    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> allPersons = model.getAllPersons();
        Person patientToMarkAppointment = model.getFilteredPatientById(allPersons, patientId);
        Person doctorToMarkAppointment = model.getFilteredDoctorById(allPersons, doctorId);

        checkForExistenceOfPersons(patientToMarkAppointment, doctorToMarkAppointment);
        checkForValidIds(patientId, doctorId);

        patientToMarkAppointment.markAppointment(appointmentTime,
                patientToMarkAppointment.getId(),
                doctorToMarkAppointment.getId());

        doctorToMarkAppointment.markAppointment(appointmentTime, patientToMarkAppointment.getId(),
                doctorToMarkAppointment.getId());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_MARK_APPOINTMENT_SUCCESS);

    }

    /**
     * Checks for the existence of patient and doctor that the user enters
     */
    public void checkForExistenceOfPersons(Person patient, Person doctor) throws CommandException {
        if (doctor == null) {
            throw new CommandException(MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }
        if (patient == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks that the user enters a patient's ID then a doctor ID
     */
    public void checkForValidIds(int patientId, int doctorId) throws CommandException {
        if (patientId % 2 == 0 && doctorId % 2 == 0) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENT_ID);
        } else if (patientId % 2 != 0 && doctorId % 2 != 0) {
            throw new CommandException(MESSAGE_MULTIPLE_DOCTOR_ID);
        } else if (patientId % 2 != 0) {
            throw new CommandException(MESSAGE_MIXED_SEQUENCE_ID);
        }
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAppointmentCommand)) {
            return false;
        }

        // State check
        MarkAppointmentCommand otherCommand = (MarkAppointmentCommand) other;
        return appointmentTime.equals(otherCommand.appointmentTime)
                && patientId == otherCommand.patientId
                && doctorId == otherCommand.doctorId;
    }
}
