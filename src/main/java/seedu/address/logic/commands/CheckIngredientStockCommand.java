package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Inventory;

/**
 * Checks if sufficient stock of a specific ingredient is available in the inventory.
 */
public class CheckIngredientStockCommand extends Command {

    public static final String COMMAND_WORD = "checkIngredientStock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks the stock level of a specific ingredient. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Flour";

    public static final String MESSAGE_INGREDIENT_AVAILABLE = "Sufficient stock available for: %1$s";
    public static final String MESSAGE_INGREDIENT_UNAVAILABLE = "Insufficient stock for: %1$s";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "Ingredient not found in the inventory: %1$s";

    private final String ingredientName;

    /**
     * Creates a CheckIngredientStockCommand to check the stock of the specified {@code Ingredient}.
     */
    public CheckIngredientStockCommand(String ingredientName) {
        requireNonNull(ingredientName);
        this.ingredientName = ingredientName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Inventory inventory = model.getInventory();
        Ingredient ingredient;
        try {
            // Attempt to retrieve the ingredient by name
            ingredient = inventory.getIngredientByName(ingredientName);
        } catch (NoSuchElementException e) {
            // Handle non-existent ingredient scenario
            throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, ingredientName));
        }

        // Check if the stock level is sufficient (greater than 0).
        int stockLevel = inventory.getStockLevel(ingredient.getProductId());
        if (stockLevel > 0) {
            return new CommandResult(String.format(MESSAGE_INGREDIENT_AVAILABLE, ingredientName));
        } else {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_UNAVAILABLE, ingredientName));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheckIngredientStockCommand)) {
            return false;
        }

        CheckIngredientStockCommand otherCommand = (CheckIngredientStockCommand) other;
        return ingredientName.equals(otherCommand.ingredientName);
    }
}

