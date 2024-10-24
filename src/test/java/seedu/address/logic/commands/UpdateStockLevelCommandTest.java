package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

public class UpdateStockLevelCommandTest {

    private Model model;
    private ProductName validProductName;
    private Product validProduct;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        validProductName = new ProductName("TestProduct");
        validProduct = new Product(validProductName);
        model.addProduct(validProduct);
    }

    @Test
    public void constructor_nullProductName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateStockLevelCommand(null, 1000));
    }

    @Test
    public void execute_validProductNameAndStockLevel_success() throws Exception {
        UpdateStockLevelCommand command = new UpdateStockLevelCommand(validProductName, 1000);
        CommandResult result = command.execute(model);

        assertEquals(String.format(UpdateStockLevelCommand.MESSAGE_EDIT_PRODUCT_SUCCESS,
                Messages.format(validProduct), 1000), result.getFeedbackToUser());

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();
        assertEquals(1000, editedProduct.getStockLevel());
    }

    @Test
    public void execute_negativeStockLevel_failure() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, -100);

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();
        assertNotEquals(-100, editedProduct.getStockLevel());
    }

    @Test
    public void execute_zeroStockLevel_success() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, 0);
        CommandResult result = command.execute(model);

        assertEquals(String.format(UpdateStockLevelCommand.MESSAGE_EDIT_PRODUCT_SUCCESS,
                Messages.format(validProduct), 0), result.getFeedbackToUser());

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();
        assertEquals(0, editedProduct.getStockLevel());
    }

    @Test
    public void execute_nonexistentProduct_throwsCommandException() {
        ProductName nonExistentProduct = new ProductName("NonExistentProduct");
        UpdateStockLevelCommand command = new UpdateStockLevelCommand(nonExistentProduct, 1000);

        assertThrows(CommandException.class, String.format(
                SetThresholdCommand.MESSAGE_PRODUCT_NOT_FOUND, nonExistentProduct), () -> command.execute(model));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        UpdateStockLevelCommand command = new UpdateStockLevelCommand(validProductName, 1000);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        UpdateStockLevelCommand command1 = new UpdateStockLevelCommand(validProductName, 1000);
        UpdateStockLevelCommand command2 = new UpdateStockLevelCommand(validProductName, 20000);
        UpdateStockLevelCommand command3 = new UpdateStockLevelCommand(new ProductName("DifferentProduct"), 1000);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // different stock level with same product name -> returns false
        assertFalse(command1.equals(command2));

        // different stock level with different product name -> returns false
        assertFalse(command2.equals(command3));

        // different product name -> returns false
        assertFalse(command1.equals(command3));

        // null -> returns false
        assertFalse(command1.equals(null));
    }
}
