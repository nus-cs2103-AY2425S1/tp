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

public class UpdateMaxStockLevelCommandTest {

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
        assertThrows(NullPointerException.class, () -> new UpdateMaxStockLevelCommand(null, 1000));
    }

    @Test
    public void execute_validProductNameAndStockLevel_success() throws Exception {
        UpdateMaxStockLevelCommand command = new UpdateMaxStockLevelCommand(validProductName, 25000);
        CommandResult result = command.execute(model);

        assertEquals(String.format(UpdateMaxStockLevelCommand.MESSAGE_EDIT_PRODUCT_SUCCESS,
                Messages.format(validProduct), 25000), result.getFeedbackToUser());

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();
        assertEquals(25000, editedProduct.getMaxStockLevel());
    }

    @Test
    public void execute_negativeStockLevel_failure() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, -100);

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();
        assertNotEquals(-100, editedProduct.getMaxStockLevel());
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
        UpdateMaxStockLevelCommand command = new UpdateMaxStockLevelCommand(validProductName, 1000);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        UpdateMaxStockLevelCommand command1 = new UpdateMaxStockLevelCommand(validProductName, 1000);
        UpdateMaxStockLevelCommand command2 = new UpdateMaxStockLevelCommand(validProductName, 20000);
        UpdateMaxStockLevelCommand command3 = new UpdateMaxStockLevelCommand(new ProductName("DifferentProduct"), 1000);

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

