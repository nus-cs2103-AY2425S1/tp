package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_COMPLETED_APPOINTMENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_MIXED_SEQUENCE_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_DOCTOR_ID;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_PATIENT_ID;
import static seedu.address.logic.commands.CheckAppointmentCommand.MESSAGE_NO_APPOINTMENT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

/**
 * marks an appointment as complete.
 */
public class MarkAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment of a doctor "
            + "with a patient as complete "
            + COMMAND_WORD + " "
            + PREFIX_ID + "[PATIENT_ID] "
            + PREFIX_ID + "[DOCTOR_ID] "
            + PREFIX_DATE + "[APPOINTMENT_TIME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678 "
            + PREFIX_DATE + "2024-12-31 15:23";
    public static final String MESSAGE_MARK_APPOINTMENT_SUCCESS = "Successfully "
            + "marked appointment as complete";
    public static final String MESSAGE_MARK_APPOINTMENT_FAIL = "The appointment doesn't exist!";
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
        ObservableList<Person> allPersons = model.getFilteredPersonList();
        Person patientToMarkAppointment = model.getFilteredPatientById(allPersons, patientId);
        Person doctorToMarkAppointment = model.getFilteredDoctorById(allPersons, doctorId);
        if (doctorToMarkAppointment == null) {
            throw new CommandException(MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }
        if (patientToMarkAppointment == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        if (patientId % 2 == 0 && doctorId % 2 == 0) {
            throw new CommandException(MESSAGE_MULTIPLE_PATIENT_ID);
        } else if (patientId % 2 != 0 && doctorId % 2 != 0) {
            throw new CommandException(MESSAGE_MULTIPLE_DOCTOR_ID);
        } else if (patientId % 2 != 0 || doctorId % 2 == 0) {
            throw new CommandException(MESSAGE_MIXED_SEQUENCE_ID);
        }
        Appointment appointment = patientToMarkAppointment.getAppointment(appointmentTime,
                patientId, doctorId);

        if (appointment == null) {
            throw new CommandException(String.format(MESSAGE_NO_APPOINTMENT_FOUND, doctorToMarkAppointment.getName()));
        }

        if (appointment.isCompleted()) {
            throw new CommandException(MESSAGE_COMPLETED_APPOINTMENT);
        }

        boolean isMarkSuccessful = patientToMarkAppointment.markAppointment(appointmentTime,
                patientToMarkAppointment.getId(),
                doctorToMarkAppointment.getId());
        if (!isMarkSuccessful) {
            throw new CommandException(MESSAGE_MARK_APPOINTMENT_FAIL);
        }
        doctorToMarkAppointment.markAppointment(appointmentTime, patientToMarkAppointment.getId(),
                doctorToMarkAppointment.getId());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_MARK_APPOINTMENT_SUCCESS);

    }

}
