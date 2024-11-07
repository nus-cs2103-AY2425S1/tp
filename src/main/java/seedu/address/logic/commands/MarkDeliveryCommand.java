package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;

/**
 * Changes the status of a delivery identified using its displayed index from the address book.
 */
public class MarkDeliveryCommand extends MarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + ": Marks the status of the delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: INDEX (must be a positive integer) STATUS (PENDING, DELIVERED, CANCELLED)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " 1 DELIVERED";

    public static final String MESSAGE_MARK_DELIVERY_SUCCESS = "Marked Delivery: %1$s as %2$s";

    private final Index index;
    private final Status status;

    /**
     * Creates an MarkDeliveryCommand to mark the specified delivery at {@code index} with new status {@code status}
     */
    public MarkDeliveryCommand(Index index, Status status) {
        requireNonNull(index);
        requireNonNull(status);
        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (index.getZeroBased() >= model.getModifiedDeliveryList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }


        Delivery deliveryToMark = model.getModifiedDeliveryList().get(index.getZeroBased());

        if (deliveryToMark.getDeliveryStatus().equals(status)) {
            throw new CommandException(String.format(Messages.MESSAGE_DELIVERY_ALREADY_HAS_STATUS,
                    Messages.formatWithoutStatus(deliveryToMark), status));
        }

        Delivery markedDelivery = new Delivery(
                deliveryToMark.getDeliveryProduct(),
                deliveryToMark.getDeliverySender(),
                status, //updates new status here
                deliveryToMark.getDeliveryDate(),
                deliveryToMark.getDeliveryCost(),
                deliveryToMark.getDeliveryQuantity());

        model.setDelivery(deliveryToMark, markedDelivery);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(String.format(MESSAGE_MARK_DELIVERY_SUCCESS,
                Messages.formatWithoutStatus(deliveryToMark), status));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkDeliveryCommand // instanceof handles nulls
                && index.equals(((MarkDeliveryCommand) other).index)
                && status.equals(((MarkDeliveryCommand) other).status));
    }
}

