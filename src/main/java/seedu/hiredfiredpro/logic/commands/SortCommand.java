package seedu.hiredfiredpro.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Sorts the person list and lists persons in ascending or descending order based on interview scores.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the candidate list based on interview scores "
            + "in ascending or descending order.\n"
            + "Parameters: [a/d] (a for ascending, d for descending)\n"
            + "Example: " + COMMAND_WORD + " a";

    public static final String MESSAGE_SORT_PERSON_SUCCESS = "Sorted based on interview scores, in %s order.";
    public static final String MESSAGE_UNKNOWN_ORDER = "Order %s is not valid for this command. "
            + "Valid orders are 'a' for ascending or 'd' for descending.";

    public static final String ASCENDING_SHORT = "a";
    public static final String DESCENDING_SHORT = "d";
    public static final String ASCENDING_WORD = "ascending";
    public static final String DESCENDING_WORD = "descending";

    private final String order;
    private final Comparator<Person> comparator;

    /**
     * Creates a SortCommand to sort the person list based on interview scores.
     * @param order the sorting order ('a' for ascending, 'd' for descending)
     */
    public SortCommand(String order) {
        this.order = order;
        this.comparator = createComparator(order);
    }

    /**
     * Creates the appropriate comparator based on the sort order.
     */
    private Comparator<Person> createComparator(String order) {
        Comparator<Person> baseComparator = (
                p1, p2) -> Float.compare(p1.getInterviewScore().toFloat(), p2.getInterviewScore().toFloat());
        return order.equals(ASCENDING_SHORT) ? baseComparator : baseComparator.reversed();
    }

    /**
     * Gets the full word representation of the sort order.
     */
    private String getOrderWord(String order) {
        return order.equals(ASCENDING_SHORT)
                ? ASCENDING_WORD
                : order.equals(DESCENDING_SHORT)
                ? DESCENDING_WORD
                : null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String orderWord = getOrderWord(order);
        if (orderWord == null) {
            throw new CommandException(String.format(MESSAGE_UNKNOWN_ORDER, order));
        }

        model.updateSortedPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SORT_PERSON_SUCCESS, orderWord));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && order.equals(((SortCommand) other).order)
                && comparator.equals(((SortCommand) other).comparator));
    }
}
