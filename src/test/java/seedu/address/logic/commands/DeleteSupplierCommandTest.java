package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;


public class DeleteSupplierCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Supplier supplierToDelete = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);

        String expectedMessage = String.format(DeleteSupplierCommand.MESSAGE_DELETE_SUPPLIER_SUCCESS,
                INDEX_FIRST_SUPPLIER.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteSupplier(supplierToDelete);

        assertCommandSuccess(deleteSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        DeleteSupplierCommand deleteSupplierCommand = new DeleteSupplierCommand(outOfBoundIndex);

        assertCommandFailure(deleteSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteSupplierCommand deleteFirstCommand = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);
        DeleteSupplierCommand deleteSecondCommand = new DeleteSupplierCommand(INDEX_SECOND_SUPPLIER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteSupplierCommand deleteFirstCommandCopy = new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delivery -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
