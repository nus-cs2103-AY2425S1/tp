package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by a specified field and order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_WRONG_NUM_OF_FIELDS = "Exactly 1 field should be provided for sorting.";
    public static final String MESSAGE_NOT_SORTED = "The field to sort by must be provided.";

    public static final Comparator<Person> COMPARE_BY_NAME =
            Comparator.comparing(person -> person.getName().toString());
    public static final Comparator<Person> COMPARE_BY_ID =
            Comparator.comparing(person -> person.getStudentId().toString());

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts according to 1 specified field and order. \n"
            + "Parameters: "
            + "ORDER (must be 1 (ascending) or -1 (descending)) "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_STUDENT_ID + "] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME;

    private final Comparator<Person> sortingOrder;
    private final boolean isAscendingOrder;

    /**
     * Creates a SortCommand with the specified comparator and order.
     * Reverses the comparator if the order is -1 (descending).
     *
     * @param personComparator The comparator to sort persons by.
     * @param order The sorting order, 1 for ascending, -1 for descending.
     * @throws NullPointerException if {@code personComparator} or {@code order} is null.
     */
    public SortCommand(Comparator<Person> personComparator, Integer order) {
        requireNonNull(personComparator);
        requireNonNull(order);

        if (order == -1) {
            this.sortingOrder = personComparator.reversed();
            this.isAscendingOrder = false;
        } else {
            this.sortingOrder = personComparator;
            this.isAscendingOrder = true;
        }
    }

    /**
     * Returns a success message indicating the sorting field and order.
     *
     * @return A message describing the sorting field and order, or an empty string if unknown.
     */
    public String getMessageSuccess() {
        String order = isAscendingOrder ? "Ascending" : "Descending";
        Comparator<Person> personComparator = isAscendingOrder ? this.sortingOrder : this.sortingOrder.reversed();
        if (personComparator.equals(COMPARE_BY_NAME)) {
            return "Sorted by Name in " + order + " order.";
        } else if (personComparator.equals(COMPARE_BY_ID)) {
            return "Sorted by Student Id in " + order + " order.";
        } else {
            return "";
        }
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateSortedPersonList(sortingOrder);
        return new CommandResult(getMessageSuccess());
    }
}
