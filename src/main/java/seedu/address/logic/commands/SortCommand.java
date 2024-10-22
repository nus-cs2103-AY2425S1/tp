package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
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
            + "Parameters: PARAMETER (" + PREFIX_NAME + ", " + PREFIX_APPOINTMENT + ", " + PREFIX_BIRTHDAY + " or "
            + PREFIX_NEXT_PAYMENT_DATE + ") " + "ORDER (asc, desc)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " asc";

    public static final String MESSAGE_SUCCESS = "Contacts have been sorted by %1$s in %2$s order.";
    public static final String MESSAGE_INVALID_PARAMETER = "Invalid parameter. Use one of the following: "
        + PREFIX_NAME + ", " + PREFIX_APPOINTMENT + ", " + PREFIX_BIRTHDAY + ", or " + PREFIX_NEXT_PAYMENT_DATE + ".";
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
        case "n/":
            comparator = Person.getNameComparator();
            break;
        case "appt/":
            comparator = Person.getAppointmentDateComparator();
            break;
        case "b/":
            comparator = Person.getBirthdayComparator();
            break;
        case "paydate/":
            comparator = Person.getPayDateComparator();
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_PARAMETER);
        }

        if (parameter.equals("paydate/") && order.equals("desc")) {
            comparator = Person.getReversedPayDateComparator();
        }

        if (order.equals("desc")) {
            comparator = comparator.reversed();
        } else if (!order.equals("asc")) {
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }

        model.sortPersonList(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, getExactParameter(parameter), order));
    }

    /**
     * Returns the exact parameter name.
     */
    private String getExactParameter(String prefix) {
        switch (prefix) {
        case "n/":
            return "name";
        case "appt/":
            return "appointment date";
        case "b/":
            return "birthday";
        case "paydate/":
            return "next payment date";
        default:
            return prefix;
        }
    }

    /**
     * Returns true if the parameter is a valid parameter.
     */
    public static boolean isValidParameter(String parameter) {
        return parameter.equals("n/") || parameter.equals("appt/") || parameter.equals("b/")
                || parameter.equals("paydate/");
    }

    /**
     * Returns true if the order is a valid order.
     */
    public static boolean isValidOrder(String order) {
        return order.equals("asc") || order.equals("desc");
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("parameter", parameter)
                .add("order", order)
                .toString();
    }
}
