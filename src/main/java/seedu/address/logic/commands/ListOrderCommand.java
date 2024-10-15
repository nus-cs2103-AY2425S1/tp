package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List all order in the addressbook
 */
public class ListOrderCommand extends Command {

    public static final String COMMAND_WORD = "listOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all order(s) from the address book";

    public static final String MESSAGE_SUCCESS = "Order(s): ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS + model.getOrderList().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListOrderCommand)) {
            return false;
        }

        return true;
    }
}
