package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.product.exceptions.InvalidMaxStockLevelException;
import seedu.address.model.product.exceptions.InvalidMinStockLevelException;
import seedu.address.model.product.exceptions.InvalidStockLevelException;
import seedu.address.model.product.exceptions.StockLevelOutOfBoundsException;
import seedu.address.model.supplier.Name;

/**
 * Unit tests for SetThresholdCommand.
 */
public class SetThresholdCommandTest {

    private Model model;
    private ProductName validProductName;
    private Product validProduct;
    private Name validSupplierName = new Name("TestSupplier");

    @BeforeEach
    public void setUp() throws InvalidStockLevelException,
            InvalidMinStockLevelException, InvalidMaxStockLevelException, StockLevelOutOfBoundsException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        validProductName = new ProductName("TestProduct");
        StockLevel initialStockLevel = new StockLevel(50, 10, 100);
        validProduct = new Product(validProductName, initialStockLevel);
        validProduct.setSupplierName(validSupplierName); // Assuming setSupplierName accepts a String
        model.addProduct(validProduct);
    }

    @Test
    public void constructor_nullProductName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetThresholdCommand(null, 20, 80));
    }

    @Test
    public void execute_validProductNameAndThresholds_success() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, 20, 80);
        CommandResult result = command.execute(model);

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();

        StockLevel expectedStockLevel = new StockLevel(50, 20, 80);
        Product expectedProduct = new Product(validProductName, expectedStockLevel);
        expectedProduct.setSupplierName(validProduct.getSupplierName());

        assertEquals(String.format(SetThresholdCommand.MESSAGE_SET_THRESHOLD_SUCCESS, editedProduct),
                result.getFeedbackToUser());
        assertEquals(expectedProduct, editedProduct);
    }

    @Test
    public void execute_minStockLevelOnly_success() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, 20, null);
        CommandResult result = command.execute(model);

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();

        StockLevel expectedStockLevel = new StockLevel(50, 20, 100);
        Product expectedProduct = new Product(validProductName, expectedStockLevel);
        expectedProduct.setSupplierName(validProduct.getSupplierName());

        assertEquals(String.format(SetThresholdCommand.MESSAGE_SET_THRESHOLD_SUCCESS, editedProduct),
                result.getFeedbackToUser());
        assertEquals(expectedProduct, editedProduct);
    }

    @Test
    public void execute_maxStockLevelOnly_success() throws Exception {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, null, 80);
        CommandResult result = command.execute(model);

        Product editedProduct = model.getFilteredProductList().stream()
                .filter(p -> p.getName().equals(validProductName))
                .findFirst()
                .get();

        StockLevel expectedStockLevel = new StockLevel(50, 10, 80);
        Product expectedProduct = new Product(validProductName, expectedStockLevel);
        expectedProduct.setSupplierName(validProduct.getSupplierName());

        assertEquals(String.format(SetThresholdCommand.MESSAGE_SET_THRESHOLD_SUCCESS, editedProduct),
                result.getFeedbackToUser());
        assertEquals(expectedProduct, editedProduct);
    }

    @Test
    public void execute_invalidThresholds_throwsCommandException() {
        // minStockLevel greater than maxStockLevel
        SetThresholdCommand command = new SetThresholdCommand(validProductName, 100, 50);

        assertThrows(CommandException.class, "Maximum stock level cannot be less than minimum stock level.", (
            ) -> command.execute(model));
    }

    @Test
    public void execute_nonexistentProduct_throwsCommandException() {
        ProductName nonExistentProduct = new ProductName("NonExistentProduct");
        SetThresholdCommand command = new SetThresholdCommand(nonExistentProduct, 20, 80);

        assertThrows(CommandException.class, String.format(
                SetThresholdCommand.MESSAGE_PRODUCT_NOT_FOUND, nonExistentProduct), () -> command.execute(model));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        SetThresholdCommand command = new SetThresholdCommand(validProductName, 20, 80);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        SetThresholdCommand command1 = new SetThresholdCommand(validProductName, 20, 80);
        SetThresholdCommand command2 = new SetThresholdCommand(validProductName, 20, 80);
        SetThresholdCommand command3 = new SetThresholdCommand(validProductName, 30, 90);
        SetThresholdCommand command4 = new SetThresholdCommand(new ProductName("DifferentProduct"), 20, 80);
        SetThresholdCommand command5 = new SetThresholdCommand(validProductName, null, 80);
        SetThresholdCommand command6 = new SetThresholdCommand(validProductName, 20, null);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different thresholds with same product name -> returns false
        assertFalse(command1.equals(command3));

        // different product name -> returns false
        assertFalse(command1.equals(command4));

        // different thresholds (one null) -> returns false
        assertFalse(command1.equals(command5));
        assertFalse(command1.equals(command6));

        // null -> returns false
        assertFalse(command1.equals(null));
    }
}
