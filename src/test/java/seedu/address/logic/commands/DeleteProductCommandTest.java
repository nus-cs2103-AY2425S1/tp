package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PRODUCT;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteProductCommand}.
 */
public class DeleteProductCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validProductNameUnfilteredList_success() {
        Product productToDelete = model.getModifiedProductList().get(INDEX_FIRST_PRODUCT.getZeroBased());
        ProductName productName = productToDelete.getName();
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productName);

        String expectedMessage = String.format(DeleteProductCommand.MESSAGE_SUCCESS,
                Messages.format(productToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteProduct(productToDelete);

        assertCommandSuccess(deleteProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidProductNameUnfilteredList_throwsCommandException() {
        ProductName invalidProductName = new ProductName("Nonexistent Product");
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(invalidProductName);

        assertCommandFailure(deleteProductCommand, model, DeleteProductCommand.MESSAGE_PRODUCT_NOT_FOUND);
    }

    @Test
    public void equals() {
        ProductName apple = new ProductName("Apple");
        ProductName orange = new ProductName("Orange");
        DeleteProductCommand deleteAppleCommand = new DeleteProductCommand(apple);
        DeleteProductCommand deleteOrangeCommand = new DeleteProductCommand(orange);

        // same object -> returns true
        assertTrue(deleteAppleCommand.equals(deleteAppleCommand));

        // same values -> returns true
        DeleteProductCommand deleteAppleCommandCopy = new DeleteProductCommand(apple);
        assertTrue(deleteAppleCommand.equals(deleteAppleCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppleCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppleCommand.equals(null));

        // different product -> returns false
        assertFalse(deleteAppleCommand.equals(deleteOrangeCommand));
    }

    @Test
    public void toStringMethod() {
        ProductName productName = new ProductName("Apple");
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productName);
        String expected = DeleteProductCommand.class.getCanonicalName() + "{productName=" + productName + "}";
        assertEquals(expected, deleteProductCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show only the product with the given {@code productName}.
     */
    private void showProductWithName(Model model, ProductName productName) {
        model.updateFilteredProductList(p -> p.getName().equals(productName));

        assertEquals(1, model.getModifiedProductList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show no products.
     */
    private void showNoProduct(Model model) {
        model.updateFilteredProductList(p -> false);

        assertTrue(model.getModifiedProductList().isEmpty());
    }
}


