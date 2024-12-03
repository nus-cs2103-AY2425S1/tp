package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import seedu.address.model.Model;


/**
 * Lists all suppliers in the Vendor Vault to the user.
 */
public class ListSupplierCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all supplier(s)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

