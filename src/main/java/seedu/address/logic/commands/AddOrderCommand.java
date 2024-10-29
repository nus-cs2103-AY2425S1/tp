package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Add a new order to addressbook
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to the address book,"
            + " order is always in lowercase. "
            + "\nParameters: [NAME OF ORDER]"
            + "\nExample: " + COMMAND_WORD + " cake";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the address book";

    private final String toAdd;

    public AddOrderCommand(String name) {
        this.toAdd = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!Order.isValidName(toAdd)) {
            throw new CommandException(Order.MESSAGE_CONSTRAINTS);
        }
        Order order = new Order(toAdd);

        if (model.hasOrder(order)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.addOrder(order);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand e = (AddOrderCommand) other;
        return this.toAdd.equals(e.toAdd);
    }
}
