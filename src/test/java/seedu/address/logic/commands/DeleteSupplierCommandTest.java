package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteSupplierCommand}.
 */
public class DeleteSupplierCommandTest {

    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validSupplierNameUnfilteredList_success() {
        // Arrange
        Supplier supplierToDelete = model.getModifiedSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Name supplierName = supplierToDelete.getName();
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(supplierName);

        String expectedMessage = String.format(DeleteSupplierCommand.MESSAGE_SUCCESS,
                Messages.format(supplierToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);

        // Act & Assert
        assertCommandSuccess(deleteSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSupplierNameUnfilteredList_throwsCommandException() {
        // Arrange
        Name invalidSupplierName = new Name("Nonexistent Supplier");
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(invalidSupplierName);

        // Act & Assert
        assertCommandFailure(deleteSupplierCommand, model, DeleteSupplierCommand.MESSAGE_SUPPLIER_NOT_FOUND);
    }

    @Test
    public void execute_validSupplierNameFilteredList_success() {
        // Arrange
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Supplier supplierToDelete = model.getModifiedSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Name supplierName = supplierToDelete.getName();
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(supplierName);

        String expectedMessage = String.format(DeleteSupplierCommand.MESSAGE_SUCCESS,
                Messages.format(supplierToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);
        showNoSupplier(expectedModel);

        // Act & Assert
        assertCommandSuccess(deleteSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        // Arrange
        Name johnDoe = new Name("John Doe");
        Name janeSmith = new Name("Jane Smith");
        DeleteSupplierCommand deleteJohnCommand = new DeleteSupplierCommand(johnDoe);
        DeleteSupplierCommand deleteJaneCommand = new DeleteSupplierCommand(janeSmith);

        // Act & Assert

        // Same object -> returns true
        assertTrue(deleteJohnCommand.equals(deleteJohnCommand));

        // Same values -> returns true
        DeleteSupplierCommand deleteJohnCommandCopy = new DeleteSupplierCommand(johnDoe);
        assertTrue(deleteJohnCommand.equals(deleteJohnCommandCopy));

        // Different types -> returns false
        assertFalse(deleteJohnCommand.equals(1));

        // Null -> returns false
        assertFalse(deleteJohnCommand.equals(null));

        // Different supplier -> returns false
        assertFalse(deleteJohnCommand.equals(deleteJaneCommand));
    }

    @Test
    public void toStringMethod() {
        // Arrange
        Name supplierName = new Name("John Doe");
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(supplierName);
        String expected = DeleteSupplierCommand.class.getCanonicalName() + "{supplierName=" + supplierName + "}";

        // Act & Assert
        assertEquals(expected, deleteSupplierCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show only the supplier at the given {@code index}.
     */
    private void showSupplierAtIndex(Model model, Index index) {
        assertTrue(index.getZeroBased() < model.getModifiedSupplierList().size());

        Supplier supplier = model.getModifiedSupplierList().get(index.getZeroBased());
        model.updateFilteredSupplierList(s -> s.equals(supplier));

        assertEquals(1, model.getModifiedSupplierList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show no suppliers.
     */
    private void showNoSupplier(Model model) {
        model.updateFilteredSupplierList(s -> false);

        assertTrue(model.getModifiedSupplierList().isEmpty());
    }
}
