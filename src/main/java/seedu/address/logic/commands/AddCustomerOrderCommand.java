package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderList;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class AddCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "addCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new customer order to the bakery's order list. "
            + "Parameters: PHONE_NUMBER PRODUCT_ID\n"
            + "Example: " + COMMAND_WORD + " 87654321 1";

    public static final String MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS = "New customer order added: \n%1$s";

    private final String phoneNumber;
    private final ArrayList<Integer> idList;

    public AddCustomerOrderCommand(String phoneNumber, ArrayList<Integer> idList) {
        requireAllNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.idList = idList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        PastryCatalogue pastryCatalogue = model.getPastryCatalogue();

        List<Product> productList = idList.stream()
                                        .map(pastryCatalogue::getProductById)
                .                       filter(Objects::nonNull)
                                        .toList();

        CustomerOrder customerOrder = new CustomerOrder(phoneNumber, productList, OrderStatus.PENDING);
        model.addCustomerOrder(customerOrder);

        return new CommandResult(String.format(MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS, customerOrder.viewOrder()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCustomerOrderCommand)) {
            return false;
        }

        AddCustomerOrderCommand otherCommand = (AddCustomerOrderCommand) other;
        return phoneNumber.equals(otherCommand.phoneNumber) && idList.equals(otherCommand.idList);
    }
}
