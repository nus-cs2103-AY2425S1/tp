package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Adds an appointment with a person in the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an appointment with a person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "yyyy-MM-dd "
            + PREFIX_FROM + "HH:mm "
            + PREFIX_TO + "HH:mm\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2024-11-15 "
            + PREFIX_FROM + "16:00 "
            + PREFIX_TO + "18:00";

    public static final String MESSAGE_SUCCESS = "New appointment with %s added:\n%s";

    private final Index index;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    private Appointment appointment;

    /**
     * Creates an AddAppointmentCommand to schedule an appointment with a person at the specified index.
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
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(index.getZeroBased());
        try {
            appointment = new Appointment(person.getName(), date, startTime, endTime);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        checkForConflictingAppointments(model);

        model.addAppointment(appointment);

        String feedback = String.format(MESSAGE_SUCCESS, person.getName(), Messages.format(appointment));
        return new CommandResult(feedback, true, false, false);
    }

    private void checkForConflictingAppointments(Model model) throws CommandException {
        List<Appointment> conflicts = model.getConflictingAppointments(appointment);
        if (conflicts.isEmpty()) {
            return;
        }

        throw new CommandException(
                String.format(Messages.MESSAGE_CONFLICTING_APPOINTMENTS, Messages.format(conflicts)));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        model.deleteAppointment(appointment);
        pastCommands.remove();
        return String.format(UndoCommand.MESSAGE_UNDO_ADD_APPOINTMENT, appointment.name(),
                Messages.format(appointment));
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("date", date)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}
