package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.product.Ingredient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveIngredientCommand}.
 */
public class RemoveIngredientCommandTest {
    private static final String INGREDIENT_NAME = "Flour";
    private static final double INGREDIENT_COST = 1.50;

    private Model model;
    private Ingredient ingredientToRemove;

    @BeforeEach
    public void setUp() {
        // Initialize the model before each test
        model = new ModelManager();
        ingredientToRemove = new Ingredient(1, INGREDIENT_NAME, INGREDIENT_COST);

        // Add the ingredient to the model's catalogue
        model.getIngredientCatalogue().addIngredient(ingredientToRemove);
    }

    @Test
    public void execute_removeExistingIngredient_success() {
        // Create the RemoveIngredientCommand
        RemoveIngredientCommand command = new RemoveIngredientCommand(INGREDIENT_NAME);

        // Create the expected model after removing the ingredient
        Model expectedModel = new ModelManager();
        expectedModel.getIngredientCatalogue().addIngredient(ingredientToRemove);
        expectedModel.getIngredientCatalogue().deleteProduct(ingredientToRemove.getProductId());

        // Execute the command and verify success
        String expectedMessage = String.format(RemoveIngredientCommand.MESSAGE_REMOVE_INGREDIENT_SUCCESS,
                INGREDIENT_NAME);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentIngredient_throwsCommandException() {
        // Create the RemoveIngredientCommand with a non-existent ingredient name
        RemoveIngredientCommand command = new RemoveIngredientCommand("NonExistentIngredient");

        // Verify that a CommandException is thrown due to non-existent ingredient
        assertCommandFailure(command, model, RemoveIngredientCommand.MESSAGE_INGREDIENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        // Create two identical RemoveIngredientCommand instances
        RemoveIngredientCommand command1 = new RemoveIngredientCommand(INGREDIENT_NAME);
        RemoveIngredientCommand command2 = new RemoveIngredientCommand(INGREDIENT_NAME);

        // Create a different command with a different ingredient name
        RemoveIngredientCommand differentCommand = new RemoveIngredientCommand("Sugar");

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different ingredient -> false
    }
}
