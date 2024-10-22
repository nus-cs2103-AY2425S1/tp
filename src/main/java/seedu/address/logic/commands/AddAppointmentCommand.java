package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
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
 * Adds an appointment to the person identified by the patient's NRIC number
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addapp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the person identified "
            + "By the patient's NRIC number"
            + "\nParameters: "
            + PREFIX_NRIC + "PATIENT_NRIC\n"
            + PREFIX_DATE + "DATE (DD/MM/YYYY) \n"
            + PREFIX_START_TIME + "START_TIME (HH:MM) \n"
            + PREFIX_END_TIME + "END_TIME (HH:MM) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_DATE + "01/01/2025 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "11:00";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "An appointment already exists at this date and time";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";

    public static final String MESSAGE_INVALID_DATE = "Invalid date. Please use the DD/MM/YYYY format";

    public static final String MESSAGE_INVALID_TIME = "Invalid time. Please use the HH:MM format";

    public static final String MESSAGE_INVALID_START_END_TIME = "Start time must be before end time";

    public static final String MESSAGE_START_TIME_IN_PAST = "Start time must be in the future";


    private final Nric patientNric;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Constructor for AddAppointmentCommand.
     * @param patientNric the NRIC of the person to add the appointment to
     * @param date the date of the appointment
     * @param startTime the start time of the appointment
     * @param endTime the end time of the appointment
     */
    public AddAppointmentCommand(Nric patientNric, LocalDate date, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(patientNric, date, startTime, endTime);
        this.patientNric = patientNric;
        this.startDateTime = LocalDateTime.of(date, startTime);
        this.endDateTime = LocalDateTime.of(date, endTime);
    }






    /**
     * Executes the command to add an appointment to the person with the given NRIC.
     * @param model the model to execute the command on
     * @return the command result
     * @throws CommandException if the command fails
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!startDateTime.isBefore(endDateTime)) {
            throw new CommandException(MESSAGE_INVALID_START_END_TIME);
        }

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new CommandException(MESSAGE_START_TIME_IN_PAST);
        }



        Person person = model.getPerson(patientNric);
        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        Appointment appointmentToAdd = new Appointment(person.getName().toString(), patientNric,
            startDateTime, endDateTime);


        boolean success = model.addAppointment(appointmentToAdd, person);
        if (success) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentToAdd));
        } else {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        // Check if it's the same object
        if (other == this) {
            return true;
        }

        // Check if the other object is an instance of AddAppointmentCommand
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        // Cast and check if the fields are equal
        AddAppointmentCommand otherCommand = (AddAppointmentCommand) other;
        return patientNric.equals(otherCommand.patientNric)
                && startDateTime.equals(otherCommand.startDateTime)
                && endDateTime.equals(otherCommand.endDateTime);
    }

    @Override
    public String toString() {
        return String.format("%s{toAdd=Appointment: %s, Date: %s, Start: %s, End: %s}",
                AddAppointmentCommand.class.getCanonicalName(),
                patientNric,
                startDateTime.toLocalDate(),
                startDateTime.toLocalTime(),
                endDateTime.toLocalTime());
    }






}
