package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliverySortComparator;

/**
 * Sorts and lists all deliveries in address book by the field.
 */
public class SortDeliveryCommand extends SortCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + " : Sorts all deliveries by cost, date time or status and "
            + "displays them as a list with index numbers.\n"
            + "Parameters:\n"
            + PREFIX_SORT_ORDER + "SORT ORDER ('a' for ascending and 'd' for descending)\n"
            + PREFIX_SORT_BY + "SORT BY ('s' for status, 'd' for datetime, 'c' for cost)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + PREFIX_SORT_ORDER + "a "
            + PREFIX_SORT_BY + "c";


    private final DeliverySortComparator comparator;

    public SortDeliveryCommand(DeliverySortComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedDeliveryList(comparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_DELIVERY_SORTED_OVERVIEW,
                        model.getSortedDeliveryList().size(),
                        comparator.toSortByString(),
                        comparator.toSortOrderString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortDeliveryCommand)) {
            return false;
        }

        SortDeliveryCommand otherSortDeliveryCommand = (SortDeliveryCommand) other;
        return comparator.equals(otherSortDeliveryCommand.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparator", comparator)
                .toString();
    }
}
