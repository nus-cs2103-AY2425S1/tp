package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Add an order to a customer
 */
public class PutOrderCommand extends Command {

    public static final String COMMAND_WORD = "put";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": put an order to a customer,"
            + " order is always in lowercase. "
            + "\nParameters: [NAME OF ORDER] "
            + "[" + PREFIX_NAME + " NAME] "
            + "\nExample: " + COMMAND_WORD + " cake " + PREFIX_NAME + "John";

    public static final String MESSAGE_SUCCESS = "Order added to customer.";
    public static final String MESSAGE_ORDER_NOT_FOUND = "Order does not exist: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person does not exist: %1$s";

    private final Order order;
    private final Name name;

    /**
     * @param order to add
     * @param name of the customer to add the order
     */
    public PutOrderCommand(Order order, Name name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasOrder(this.order)) {
            throw new CommandException(String.format(MESSAGE_ORDER_NOT_FOUND, this.order.toString()));
        }

        Person p = model.findPersonByName(this.name);

        if (p == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, this.name.toString()));
        }

        p.putOrder(this.order);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PutOrderCommand)) {
            return false;
        }

        PutOrderCommand e = (PutOrderCommand) other;
        return this.order.equals(e.order)
                && this.name.equals(e.name);
    }
}
