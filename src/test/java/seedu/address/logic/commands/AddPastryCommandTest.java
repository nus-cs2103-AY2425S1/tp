package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddPastryCommand}.
 */
public class AddPastryCommandTest {
    private static final String PASTRY_NAME = "Croissant";
    private static final double PASTRY_COST = 3.50;

    private Model model;
    private ArrayList<Ingredient> ingredients;

    @BeforeEach
    public void setUp() {
        // Initialize the model before each test
        model = new ModelManager();

        // Prepare ingredients
        ingredients = new ArrayList<>(List.of(
                IngredientCatalogue.FLOUR,
                IngredientCatalogue.SUGAR
        ));
    }

    @Test
    public void execute_newPastry_success() {
        // Create a new AddPastryCommand
        AddPastryCommand command = new AddPastryCommand(PASTRY_NAME, PASTRY_COST, ingredients);

        // Use the model's PastryCatalogue to get the next available ID
        PastryCatalogue catalogue = model.getPastryCatalogue();
        int nextProductId = catalogue.getNextProductId();  // Ensure consistent product ID

        // Create the expected pastry object
        Pastry expectedPastry = new Pastry(nextProductId, PASTRY_NAME, PASTRY_COST, ingredients); // ID assumed to be 1
        String expectedMessage = String.format(AddPastryCommand.MESSAGE_ADD_PASTRY_SUCCESS, expectedPastry);

        // Create a new expected model (which should reflect the expected state)
        Model expectedModel = new ModelManager();

        // Add pastry manually
        expectedModel.addPastry(expectedPastry);

        // Verify that the command executes successfully
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePastry_failure() {
        // Add the pastry to the model's catalogue to simulate existing state
        model.getPastryCatalogue().addPastry(PASTRY_NAME, PASTRY_COST, ingredients);

        // Create an AddPastryCommand with the same details
        AddPastryCommand command = new AddPastryCommand(PASTRY_NAME, PASTRY_COST, ingredients);

        // Verify that a CommandException is thrown due to the duplicate pastry
        assertCommandFailure(command, model, AddPastryCommand.MESSAGE_DUPLICATE_PASTRY);
    }

    @Test
    public void equals() {
        // Create two identical AddPastryCommand instances
        AddPastryCommand command1 = new AddPastryCommand(PASTRY_NAME, PASTRY_COST, ingredients);
        AddPastryCommand command2 = new AddPastryCommand(PASTRY_NAME, PASTRY_COST, ingredients);

        // Create a different command with different pastry details
        ArrayList<Ingredient> differentIngredients = new ArrayList<>(List.of(IngredientCatalogue.CHOCOLATE));
        AddPastryCommand differentCommand = new AddPastryCommand("Chocolate Donut", 4.00, differentIngredients);

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different pastry -> false
    }
}