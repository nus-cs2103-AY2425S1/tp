package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Marks a supply order as completed and updates the inventory accordingly.
 */
public class MarkSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "markSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the supply order at the given index as completed and updates the inventory.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "Marked supply order as completed:";
    public static final String MESSAGE_ORDER_ALREADY_COMPLETED = "The order at index %1$s is already completed.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public MarkSupplyOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        SupplyOrderList supplyOrderList = model.getSupplyOrderList();

        // Validate index
        if (targetIndex <= 0 || targetIndex > supplyOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        // Retrieve the supplier order at the target index
        SupplyOrder supplyOrder = supplyOrderList.getOrders().get(targetIndex - 1);

        // Check if the order is already completed
        if (supplyOrder.getStatus() == OrderStatus.COMPLETED) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_COMPLETED, targetIndex));
        }

        List<? extends Product> items = supplyOrder.getItems();

        // Mark the order as completed
        supplyOrder.setStatus(OrderStatus.COMPLETED);

        supplyOrderList.removeOrder(targetIndex - 1);
        supplyOrderList.addOrder(supplyOrder);

        // Update personList
        Person personToEdit = supplyOrder.getOriginalPerson();
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = MESSAGE_MARK_ORDER_SUCCESS + "\n" + supplyOrder.toString();
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkSupplyOrderCommand
                && targetIndex == ((MarkSupplyOrderCommand) other).targetIndex);
    }
}
