package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Person;

/**
 * Deletes a supply order at the specified index.
 */
public class DeleteSupplyOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete the supply order at the given index of the displayed supplier orders. "
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

        Order order = supplyOrderList.getOrder(targetIndex - 1);

        Person person = order.getOriginalPerson();
        person.removeOrder(order);

        supplyOrderList.removeOrder(targetIndex - 1);

        // Update personList
        model.setPerson(person, person);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

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
