package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Removes an existing ingredient from the bakery's ingredient catalogue.
 */
public class RemoveIngredientCommand extends Command {
    public static final String COMMAND_WORD = "removeIngredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an ingredient from the bakery's catalogue. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Flour";

    public static final String MESSAGE_REMOVE_INGREDIENT_SUCCESS = "Ingredient removed: %1$s";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "This ingredient does not exist in the catalogue.";

    private final String ingredientName;

    /**
     * Creates a RemoveIngredientCommand to remove the specified {@code Ingredient} by name.
     */
    public RemoveIngredientCommand(String ingredientName) {
        requireNonNull(ingredientName);
        this.ingredientName = ingredientName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        IngredientCatalogue ingredientCatalogue = model.getIngredientCatalogue();
        Ingredient ingredientToRemove = findIngredientByName(ingredientCatalogue, ingredientName);

        if (ingredientToRemove == null) {
            throw new CommandException(MESSAGE_INGREDIENT_NOT_FOUND);
        }

        ingredientCatalogue.deleteProduct(ingredientToRemove.getProductId());

        return new CommandResult(String.format(MESSAGE_REMOVE_INGREDIENT_SUCCESS, ingredientName));
    }

    /**
     * Finds an ingredient by its name in the ingredient catalogue.
     * @return The ingredient if found, otherwise null.
     */
    private Ingredient findIngredientByName(IngredientCatalogue catalogue, String name) {
        return catalogue.getCatalogue().values().stream()
                .filter(product -> product instanceof Ingredient)
                .map(product -> (Ingredient) product)
                .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveIngredientCommand)) {
            return false;
        }

        RemoveIngredientCommand otherCommand = (RemoveIngredientCommand) other;
        return ingredientName.equals(otherCommand.ingredientName);
    }
}
