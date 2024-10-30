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
import seedu.address.model.product.IngredientCatalogue;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddIngredientCommand}.
 */
public class AddIngredientCommandTest {
    private static final String INGREDIENT_NAME = "Syrup";
    private static final double INGREDIENT_COST = 2.50;

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model and create the test ingredient
        model = new ModelManager();
    }

    @Test
    public void execute_newIngredient_success() {
        // Create a new AddIngredientCommand
        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Use the model's IngredientCatalogue to get the next available ID
        IngredientCatalogue catalogue = model.getIngredientCatalogue();
        int nextProductId = catalogue.getNextProductId();

        // Create the expected ingredient object
        Ingredient expectedIngredient = new Ingredient(nextProductId, INGREDIENT_NAME, INGREDIENT_COST);

        // Prepare the expected success message
        String expectedMessage = String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, expectedIngredient);

        // Set up the expected model state
        Model expectedModel = new ModelManager();

        // Add ingredient manually
        expectedModel.addIngredient(expectedIngredient);

        // Execute the command and verify success
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the ingredient exists in the catalogue
        assertTrue(model.getIngredientCatalogue().getCatalogue().containsValue(expectedIngredient));
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        // Use the model's IngredientCatalogue to get the next available ID
        IngredientCatalogue catalogue = model.getIngredientCatalogue();
        int nextProductId = catalogue.getNextProductId();  // Ensure consistent product ID

        // Add the ingredient to the model to simulate an existing ingredient
        Ingredient expectedIngredient = new Ingredient(nextProductId, INGREDIENT_NAME, INGREDIENT_COST);
        model.addIngredient(expectedIngredient);

        // Create the AddIngredientCommand with the same ingredient
        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Verify that a CommandException is thrown due to the duplicate ingredient
        assertCommandFailure(command, model, AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void equals() {
        // Create two identical AddIngredientCommand instances
        AddIngredientCommand command1 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        AddIngredientCommand command2 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Create a different command with different ingredient details
        AddIngredientCommand differentCommand = new AddIngredientCommand("Sugar", 2.00);

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different ingredient -> false
    }
}