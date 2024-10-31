package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.SupplyOrderList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class DeleteSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the supply order at the given index of the displayed supplier orders. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SUPPLY_ORDER_SUCCESS = "Supply order deleted at index: %1$d";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public DeleteSupplyOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        SupplyOrderList supplyOrderList = model.getSupplyOrderList();

        if (targetIndex <= 0 || targetIndex > supplyOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        String phoneNumber = supplyOrderList.getOrderByIndex(targetIndex - 1).getPhoneNumber();

        supplyOrderList.removeOrder(phoneNumber);

        return new CommandResult(String.format(MESSAGE_DELETE_SUPPLY_ORDER_SUCCESS, targetIndex));
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
        return targetIndex == otherCommand.targetIndex;
    }
}
