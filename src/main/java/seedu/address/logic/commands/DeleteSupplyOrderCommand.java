package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderList;
import seedu.address.model.order.SupplierOrderList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class DeleteSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the supply order by their phone number. "
            + "Parameters: PHONE_NUMBER\n"
            + "Example: " + COMMAND_WORD + " 87654321";

    public static final String MESSAGE_DELETE_SUPPLY_ORDER_SUCCESS = "Supply order deleted.\n\n%1$s";

    private final String phoneNumber;

    public DeleteSupplyOrderCommand(String phoneNumber) {
        requireAllNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        SupplierOrderList supplierOrderList = model.getSupplierOrderList();

        supplierOrderList.removeOrder(phoneNumber);

        return new CommandResult(String.format(MESSAGE_DELETE_SUPPLY_ORDER_SUCCESS, supplierOrderList.viewOrders()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteSupplyOrderCommand)) {
            return false;
        }

        DeleteSupplyOrderCommand otherCommand = (DeleteSupplyOrderCommand) other;
        return phoneNumber.equals(otherCommand.phoneNumber);
    }
}
