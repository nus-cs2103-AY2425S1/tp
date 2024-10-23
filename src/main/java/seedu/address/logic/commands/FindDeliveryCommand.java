package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryDatePredicate;

/**
 * Finds and lists all deliveries in the address book that match the specified delivery date.
 */
public class FindDeliveryCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all deliveries with the specified date "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: -d on/<DELIVERY_DATE>\n"
            + "Example: " + COMMAND_WORD + " -d on/23-08-2024 20:21";

    private final DeliveryDatePredicate predicate;

    public FindDeliveryCommand(DeliveryDatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDeliveryList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW, model.getFilteredDeliveryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindDeliveryCommand)) {
            return false;
        }

        FindDeliveryCommand otherFindCommand = (FindDeliveryCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }
}
