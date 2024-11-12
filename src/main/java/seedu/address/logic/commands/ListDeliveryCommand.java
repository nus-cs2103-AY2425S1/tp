package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import seedu.address.model.Model;

/**
 * Lists all deliveries in the Vendor Vault to the user.
 */
public class ListDeliveryCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all delivery(s)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

