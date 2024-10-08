package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the contact list based on the specified parameter and order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list by the specified parameter in the specified order.\n"
            + "Parameters: PARAMETER (name, insurance_type, address, renewal_date) ORDER (asc, desc)\n"
            + "Example: " + COMMAND_WORD + " name asc";

    public static final String MESSAGE_SUCCESS = "Contacts have been sorted by %1$s in %2$s order.";
    public static final String MESSAGE_INVALID_PARAMETER = "Invalid parameter. Use one of the following: "
            + "name, " + "insurance type, address, or renewal_date.";
    public static final String MESSAGE_INVALID_ORDER = "Invalid order. Use `asc` for ascending or "
            + "`desc` for descending.";

    private final String parameter;
    private final String order;

    /**
     * Creates a SortCommand to sort the contact list by the specified parameter and order.
     */
    public SortCommand(String parameter, String order) {
        requireNonNull(parameter);
        requireNonNull(order);
        this.parameter = parameter;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Comparator<Person> comparator;
        switch (parameter) {
        case "name":
            comparator = Person.getNameComparator();
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_PARAMETER);
        }

        if (order.equals("desc")) {
            comparator = comparator.reversed();
        } else if (!order.equals("asc")) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }

        model.sortPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter, order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return parameter.equals(otherSortCommand.parameter)
                && order.equals(otherSortCommand.order);
    }
}
