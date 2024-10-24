package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons with an appointment in the address book to the user and sorts them by appointment time.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_SUCCESS = "Schedule displayed, starting from earliest to latest appointment.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(new PredicateHasAppointment());
        model.updateSortedPersonList(new ComparatorAppointment());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Tests that a {@code Person}'s appointment is not null.
     */
    private static class PredicateHasAppointment implements Predicate<Person> {

        @Override
        public boolean test(Person person) {
            return person.getAppointment() != null;
        }
    }

    /**
     * Compares two {@code Person}'s appointments for ordering.
     */
    static class ComparatorAppointment implements Comparator<Person> {

        @Override
        public int compare(Person p1, Person p2) {
            if (p1.getAppointment() == null || p2.getAppointment() == null) {
                return 0;
            } else {
                // Compare appointment times
                return p1.getAppointment().value.compareTo(p2.getAppointment().value);
            }
        }
    }
}
