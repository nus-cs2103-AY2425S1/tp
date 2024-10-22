package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all Persons by date
 */
public class SortByDateCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Persons list has been sorted by date.";

    public static final String COMMAND_WORD = "sort_date";
    public static final String ALT_COMMAND_WORD = "sd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all Persons by date recent to distant "
            + "or distant to recent based on the paramater.\n"
            + "Parameters: recent/distant\n"
            + "Example: " + COMMAND_WORD + " recent";

    private final Comparator<Person> comparator;

    /**
     * @param comparator comparator to be used to sort persons
     */
    public SortByDateCommand(Comparator<Person> comparator) {
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
        if (!(o instanceof SortByDateCommand)) {
            return false;
        }

        SortByDateCommand e = (SortByDateCommand) o;
        return comparator.equals(e.comparator);
    }
}
