package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ASCENDING;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

import java.util.Comparator;

/**
 * Sorts and lists all deliveries in address book by the specified prefix.
 */
public class SortDeliveryCommand extends SortCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + ": Sorts all deliveries by any of the prefix and "
            + "displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + PREFIX_SORT_ASCENDING + "SORT ORDER ('a' for ascending and 'd' for descending)";

    private final Comparator<Delivery> comparator;

    public SortDeliveryCommand(Comparator<Delivery> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedDeliveryList(comparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_DELIVERY_SORTED_OVERVIEW, model.getSortedDeliveryList().size()));
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
