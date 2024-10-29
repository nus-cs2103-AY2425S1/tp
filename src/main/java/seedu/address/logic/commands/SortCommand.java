package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts persons in the address book based on the given keyword.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts persons in the address book by the given field "
            + "and order. Supported fields: deadline, name. Supported orders: ascending, descending\n"
            + "Example: " + COMMAND_WORD + " deadline ascending OR " + COMMAND_WORD + " name descending";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by %1$s in %2$s order";
    public static final String MESSAGE_NO_PERSONS = "No persons available to sort.";
    public static final String MESSAGE_INVALID_KEYWORD = "Invalid sort keyword! Supported keywords: deadline, name.";
    public static final String MESSAGE_INVALID_ORDER = "Invalid sort order! Supported orders: ascending, descending";

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

    private final String sortByKeyword;
    private final boolean isAscending;


    /**
     * @param sortByKeyword The field to sort by (e.g., "deadline" or "name").
     * @param isAscending Indicates whether the sorting should be in ascending order.
     */
    public SortCommand(String sortByKeyword, boolean isAscending) {
        this.sortByKeyword = sortByKeyword.trim().toLowerCase();
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getAddressBook().getPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_PERSONS);
        }

        Comparator<Person> comparator;
        switch (sortByKeyword) {
        case "deadline":
            comparator = Comparator.comparing(person -> person.getDeadline().value);
            break;
        case "name":
            comparator = Comparator.comparing(person -> person.getName().fullName.toLowerCase());
            break;
        default:
            logger.finer("This keyword caused a CommandException: " + sortByKeyword);
            throw new CommandException(MESSAGE_INVALID_KEYWORD);
        }

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        model.sortByComparator(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortByKeyword,
                isAscending ? "ascending" : "descending"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return sortByKeyword.equals(otherCommand.sortByKeyword) && isAscending == otherCommand.isAscending;
    }

    @Override
    public int hashCode() {
        return sortByKeyword.hashCode() * 31 + Boolean.hashCode(isAscending);
    }
}
