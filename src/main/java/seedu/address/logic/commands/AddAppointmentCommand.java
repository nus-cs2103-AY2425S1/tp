package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Schedules an appointment with a person in SocialBook.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "adda";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an appointment with a person in SocialBook.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "yyyy-MM-dd "
            + PREFIX_FROM + "HH:mm "
            + PREFIX_TO + "HH:mm\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2024-11-15 "
            + PREFIX_FROM + "16:00 "
            + PREFIX_TO + "18:00";

    public static final String MESSAGE_SUCCESS = "New appointment added:\n%s";
    public static final String MESSAGE_INVALID_APPOINTMENT_TIME = "Appointment start time must be before end time";
    public static final String MESSAGE_INVALID_APPOINTMENT_DATE = "Can't schedule appointments in the past";
    public static final String MESSAGE_CONFLICTING_APPOINTMENTS = "Conflicting appointments found:\n%s";

    private final Index index;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Creates an AddAppointmentCommand to schedule an appointment with a family at the specified index.
     */
    public AddAppointmentCommand(Index index, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.index = index;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEXES);
        }

        if (!startTime.isBefore(endTime)) { // start time >= end time
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_TIME);
        }

        if (date.isBefore(LocalDate.now()) // date < current date
                || date.equals(LocalDate.now()) // date == current date
                && !startTime.isAfter(LocalTime.now())) { // start time <= current time
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DATE);
        }

        Person person = lastShownList.get(index.getZeroBased());
        Appointment appointment = new Appointment(person.getName(), date, startTime, endTime);

        checkForConflictingAppointments(model, appointment);

        model.addAppointment(appointment);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment), true, false, false);
    }

    private void checkForConflictingAppointments(Model model, Appointment appointment) throws CommandException {
        List<Appointment> conflicts = model.getConflictingAppointments(appointment);
        if (conflicts.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Appointment conflict : conflicts) {
            sb.append(String.format("Name: %s; Date: %s; From: %s; To: %s\n",
                    conflict.name(), conflict.date(), conflict.startTime(), conflict.endTime()));
        }

        throw new CommandException(String.format(MESSAGE_CONFLICTING_APPOINTMENTS, sb));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand otherAddAppointmentCommand)) {
            return false;
        }

        return index.equals(otherAddAppointmentCommand.index)
                && date.equals(otherAddAppointmentCommand.date)
                && startTime.equals(otherAddAppointmentCommand.startTime)
                && endTime.equals(otherAddAppointmentCommand.endTime);
    }
}
