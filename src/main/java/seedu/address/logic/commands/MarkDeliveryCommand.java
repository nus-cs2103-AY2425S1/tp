package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.List;

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
     * Creates a MarkDeliveryCommand to mark the specified delivery at {@code index} with new status {@code status}.
     *
     * @param index Index of delivery to be marked.
     * @param status Updated status to mark delivery with
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

        Delivery deliveryToMark = getDeliveryToMark(model);
        assert deliveryToMark != null;

        validateDeliveryStatus(deliveryToMark);

        Delivery markedDelivery = createDeliveryWithUpdatedStatus(deliveryToMark);

        model.setDelivery(deliveryToMark, markedDelivery);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(String.format(MESSAGE_MARK_DELIVERY_SUCCESS,
                Messages.formatWithoutStatus(deliveryToMark), status));
    }

    /**
     * Retrieves delivery to be marked with a new status.
     *
     * @param model Model which the command should operate on.
     * @return existing Delivery that needs to be marked.
     * @throws CommandException If index given exceeds the number of deliveries.
     */
    private Delivery getDeliveryToMark(Model model) throws CommandException {
        List<Delivery> deliveryList = model.getModifiedDeliveryList();
        assert deliveryList != null;

        if (index.getZeroBased() >= deliveryList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }
        return model.getModifiedDeliveryList().get(index.getZeroBased());
    }

    /**
     * Creates a new Delivery with the updated status.
     *
     * @param deliveryToMark Delivery to be marked with new status.
     * @return Delivery with updated status.
     */
    private Delivery createDeliveryWithUpdatedStatus(Delivery deliveryToMark) {
        assert deliveryToMark != null;
        return new Delivery(
                deliveryToMark.getDeliveryProduct(),
                deliveryToMark.getDeliverySender(),
                status, // updates new status here
                deliveryToMark.getDeliveryDate(),
                deliveryToMark.getDeliveryCost(),
                deliveryToMark.getDeliveryQuantity());
    }

    /**
     * Validates status input by user to ensure it is not the same as the delivery's current status.
     *
     * @param deliveryToMark Delivery to be marked with new status.
     * @throws CommandException If the status input by user matches the current status of the delivery
     */
    private void validateDeliveryStatus(Delivery deliveryToMark) throws CommandException {
        if (deliveryToMark.getDeliveryStatus().equals(status)) {
            throw new CommandException(String.format(Messages.MESSAGE_DELIVERY_ALREADY_HAS_STATUS,
                    Messages.formatWithoutStatus(deliveryToMark), status));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkDeliveryCommand // instanceof handles nulls
                && index.equals(((MarkDeliveryCommand) other).index)
                && status.equals(((MarkDeliveryCommand) other).status));
    }
}

