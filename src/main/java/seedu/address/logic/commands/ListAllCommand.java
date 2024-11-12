package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import seedu.address.model.Model;
/**
 * Lists all suppliers and deliveries in the Vendor Vault to the user.
 */
public class ListAllCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all supplier(s) and delivery(s).";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

