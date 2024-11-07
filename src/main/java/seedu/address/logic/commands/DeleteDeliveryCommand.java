package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

/**
 * Deletes a delivery identified using it's displayed index from the address book.
 */
public class DeleteDeliveryCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + ": Deletes the delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " 1";

    public static final String MESSAGE_DELETE_DELIVERY_SUCCESS = "Deleted Delivery: %1$s";

    private final Index targetIndex;

    public DeleteDeliveryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Ensure delivery list has items
        if (targetIndex.getZeroBased() >= model.getModifiedDeliveryList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }

        // Get the delivery at the specified index
        Delivery deliveryToDelete = model.getModifiedDeliveryList().get(targetIndex.getZeroBased());

        // Delete the delivery
        model.deleteDelivery(deliveryToDelete);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);

        // Return success message
        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS, Messages.format(deliveryToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteDeliveryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDeliveryCommand) other).targetIndex)); // state check
    }
}

