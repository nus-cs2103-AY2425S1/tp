package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import seedu.address.model.Model;

/**
 * Lists all suppliers in the address book to the user.
 */
public class ViewAllSupplierCommand extends ViewSupplierCommand {

    public static final String COMMAND_WORD = CommandWords.VIEW_SUPPLIER_COMMAND;

    public static final String MESSAGE_SUCCESS = "Showing all suppliers";

    public ViewAllSupplierCommand() {
        super(PREDICATE_SHOW_ALL_SUPPLIERS);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
