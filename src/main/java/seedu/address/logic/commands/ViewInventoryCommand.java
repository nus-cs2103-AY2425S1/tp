package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Displays the current stock levels of ingredients in the inventory.
 */
public class ViewInventoryCommand extends Command {

    public static final String COMMAND_WORD = "viewInventory";

    public static final String MESSAGE_SUCCESS = "Current Ingredient Stock Levels:\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Retrieve the formatted stock level information from the inventory
        String result = model.getInventory().getFormattedStockLevels();

        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
