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
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddIngredientCommand}.
 */
public class AddIngredientCommandTest {

    private static final String INGREDIENT_NAME = "Peanut Butter";
    private static final double INGREDIENT_COST = 4.00;

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model before each test
        model = new ModelManager();
    }

    @Test
    public void execute_newIngredient_success() {
        // Arrange
        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        int nextProductId = model.getIngredientCatalogue().getNextProductId();
        Ingredient expectedIngredient = new Ingredient(nextProductId, INGREDIENT_NAME, INGREDIENT_COST);
        String expectedMessage = String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, expectedIngredient);

        Model expectedModel = new ModelManager();
        expectedModel.addIngredient(expectedIngredient);

        // Act and Assert
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Check that the ingredient exists in the catalogue
        assertTrue(model.getIngredientCatalogue().getCatalogue().containsValue(expectedIngredient));
    }

    @Test
    public void execute_duplicateIngredient_failure() {
        // Arrange
        Ingredient existingIngredient = new Ingredient(1, INGREDIENT_NAME, INGREDIENT_COST);
        model.getIngredientCatalogue().addIngredient(existingIngredient);

        AddIngredientCommand command = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        Model expectedModel = new ModelManager(); // Create an unmodified model for comparison

        // Act and Assert
        assertCommandFailure(command, model, AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);

        // Verify that the model remains unchanged after attempting to add duplicate
        assertTrue(model.equals(expectedModel));
    }

    @Test
    public void execute_ingredientWithWhitespaceOrSpecialChars_success() {
        // Arrange
        String specialName = "Maple Syrup #1";
        double specialCost = 3.99;
        AddIngredientCommand command = new AddIngredientCommand(specialName, specialCost);
        int nextProductId = model.getIngredientCatalogue().getNextProductId();
        Ingredient expectedIngredient = new Ingredient(nextProductId, specialName, specialCost);

        Model expectedModel = new ModelManager();
        expectedModel.addIngredient(expectedIngredient);

        // Act and Assert
        assertCommandSuccess(command, model, String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, expectedIngredient), expectedModel);

        // Verify that ingredient with special characters was added successfully
        assertTrue(model.getIngredientCatalogue().getCatalogue().containsValue(expectedIngredient));
    }

    @Test
    public void execute_ingredientWithBoundaryValues_success() {
        // Arrange
        String boundaryName = "Vanilla";
        double highCost = Double.MAX_VALUE;
        double lowCost = 0.01;

        // High-cost ingredient
        AddIngredientCommand highCostCommand = new AddIngredientCommand(boundaryName, highCost);
        int nextProductIdHigh = model.getIngredientCatalogue().getNextProductId();
        Ingredient highCostIngredient = new Ingredient(nextProductIdHigh, boundaryName, highCost);
        Model expectedHighCostModel = new ModelManager();
        expectedHighCostModel.addIngredient(highCostIngredient);

        assertCommandSuccess(highCostCommand, model, String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, highCostIngredient), expectedHighCostModel);

        // Verify that high-cost ingredient was added successfully
        assertTrue(model.getIngredientCatalogue().getCatalogue().containsValue(highCostIngredient));

        // Low-cost ingredient
        AddIngredientCommand lowCostCommand = new AddIngredientCommand(boundaryName, lowCost);
        int nextProductIdLow = model.getIngredientCatalogue().getNextProductId();
        Ingredient lowCostIngredient = new Ingredient(nextProductIdLow, boundaryName, lowCost);
        Model expectedLowCostModel = new ModelManager();
        expectedLowCostModel.addIngredient(lowCostIngredient);

        assertCommandSuccess(lowCostCommand, model, String.format(AddIngredientCommand.MESSAGE_ADD_INGREDIENT_SUCCESS, lowCostIngredient), expectedLowCostModel);

        // Verify that low-cost ingredient was added successfully
        assertTrue(model.getIngredientCatalogue().getCatalogue().containsValue(lowCostIngredient));
    }

    @Test
    public void equals() {
        // Arrange
        AddIngredientCommand command1 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        AddIngredientCommand command2 = new AddIngredientCommand(INGREDIENT_NAME, INGREDIENT_COST);
        AddIngredientCommand differentCommand = new AddIngredientCommand("Milk", 1.00);
        AddIngredientCommand caseInsensitiveCommand = new AddIngredientCommand("MAPLE SYRUP", INGREDIENT_COST);

        // Act & Assert
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different ingredient -> false
        assertFalse(command1.equals(caseInsensitiveCommand)); // Case-sensitive check -> false if case matters
    }
}