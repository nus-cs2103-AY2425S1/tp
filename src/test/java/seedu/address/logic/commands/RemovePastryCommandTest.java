package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Pastry;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemovePastryCommand}.
 */
public class RemovePastryCommandTest {
    private static final String PASTRY_NAME = "Croissant";
    private static final double PASTRY_COST = 3.50;

    private Model model;
    private ArrayList<seedu.address.model.product.Ingredient> ingredients;

    @BeforeEach
    public void setUp() {
        // Initialize the model before each test
        model = new ModelManager();
        ingredients = new ArrayList<>(List.of(
                IngredientCatalogue.FLOUR,
                IngredientCatalogue.SUGAR
        ));
    }

    @Test
    public void execute_removeExistingPastry_success() {
        // Add a pastry to the model to simulate existing state
        Pastry existingPastry = new Pastry(4, PASTRY_NAME, PASTRY_COST, ingredients);
        model.getPastryCatalogue().addPastry(PASTRY_NAME, PASTRY_COST, ingredients);

        // Create the RemovePastryCommand
        RemovePastryCommand command = new RemovePastryCommand(PASTRY_NAME);

        // Expected model after the pastry is removed
        Model expectedModel = new ModelManager();
        // Pastry removed from the expected model (manually simulated)
        expectedModel.getPastryCatalogue().addPastry(PASTRY_NAME, PASTRY_COST, ingredients);
        expectedModel.getPastryCatalogue().deleteProduct(existingPastry.getProductId());

        // Execute the command and verify success
        String expectedMessage = String.format(RemovePastryCommand.MESSAGE_REMOVE_PASTRY_SUCCESS, PASTRY_NAME);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentPastry_throwsCommandException() {
        // Create the RemovePastryCommand with a non-existent pastry name
        RemovePastryCommand command = new RemovePastryCommand("NonExistentPastry");

        // Verify that a CommandException is thrown with the correct message
        assertCommandFailure(command, model, RemovePastryCommand.MESSAGE_PASTRY_NOT_FOUND);
    }

    @Test
    public void equals() {
        // Create two RemovePastryCommand instances with the same name
        RemovePastryCommand command1 = new RemovePastryCommand(PASTRY_NAME);
        RemovePastryCommand command2 = new RemovePastryCommand(PASTRY_NAME);

        // Create a different command with a different name
        RemovePastryCommand differentCommand = new RemovePastryCommand("Chocolate Donut");

        // Verify equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different pastry name -> false
    }
}
