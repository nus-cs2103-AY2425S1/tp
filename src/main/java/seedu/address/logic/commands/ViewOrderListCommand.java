package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewOrderListCommand extends Command {

    public static final String COMMAND_WORD = "viewOrder";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result = "Customer Orders: \n"
                        + model.getOrderList().viewCustomerOrders()
                        + "Supply Orders: \n"
                        + model.getOrderList().viewSupplyOrders();
        return new CommandResult(result);
    }
}
