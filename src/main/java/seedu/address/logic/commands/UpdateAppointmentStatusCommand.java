package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Status;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Updates the status of an appointment.
 */
public class UpdateAppointmentStatusCommand extends Command {

    public static final String COMMAND_WORD = "updatestatus";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the status of an appointment.\n"
        + "By the patient's NRIC number"
        + "\nParameters: "
        + PREFIX_NRIC + "PATIENT_NRIC\n"
        + PREFIX_DATE + "DATE (DD/MM/YYYY) \n"
        + PREFIX_START_TIME + "START_TIME (HH:MM) \n"
        + PREFIX_STATUS + "STATUS\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NRIC + "S1234567A "
        + PREFIX_DATE + "01/01/2021 "
        + PREFIX_START_TIME + "10:00 "
        + PREFIX_STATUS + "COMPLETED";

    public static final String MESSAGE_SUCCESS = "Appointment status updated: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";
    public static final String MESSAGE_INVALID_DATE = "Invalid date. Please use the DD/MM/YYYY format";
    public static final String MESSAGE_INVALID_TIME = "Invalid time. Please use the HH:MM format";
    public static final String MESSAGE_NO_APPOINTMENT = "This appointment does not exist in CareLink";

    private final Nric nric;
    private final LocalDateTime startDateTime;
    private final Status status;

    /**
     * Constructor for UpdateAppointmentStatusCommand.
     * @param nric the NRIC of the person to update the appointment status
     * @param date the date of the appointment
     * @param startTime the start time of the appointment
     * @param status the status of the appointment
     */
    public UpdateAppointmentStatusCommand(Nric nric, LocalDate date, LocalTime startTime, Status status) {
        this.nric = nric;
        this.startDateTime = LocalDateTime.of(date, startTime);
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        Person person = model.getPerson(nric);

        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Appointment appointment = model.getAppointmentForPersonAndTime(person, startDateTime);
        if (appointment == null) {
            throw new CommandException(MESSAGE_NO_APPOINTMENT);
        }
        model.updateAppointmentStatus(appointment, status);
        return new CommandResult(String.format(MESSAGE_SUCCESS, status));

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        UpdateAppointmentStatusCommand otherCommand = (UpdateAppointmentStatusCommand) other;
        return nric.equals(otherCommand.nric)
            && startDateTime.equals(otherCommand.startDateTime)
            && status.equals(otherCommand.status);
    }

}
