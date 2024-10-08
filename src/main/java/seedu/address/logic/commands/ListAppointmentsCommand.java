package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all upcoming appointments in the address book to the user.
 */
public class ListAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "list-appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all upcoming appointments in the address book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed %d upcoming appointments";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();

        LocalDateTime now = LocalDateTime.now();
        List<Person> upcomingAppointments = personList.stream()
                .filter(person -> person.hasUpcomingAppointment(now))
                .collect(Collectors.toList());

        return new CommandResult(String.format(MESSAGE_SUCCESS, upcomingAppointments.size()) + "\n" +
                formatAppointments(upcomingAppointments));
    }

    /**
     * Formats the list of persons with upcoming appointments into a string.
     *
     * @param appointments List of persons with upcoming appointments.
     * @return A formatted string containing the names and schedules of persons with upcoming appointments.
     */
    private String formatAppointments(List<Person> appointments) {
        return appointments.stream()
                .map(person -> String.format("%s: %s", person.getName(), person.getSchedule()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ListAppointmentsCommand;
    }

    @Override
    public int hashCode() {
        return ListAppointmentsCommand.class.hashCode();
    }
}