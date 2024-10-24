package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import seedu.address.model.Model;

/**
 * Lists all products in the address book to the user.
 */
public class ViewAllProductCommand extends ViewProductCommand {

    public static final String COMMAND_WORD = "view_product";

    public static final String MESSAGE_SUCCESS = "Showing all products";

    public ViewAllProductCommand() {
        super(PREDICATE_SHOW_ALL_PRODUCTS);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

