package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Schedules an appointment with a person in the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules an appointment with a person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "yyyy-MM-dd "
            + PREFIX_FROM + "HH:mm "
            + PREFIX_TO + "HH:mm\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2024-11-15 "
            + PREFIX_FROM + "16:00 "
            + PREFIX_TO + "18:00";

    public static final String MESSAGE_SUCCESS = "Appointment scheduled with %s on %s from %s to %s.";
    public static final String MESSAGE_INVALID_APPOINTMENT_TIME = "Appointment start time must be before end time";
    public static final String MESSAGE_INVALID_APPOINTMENT_DATE = "Appointment must be scheduled in the future";
    public static final String MESSAGE_CONFLICTING_APPOINTMENTS = "Conflicting appointments found:\n%s";

    private final Index index;
    private final Appointment appointment;

    /**
     * Creates a ScheduleCommand to schedule an appointment with a person at the specified index.
     */
    public ScheduleCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!appointment.startTime().isBefore(appointment.endTime())) { // start time >= end time
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_TIME);
        }

        if (appointment.date().isBefore(LocalDate.now()) // date < current date
                || appointment.date().equals(LocalDate.now()) // date == current date
                && !appointment.startTime().isAfter(LocalTime.now())) { // start time <= current time
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DATE);
        }

        Person person = lastShownList.get(index.getZeroBased());
        Person updatedPerson = person.withAppointment(appointment);

        StringBuilder sb = new StringBuilder();
        for (Pair<Name, Appointment> pair : model.getConflictingAppointments(person, appointment)) {
            Appointment conflictingAppointment = pair.getValue();
            sb.append(String.format("Name: %s; Date: %s; From: %s; To: %s\n",
                    pair.getKey(),
                    conflictingAppointment.date(),
                    conflictingAppointment.startTime(),
                    conflictingAppointment.endTime()));
        }

        if (!sb.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_CONFLICTING_APPOINTMENTS, sb));
        }

        model.setPerson(person, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, person.getName(),
                appointment.getFormattedDate(),
                appointment.getFormattedStartTime(),
                appointment.getFormattedEndTime()));
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
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return index.equals(otherScheduleCommand.index)
                && appointment.equals(otherScheduleCommand.appointment);
    }
}
