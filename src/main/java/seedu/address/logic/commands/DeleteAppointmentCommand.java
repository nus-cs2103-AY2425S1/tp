package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Deletes an appointment identified using it's displayed name from the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified appointment.\n"
            + "Parameters: NAME d/yyyy-MM-dd HHmm (name must of an existing client and should not be blank)\n"
            + "Example: " + COMMAND_WORD + " John Doe d/2024-10-01 1200";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted appointment for %1$s";

    private Name name;
    private Schedule appointment;

    /**
     * Creates a {@code DeleteAppointmentCommand} with the specified name and appointment
     */
    public DeleteAppointmentCommand(Name name, Schedule appointment) {
        this.name = name;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int index = -1;
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME_DISPLAYED);
        }

        Person personWithAppointmentToDelete = lastShownList.get(index);
        Set<Schedule> oldSchedule = personWithAppointmentToDelete.getSchedules();

        Schedule appointmentToDelete = new Schedule("", "");
        for (Schedule schedule : personWithAppointmentToDelete.getSchedules()) {
            if (schedule.equals(appointment)) {
                appointmentToDelete = appointment;
                break;
            }
        }

        if (appointmentToDelete.getDateTime().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED);
        }

        Set<Schedule> updatedSchedules = oldSchedule.stream()
                .filter(s -> !(s.getDateTime().equals(appointment.getDateTime())))
                .collect(Collectors.toCollection(HashSet::new));

        Person updatedPerson = new Person(personWithAppointmentToDelete.getName(),
                personWithAppointmentToDelete.getPhone(),
                personWithAppointmentToDelete.getEmail(),
                personWithAppointmentToDelete.getAddress(),
                updatedSchedules,
                personWithAppointmentToDelete.getReminder(),
                personWithAppointmentToDelete.getTags());

        // if the person has no scheduled appointments and a reminder set, delete the reminder
        if (updatedPerson.getSchedules().isEmpty()
                && updatedPerson.getReminder() != null) {
            updatedPerson.removeReminder();
        }

        model.setPerson(personWithAppointmentToDelete, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.formatSchedule(personWithAppointmentToDelete, appointment)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteAppointmentCommand = (DeleteAppointmentCommand) other;
        return appointment.equals(otherDeleteAppointmentCommand.appointment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDeleteAppointment", name)
                .toString();
    }
}
