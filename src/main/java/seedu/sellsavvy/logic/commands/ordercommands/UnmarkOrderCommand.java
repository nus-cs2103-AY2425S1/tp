package seedu.sellsavvy.logic.commands.ordercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST;

import java.util.List;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Status;

/**
 * Reverts an order to the pending status.
 */
public class UnmarkOrderCommand extends Command {

    public static final String COMMAND_WORD = "unmarkorder";
    public static final String COMMAND_ALIAS = "unmarko";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverts an order identified by "
            + "the index number used in the displayed order list to the pending status.\n"
            + "Parameters: ORDER_INDEX (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_UNMARK_ORDER_SUCCESS =
            "The order has been reverted to the pending status: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_UNMARKED = "The order is already in pending status.";

    private final Index index;

    /**
     * Creates a UnmarkOrderCommand to revert the order of an order
     * at the specified index to the pending status.
     *
     * @param index of the order in the displayed order list to mark.
     */
    public UnmarkOrderCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> filteredOrderList = model.getFilteredOrderList();
        if (filteredOrderList == null) {
            throw new CommandException(MESSAGE_ORDERLIST_DOES_NOT_EXIST);
        }

        if (index.getZeroBased() >= filteredOrderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToUnmark = filteredOrderList.get(index.getZeroBased());
        if (!orderToUnmark.isCompleted()) {
            throw new CommandException(MESSAGE_ORDER_ALREADY_UNMARKED);
        }

        Order newOrder = createUnmarkedOrder(orderToUnmark);
        model.setOrder(orderToUnmark, newOrder);
        return new CommandResult(String.format(MESSAGE_UNMARK_ORDER_SUCCESS, Messages.format(newOrder)));
    }

    /**
     * Creates an unmarked version of the given {@code Order}.
     */
    public static Order createUnmarkedOrder(Order order) {
        assert order != null;

        Item item = order.getItem();
        Date date = order.getDate();
        Quantity quantity = order.getQuantity();
        Status status = Status.PENDING;

        return new Order(item, quantity, date, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkOrderCommand)) {
            return false;
        }

        UnmarkOrderCommand otherMarkOrderCommand = (UnmarkOrderCommand) other;
        return index.equals(otherMarkOrderCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
