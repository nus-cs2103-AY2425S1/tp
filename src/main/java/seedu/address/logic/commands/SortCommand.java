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
    public static final Integer ASCENDING = 1;
    public static final Integer DESCENDING = -1;

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

    private final Comparator<Person> sortingComparator;
    private final boolean isAscendingOrder;

    /**
     * Constructs a {@code SortCommand} with the given comparator and order.
     *
     * @param personComparator Comparator to sort persons.
     * @param isAscendingOrder {@code true} for ascending, {@code false} for descending.
     * @throws NullPointerException if {@code personComparator} is null.
     */
    private SortCommand(Comparator<Person> personComparator, boolean isAscendingOrder) {
        requireNonNull(personComparator);
        this.sortingComparator = personComparator;
        this.isAscendingOrder = isAscendingOrder;
    }

    /**
     * Returns a {@code SortCommand} to sort by name.
     *
     * @param order 1 for ascending, -1 for descending.
     */
    public static SortCommand sortByName(Integer order) {
        requireNonNull(order);
        boolean isAscendingOrder = (order.equals(ASCENDING));
        return new SortCommand(COMPARE_BY_NAME, isAscendingOrder);
    }

    /**
     * Returns a {@code SortCommand} to sort by student ID.
     *
     * @param order 1 for ascending, -1 for descending.
     */
    public static SortCommand sortByStudentId(Integer order) {
        requireNonNull(order);
        boolean isAscendingOrder = (order.equals(ASCENDING));
        return new SortCommand(COMPARE_BY_ID, isAscendingOrder);
    }

    /**
     * Returns a success message indicating the sorting field and order.
     *
     * @return A message describing the sorting field and order, or an empty string if unknown.
     */
    public String getMessageSuccess() {
        String order = isAscendingOrder ? "Ascending" : "Descending";
        if (this.sortingComparator == COMPARE_BY_NAME) {
            return "Sorted by Name in " + order + " order.";
        } else if (this.sortingComparator == COMPARE_BY_ID) {
            return "Sorted by Student Id in " + order + " order.";
        } else {
            // This line should never be reached if the logic is correct.
            assert false : "Unknown comparator used.";
            return "";
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        Comparator<Person> personComparator =
                isAscendingOrder ? this.sortingComparator : this.sortingComparator.reversed();
        model.updateSortedPersonList(personComparator);
        return new CommandResult(getMessageSuccess());
    }

    @Override
    public String toString() {
        String field = sortingComparator == COMPARE_BY_NAME ? "Name" : "Student ID";
        String order = isAscendingOrder ? "Ascending" : "Descending";
        return String.format("SortCommand[field=%s, order=%s]", field, order);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return this.sortingComparator == e.sortingComparator
                && this.isAscendingOrder == e.isAscendingOrder;
    }
}
