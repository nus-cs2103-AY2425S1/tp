package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.person.SupplierStatus;
import seedu.address.testutil.TypicalSuppliers;



public class MarkSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws InterruptedException {
        Supplier supplierToMark = TypicalSuppliers.BOB;
        Index index = Index.fromZeroBased(model.getFilteredSupplierList().indexOf(supplierToMark));
        MarkSupplierCommand markSupplierCommand = new MarkSupplierCommand(index, new SupplierStatus("inactive"));

        String expectedMessage = String.format(
                MarkSupplierCommand.MESSAGE_MARK_SUPPLIER_SUCCESS, supplierToMark, new SupplierStatus("inactive"));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Supplier updatedSupplier = new Supplier(
                supplierToMark.getName(),
                supplierToMark.getPhone(),
                supplierToMark.getEmail(),
                supplierToMark.getCompany(),
                supplierToMark.getProduct(),
                supplierToMark.getSupplierStatus());

        expectedModel.setSupplier(supplierToMark, updatedSupplier); // Update expected model
        assertCommandSuccess(markSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredSupplierList().size());
        MarkSupplierCommand markSupplierCommand = new MarkSupplierCommand(outOfBoundIndex,
                new SupplierStatus("inactive"));

        assertCommandFailure(markSupplierCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkSupplierCommand markFirstCommand = new MarkSupplierCommand(Index.fromZeroBased(1),
                new SupplierStatus("inactive"));
        MarkSupplierCommand markSecondCommand = new MarkSupplierCommand(Index.fromZeroBased(2),
                new SupplierStatus("active"));

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkSupplierCommand markFirstCommandCopy = new MarkSupplierCommand(Index.fromZeroBased(1),
                new SupplierStatus("inactive"));
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different supplier -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }
}
