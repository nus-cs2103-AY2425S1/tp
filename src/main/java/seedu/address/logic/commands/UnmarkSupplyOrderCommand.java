package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;

/**
 * Marks a supply order as pending.
 */
public class UnmarkSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "unmarkSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the supply order at the given index as pending.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_ORDER_SUCCESS = "Marked supply order as pending: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_PENDING = "The order at index %1$s is already pending.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public UnmarkSupplyOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        SupplyOrderList supplyOrderList = model.getSupplyOrderList();

        if (targetIndex <= 0 || targetIndex > supplyOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        SupplyOrder supplyOrder = supplyOrderList.getOrders().get(targetIndex - 1);

        if (supplyOrder.getStatus() == OrderStatus.PENDING) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_PENDING, targetIndex));
        }

        supplyOrder.setStatus(OrderStatus.PENDING);

        supplyOrderList.removeOrder(targetIndex - 1);
        supplyOrderList.addOrder(supplyOrder);

        return new CommandResult(String.format(MESSAGE_UNMARK_ORDER_SUCCESS, targetIndex));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnmarkSupplyOrderCommand
                && targetIndex == ((UnmarkSupplyOrderCommand) other).targetIndex);
    }
}
