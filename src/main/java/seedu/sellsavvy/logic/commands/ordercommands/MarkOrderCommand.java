package seedu.sellsavvy.logic.commands.ordercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.Count;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.person.Person;

import java.util.List;

/**
 * Marks an order as completed.
 */
public class MarkOrderCommand extends Command {

    public static final String COMMAND_WORD = "markOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an order as completed."
            + "Parameters: ORDER_INDEX (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "The order has been marked as completed: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_MARKED = "The order has already been marked as completed.";

    private final Index index;

    /**
     * Creates a MarkOrderCommand to mark the order under the
     * specified index as completed.
     *
     * @param index of the order in the displayed order list to mark.
     */
    public MarkOrderCommand(Index index) {
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

        Order orderToMark = filteredOrderList.get(index.getZeroBased());
        if (orderToMark.getStatus() == Status.COMPLETED) {
            throw new CommandException(MESSAGE_ORDER_ALREADY_MARKED);
        }

        Order newOrder = createMarkedOrder(orderToMark);
        model.setOrder(orderToMark, newOrder);
        return new CommandResult(String.format(MESSAGE_MARK_ORDER_SUCCESS, Messages.format(newOrder)));
    }

    /**
     * Creates a marked version of the given {@code Order}.
     */
    public static Order createMarkedOrder(Order order) {
        assert order != null;

        Item item = order.getItem();
        Date date = order.getDate();
        Count count = order.getCount();
        Status status = Status.COMPLETED;

        return new Order(item, count, date, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkOrderCommand)) {
            return false;
        }

        MarkOrderCommand otherMarkOrderCommand = (MarkOrderCommand) other;
        return index.equals(otherMarkOrderCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
