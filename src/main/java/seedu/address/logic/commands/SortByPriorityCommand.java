package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityHighToLowComparator;

/**
 * Sorts all Persons by priority
 */
public class SortByPriorityCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Persons list has been sorted by priority.";

    public static final String COMMAND_WORD = "sort_priority";
    public static final String ALT_COMMAND_WORD = "sp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all Persons by priority High to Low "
            + "or Low To High based on the paramater.\n"
            + "Parameters: high/low\n"
            + "Example: " + COMMAND_WORD + " high";

    private final Comparator<Person> comparator;

    /**
     *
     * @param comparator comparator to be used to sort persons
     */
    public SortByPriorityCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateSortedPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
