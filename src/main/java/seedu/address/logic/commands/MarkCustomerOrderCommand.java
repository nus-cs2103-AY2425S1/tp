package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Marks a customer order as completed and updates the inventory accordingly.
 */
public class MarkCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "markCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the customer order at the given index as completed and updates the inventory.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "Marked customer order as completed:";
    public static final String MESSAGE_ORDER_ALREADY_COMPLETED = "The order at index %1$s is already completed.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public MarkCustomerOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CustomerOrderList customerOrderList = model.getCustomerOrderList();

        if (targetIndex <= 0 || targetIndex > customerOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        CustomerOrder customerOrder = customerOrderList.getOrders().get(targetIndex - 1);

        if (customerOrder.getStatus() == OrderStatus.COMPLETED) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_COMPLETED, targetIndex));
        }

        List<? extends Product> items = customerOrder.getItems();

        customerOrder.setStatus(OrderStatus.COMPLETED);

        customerOrderList.removeOrder(targetIndex - 1);
        customerOrderList.addOrder(customerOrder);

        // Update personList
        Person personToEdit = customerOrder.getOriginalPerson();
        model.setPerson(personToEdit, personToEdit);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String resultMessage = MESSAGE_MARK_ORDER_SUCCESS + "\n" + customerOrder.toString();
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkCustomerOrderCommand
                && targetIndex == ((MarkCustomerOrderCommand) other).targetIndex);
    }
}