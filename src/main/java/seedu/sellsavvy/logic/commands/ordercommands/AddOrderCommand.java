package seedu.sellsavvy.logic.commands.ordercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;

import java.util.List;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.person.Person;

/**
 * Adds an order under a specified person.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order under the specified person. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ITEM + "ITEM "
            + PREFIX_DATE + "DELIVERY_BY "
            + "[" + PREFIX_COUNT + "QUANTITY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM + "Lamp "
            + PREFIX_DATE + "20-12-2024 "
            + PREFIX_COUNT + "2\n"
            + "If [" + PREFIX_COUNT + "QUANTITY] is not provided, the quantity will be set to 1";

    public static final String MESSAGE_ADD_ORDER_SUCCESS = "New order added for %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_ORDER_WARNING = "Note: "
            + "This customer already has an order for this item"
            + ", verify if this is a mistake\n";

    private final Index index;
    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specific order under the specified index.
     *
     * @param index of the person in the filtered person list to add order under.
     * @param toAdd the order made by the person.
     */
    public AddOrderCommand(Index index, Order toAdd) {
        requireAllNonNull(index, toAdd);
        this.index = index;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddUnder = lastShownList.get(index.getZeroBased());
        OrderList orderList = personToAddUnder.getOrderList();
        String feedbackToUser = orderList.contains(toAdd) ? MESSAGE_DUPLICATE_ORDER_WARNING : "";
        orderList.add(toAdd);
        personToAddUnder.resetFilteredOrderList();

        return new CommandResult(feedbackToUser + String.format(MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(toAdd)));
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

        AddOrderCommand otherAddOrderCommand = (AddOrderCommand) other;
        return index.equals(otherAddOrderCommand.index)
                && toAdd.equals(otherAddOrderCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("order", toAdd)
                .toString();
    }
}
