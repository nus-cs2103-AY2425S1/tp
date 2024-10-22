package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortCommandParser;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Sorts persons in SocialBook according to the specified field.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all persons in the address book according to the given parameter.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final String parameter;

    public SortCommand(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (parameter.equals(SortCommandParser.NAME)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getName().toString()));
        }

        if (parameter.equals(SortCommandParser.ADDRESS)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getAddress().toString()));
        }

        if (parameter.equals(SortCommandParser.PRIORITY)) {
            model.updateSortingOrder(Comparator.comparing(Person::getPriority));
        }

        if (parameter.equals(SortCommandParser.INCOME)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getIncome().getValue()));
        }

        if (parameter.equals(SortCommandParser.APPOINTMENT)) {
            // For appointments, put those without appointments to the back, and sort by earliest appointment first
            Comparator<Person> comparator = Comparator.comparing(Person::getAppointment,
                    Comparator.nullsLast(
                            Comparator.comparing(Appointment::date).thenComparing(Appointment::startTime)));
            model.updateSortingOrder(comparator);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
