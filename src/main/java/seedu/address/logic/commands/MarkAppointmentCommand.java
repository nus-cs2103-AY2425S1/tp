package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
    public static final String COMMAND_WORD = "markAppointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": marks an appointment as complete"
            + "between the relevant doctor and patient. "
            + COMMAND_WORD + " "
            + PREFIX_DATE + "[APPOINTMENT_TIME] "
            + PREFIX_ID + "[PATIENT_ID] "
            + PREFIX_ID + "[DOCTOR_ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2024-12-31 15:23 g"
            + PREFIX_ID + "1234 "
            + PREFIX_ID + "5678";
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
