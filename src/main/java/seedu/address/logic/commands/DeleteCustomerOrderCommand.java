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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the customer order by their phone number. "
            + "Parameters: PHONE_NUMBER\n"
            + "Example: " + COMMAND_WORD + " 87654321";

    public static final String MESSAGE_DELETE_CUSTOMER_ORDER_SUCCESS = "Customer order deleted.\n\n%1$s";

    private final String phoneNumber;

    public DeleteCustomerOrderCommand(String phoneNumber) {
        requireAllNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CustomerOrderList customerOrderList = model.getCustomerOrderList();

        customerOrderList.removeOrder(phoneNumber);

        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_ORDER_SUCCESS, customerOrderList.viewOrders()));
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
        return phoneNumber.equals(otherCommand.phoneNumber);
    }
}
