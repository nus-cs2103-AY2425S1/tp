package seedu.sellsavvy.logic.commands.ordercommands;

import seedu.sellsavvy.commons.util.ToStringBuilder;
import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.logic.commands.exceptions.CommandException;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.parser.CliSyntax.*;

/**
 * Adds an order under a specified person.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order under the specified person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ITEM + "ITEM "
            + PREFIX_DATE + "DELIVERY_BY "
            + PREFIX_COUNT + "ITEM_COUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Foo Chao "
            + PREFIX_ITEM + "Lamp "
            + PREFIX_DATE + "2024-12-20 "
            + PREFIX_COUNT + "2";

    public static final String MESSAGE_SUCCESS = "New Order added under xxx: xxx";

    private final Person personToAddUnder;

    private final Order toAdd;

    /**
     * Creates an AddOrderCommand to add the specific order under
     */
    public AddOrderCommand(Person person, Order order) {
        requireNonNull(person);
        requireNonNull(order);
        personToAddUnder = person;
        toAdd = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // add order in model here
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof AddOrderCommand)) {
            return false;
        }

        AddOrderCommand otherAddOrderCommand = (AddOrderCommand) other;
        boolean samePerson = personToAddUnder.equals(otherAddOrderCommand.personToAddUnder);
        boolean sameOrder = toAdd.equals(otherAddOrderCommand.toAdd);
        return samePerson && sameOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personToAddUnder", personToAddUnder)
                .add("toAdd", toAdd)
                .toString();
    }
}
