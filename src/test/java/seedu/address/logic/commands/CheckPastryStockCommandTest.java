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
import seedu.address.model.product.Pastry;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code CheckPastryStockCommand}.
 */
public class CheckPastryStockCommandTest {

    private static final String PASTRY_NAME = "Chocolate Donut";
    private static final String NON_EXISTENT_PASTRY = "NonExistentPastry";

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model and add necessary stock before each test
        model = new ModelManager();

        // Prepare stock levels for ingredients
        model.getInventory().addStock(IngredientCatalogue.FLOUR.getProductId(), 10);
        model.getInventory().addStock(IngredientCatalogue.SUGAR.getProductId(), 10);
        model.getInventory().addStock(IngredientCatalogue.CHOCOLATE.getProductId(), 5);
    }

    @Test
    public void execute_sufficientStock_success() {
        // Ensure the pastry exists in the catalogue
        Pastry pastry = model.getPastryCatalogue().getPastryByName(PASTRY_NAME);

        // Create the CheckPastryStockCommand
        CheckPastryStockCommand command = new CheckPastryStockCommand(pastry.getName());

        // Prepare the expected success message
        String expectedMessage = String.format(CheckPastryStockCommand.MESSAGE_PASTRY_AVAILABLE, pastry.getName());

        // Verify that the command executes successfully
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_insufficientStock_failure() {
        // Log initial stock levels for pastries
        System.out.println("Initial stock levels:");
        model.getInventory().displayStockLevels();

        // Remove all chocolate stock to simulate insufficient stock
        model.getInventory().removeStock(IngredientCatalogue.CHOCOLATE.getProductId(), 5);

        // Verify that the stock was reduced to 0
        assertEquals(0, model.getInventory().getStockLevel(IngredientCatalogue.CHOCOLATE.getProductId()),
                "Chocolate stock should be 0 after removal.");

        // Create the CheckPastryStockCommand
        CheckPastryStockCommand command = new CheckPastryStockCommand(PASTRY_NAME);

        // Verify that a CommandException is thrown due to insufficient stock
        assertCommandFailure(command, model,
                String.format(CheckPastryStockCommand.MESSAGE_PASTRY_UNAVAILABLE, PASTRY_NAME));
    }

    @Test
    public void execute_nonExistentPastry_failure() {
        // Create the CheckPastryStockCommand for a non-existent pastry
        CheckPastryStockCommand command = new CheckPastryStockCommand(NON_EXISTENT_PASTRY);

        // Verify that a CommandException is thrown due to the pastry not being found
        assertCommandFailure(command, model, "Pastry not found in the catalogue: " + NON_EXISTENT_PASTRY);
    }

    @Test
    public void equals() {
        // Create two identical CheckPastryStockCommand instances
        CheckPastryStockCommand command1 = new CheckPastryStockCommand(PASTRY_NAME);
        CheckPastryStockCommand command2 = new CheckPastryStockCommand(PASTRY_NAME);

        // Create a different command with another pastry name
        CheckPastryStockCommand differentCommand = new CheckPastryStockCommand(NON_EXISTENT_PASTRY);

        // Verify the equals() behavior
        assertTrue(command1.equals(command2)); // Same values -> true
        assertTrue(command1.equals(command1)); // Same object -> true
        assertFalse(command1.equals(null)); // Null -> false
        assertFalse(command1.equals(new ClearCommand())); // Different type -> false
        assertFalse(command1.equals(differentCommand)); // Different pastry -> false
    }
}