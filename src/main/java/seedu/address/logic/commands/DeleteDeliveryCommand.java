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

    /**
     * Creates a DeleteDeliveryCommand to delete the specified delivery at {@code targetTndex}.
     *
     * @param targetIndex Index of delivery to be deleted.
     */
    public DeleteDeliveryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Delivery deliveryToDelete = getDeliveryToDelete(model);
        assert deliveryToDelete != null;

        model.deleteDelivery(deliveryToDelete);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);

        return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS, Messages.format(deliveryToDelete)));
    }

    /**
     * Retrieves delivery to be deleted.
     *
     * @param model Model which the command should operate on.
     * @return existing Delivery that needs to be deleted.
     * @throws CommandException If index given exceeds the number of deliveries.
     */
    private Delivery getDeliveryToDelete(Model model) throws CommandException {
        List<Delivery> deliveryList = model.getModifiedDeliveryList();
        assert deliveryList != null;

        if (targetIndex.getZeroBased() >= deliveryList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }
        return model.getModifiedDeliveryList().get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteDeliveryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDeliveryCommand) other).targetIndex)); // state check
    }
}

