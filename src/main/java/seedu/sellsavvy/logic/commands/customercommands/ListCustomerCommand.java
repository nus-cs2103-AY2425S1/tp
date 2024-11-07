package seedu.sellsavvy.logic.commands.customercommands;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.sellsavvy.logic.commands.Command;
import seedu.sellsavvy.logic.commands.CommandResult;
import seedu.sellsavvy.model.Model;

/**
 * Lists all customers in the address book to the user.
 */
public class ListCustomerCommand extends Command {

    public static final String COMMAND_WORD = "listcustomer";
    public static final String COMMAND_ALIAS = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all customers.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
