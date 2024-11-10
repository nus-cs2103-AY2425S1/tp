package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.OrderStatus;

/**
 * Marks a customer order as pending.
 */
public class UnmarkCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "unmarkCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the customer order at the given index as pending.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_ORDER_SUCCESS = "Marked customer order as pending:";
    public static final String MESSAGE_ORDER_ALREADY_PENDING = "The order at index %1$s is already pending.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public UnmarkCustomerOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CustomerOrderList customerOrderList = model.getCustomerOrderList();

        if (targetIndex <= 0 || targetIndex > customerOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        CustomerOrder customerOrder = customerOrderList.getOrders().get(targetIndex - 1);

        if (customerOrder.getStatus() == OrderStatus.PENDING) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_PENDING, targetIndex));
        }

        customerOrder.setStatus(OrderStatus.PENDING);

        customerOrderList.removeOrder(targetIndex - 1);
        customerOrderList.addOrder(customerOrder);

        String resultMessage = MESSAGE_UNMARK_ORDER_SUCCESS + "\n" + customerOrder.toString();
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnmarkCustomerOrderCommand
                && targetIndex == ((UnmarkCustomerOrderCommand) other).targetIndex);
    }
}
