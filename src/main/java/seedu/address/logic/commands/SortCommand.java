package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_WRONG_NUM_OF_FIELDS = "Exactly 1 field should be provided for sorting.";
    public static final String MESSAGE_NOT_SORTED = "The field to sort by must be provided.";
    public static final String MESSAGE_SUCCESS = "Sorted based on order.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts according to 1 specified field and order. "
            + "Parameters: "
            + "[" + PREFIX_NAME + "] / "
            + "[" + PREFIX_STUDENT_ID + "] \n"
            + "ORDER (must be 1 (ascending) or -1 (descending)) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " 1";

    private final Comparator<Person> sortingOrder;

    public SortCommand(Comparator<Person> personComparator, Integer order) {
        requireNonNull(personComparator);
        requireNonNull(order);

        if (order == -1) {
            this.sortingOrder = personComparator.reversed();
        } else {
            this.sortingOrder = personComparator;
        }
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateSortedPersonList(sortingOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}