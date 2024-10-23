package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code CheckIngredientStockCommand}.
 */
public class CheckIngredientStockCommandTest {

    private static final String INGREDIENT_NAME = "Flour";
    private static final String NON_EXISTENT_INGREDIENT = "GoldDust";

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model and add necessary stock before each test
        model = new ModelManager();

        // Add stock levels for ingredients
        model.getInventory().addStock(IngredientCatalogue.FLOUR.getProductId(), 10);
        model.getInventory().addStock(IngredientCatalogue.SUGAR.getProductId(), 5);
    }

    @Test
    public void execute_sufficientStock_success() {
        // Ensure the ingredient exists in the catalogue
        Ingredient ingredient = model.getInventory().getIngredientByName(INGREDIENT_NAME);

        // Create the CheckIngredientStockCommand
        CheckIngredientStockCommand command = new CheckIngredientStockCommand(ingredient.getName());

        // Prepare the expected success message
        String expectedMessage = String.format(CheckIngredientStockCommand.MESSAGE_INGREDIENT_AVAILABLE, INGREDIENT_NAME);

        // Verify that the command executes successfully
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_insufficientStock_failure() {
        // Log initial stock levels
        System.out.println("Initial stock levels:");
        model.getInventory().displayStockLevels();

        // Remove all stock of sugar to simulate insufficient stock
        model.getInventory().removeStock(IngredientCatalogue.SUGAR.getProductId(), 5);

        // Verify that the stock was reduced to 0
        assertEquals(0, model.getInventory().getStockLevel(IngredientCatalogue.SUGAR.getProductId()),
                "Sugar stock should be 0 after removal.");

        // Create the CheckIngredientStockCommand
        CheckIngredientStockCommand command = new CheckIngredientStockCommand("Sugar");

        // Verify that a CommandException is thrown due to insufficient stock
        assertCommandFailure(command, model,
                String.format(CheckIngredientStockCommand.MESSAGE_INGREDIENT_UNAVAILABLE, "Sugar"));
    }

    @Test
    public void execute_nonExistentIngredient_failure() {
        // Create the CheckIngredientStockCommand for a non-existent ingredient
        CheckIngredientStockCommand command = new CheckIngredientStockCommand(NON_EXISTENT_INGREDIENT);

        // Verify that a CommandException is thrown due to the ingredient not being found
        assertCommandFailure(command, model,
                String.format(CheckIngredientStockCommand.MESSAGE_INGREDIENT_NOT_FOUND, NON_EXISTENT_INGREDIENT));
    }

    @Test
    public void equals() {
        // Create two identical CheckIngredientStockCommand instances
        CheckIngredientStockCommand command1 = new CheckIngredientStockCommand(INGREDIENT_NAME);
        CheckIngredientStockCommand command2 = new CheckIngredientStockCommand(INGREDIENT_NAME);

        // Create a different command with another ingredient name
        CheckIngredientStockCommand differentCommand = new CheckIngredientStockCommand(NON_EXISTENT_INGREDIENT);

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different ingredient -> false
    }
}