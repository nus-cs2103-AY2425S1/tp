package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes an appointment from the person identified by the patient's NRIC number
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteapp";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment from the person identified "
            + "By the patient's NRIC number"
            + "\nParameters: "
            + PREFIX_NRIC + "PATIENT_NRIC\n"
            + PREFIX_DATE + "DATE (DD/MM/YYYY) \n"
            + PREFIX_START_TIME + "START_TIME (HH:MM) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_DATE + "01/01/2025 "
            + PREFIX_START_TIME + "10:00";
    public static final String MESSAGE_SUCCESS = "Deleted appointment: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";
    public static final String MESSAGE_INVALID_DATE = "Invalid date. Please use the DD/MM/YYYY format";
    public static final String MESSAGE_INVALID_TIME = "Invalid time. Please use the HH:MM format";
    public static final String MESSAGE_NO_APPOINTMENT = "This appointment does not exist in CareLink";
    private final Nric patientNric;
    private final LocalDateTime startDateTime;

    /**
     * Constructs a DeleteAppointmentCommand object
     * @param patientNric
     * @param date
     * @param startTime
     */
    public DeleteAppointmentCommand(Nric patientNric, LocalDate date, LocalTime startTime) {
        requireAllNonNull(patientNric, date, startTime);
        this.patientNric = patientNric;
        this.startDateTime = LocalDateTime.of(date, startTime);
    }

    /**
     * Executes the command to delete an appointment for the person with the given NRIC.
     * @param model the model to execute the command on
     * @return the command result
     * @throws CommandException if the person is not found or the appointment does not exist
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        Person person = model.getPerson(patientNric);

        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Appointment appointment = model.getAppointmentForPersonAndTime(person, startDateTime);

        if (appointment == null) {
            throw new CommandException(MESSAGE_NO_APPOINTMENT);
        }

        model.removeAppointment(appointment, person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    @Override
    public boolean equals(Object other) {
        // Check if it's the same object
        if (other == this) {
            return true;
        }

        // Check if the other object is an instance of DeleteAppointmentCommand
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        // Cast and check if the fields are equal
        DeleteAppointmentCommand otherCommand = (DeleteAppointmentCommand) other;
        return patientNric.equals(otherCommand.patientNric)
                && startDateTime.equals(otherCommand.startDateTime);
    }

}
