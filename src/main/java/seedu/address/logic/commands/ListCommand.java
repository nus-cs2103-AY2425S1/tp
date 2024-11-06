package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparators;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons in the address book.\n"
            + "Parameters: [sortOrder]\n"
            + "Sort order can be one of the following:\n"
            + "timeAdded asc, timeAdded desc, name asc, name desc\n"
            + "Note: If no sort order is specified, the default is by time added (timeAdded) and ascending (asc).\n"
            + "Example: " + COMMAND_WORD + " name asc";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    private final Comparator<Person> comparator;

    public ListCommand() {
        // Default comparator is BY_ORDER_ADDED_REVERSED
        this.comparator = PersonComparators.BY_ORDER_ADDED_REVERSED;
    }

    public ListCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
