package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all Persons by preference
 */
public class SortCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Persons list has been sorted.";

    public static final String COMMAND_WORD = "sort";
    public static final String ALT_COMMAND_WORD = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all Persons "
            + "by priority High to Low or Low to High "
            + "or by last seen date Recent to Distant or Distant to Recent based on the paramater.\n"
            + "Parameters: high/low/recent/distant\n"
            + "Example: " + COMMAND_WORD + " high";

    private final Comparator<Person> comparator;

    /**
     * @param comparator comparator to be used to sort persons
     */
    public SortCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateSortedPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(o instanceof SortCommand)) {
            return false;
        }

        SortCommand e = (SortCommand) o;
        return comparator.equals(e.comparator);
    }
}
