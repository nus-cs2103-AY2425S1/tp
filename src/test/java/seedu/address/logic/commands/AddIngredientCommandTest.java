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

    private static final String INGREDIENT_NAME = "Sweet";
    private static final double INGREDIENT_COST = 2.50;

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model before each test
        model = new ModelManager();
    }

    @Test
    public void execute_newIngredient_success() {
        // Create a new AddIngredientCommand
        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Get the next available ID from the model's IngredientCatalogue
        IngredientCatalogue catalogue = IngredientCatalogue.getInstance();
        int nextProductId = catalogue.getNextProductId();

        // Create the expected ingredient object
        Ingredient expectedIngredient = new Ingredient(nextProductId, INGREDIENT_NAME, INGREDIENT_COST);

        // Prepare the expected success message
        String expectedMessage = String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, expectedIngredient);

        // Set up the expected model state
        Model expectedModel = new ModelManager();
        expectedModel.addIngredient(expectedIngredient);

        // Execute the command and verify success
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIngredient_failure() {
        // Add the ingredient to the model to simulate an existing ingredient
        model.getIngredientCatalogue().addIngredient(new Ingredient(1, INGREDIENT_NAME, INGREDIENT_COST));

        // Create an AddIngredientCommand with the same ingredient details
        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Verify that a CommandException is thrown for the duplicate ingredient
        assertCommandFailure(command, model, AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

    @Test
    public void equals() {
        // Create two identical AddIngredientCommand instances
        AddIngredientCommand command1 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        AddIngredientCommand command2 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);

        // Create a different command with different ingredient details
        AddIngredientCommand differentCommand = new AddIngredientCommand("Milk", 1.00);

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different ingredient -> false
    }
}