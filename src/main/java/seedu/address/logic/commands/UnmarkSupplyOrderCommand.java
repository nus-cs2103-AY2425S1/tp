package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Person;

/**
 * Marks a supply order as pending.
 */
public class UnmarkSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "unmarkSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the supply order at the given index as pending.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_ORDER_SUCCESS = "Marked supply order as pending:";
    public static final String MESSAGE_ORDER_ALREADY_PENDING = "The order at index %1$s is already pending.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public UnmarkSupplyOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        SupplyOrderList supplyOrderList = model.getSupplyOrderList();

        if (targetIndex <= 0 || targetIndex > supplyOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        SupplyOrder supplyOrder = supplyOrderList.getOrders().get(targetIndex - 1);

        if (supplyOrder.getStatus() == OrderStatus.PENDING) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_PENDING, targetIndex));
        }

        supplyOrder.setStatus(OrderStatus.PENDING);

        supplyOrderList.removeOrder(targetIndex - 1);
        supplyOrderList.addOrder(supplyOrder);

        // Update personList
        Person personToEdit = supplyOrder.getOriginalPerson();
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = MESSAGE_UNMARK_ORDER_SUCCESS + "\n" + supplyOrder.toString();
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnmarkSupplyOrderCommand
                && targetIndex == ((UnmarkSupplyOrderCommand) other).targetIndex);
    }
}
