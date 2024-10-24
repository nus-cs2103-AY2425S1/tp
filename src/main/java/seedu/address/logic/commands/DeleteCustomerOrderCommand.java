package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.OrderList;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class DeleteCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the customer order at the given index of the displayed customer orders. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_ORDER_SUCCESS = "Customer order deleted at index: %1$d";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public DeleteCustomerOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the customer order list
        CustomerOrderList customerOrderList = model.getCustomerOrderList();

        // Check if the index is valid (index should be 1-based)
        if (targetIndex <= 0 || targetIndex > customerOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        String phoneNumber = customerOrderList.getOrderByIndex(targetIndex - 1).getPhoneNumber();

        customerOrderList.removeOrder(phoneNumber);

        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_ORDER_SUCCESS, targetIndex));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCustomerOrderCommand)) {
            return false;
        }

        DeleteCustomerOrderCommand otherCommand = (DeleteCustomerOrderCommand) other;
        return targetIndex == otherCommand.targetIndex;
    }
}
