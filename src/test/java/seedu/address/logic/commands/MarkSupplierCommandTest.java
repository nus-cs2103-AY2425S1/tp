package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.testutil.SupplierBuilder;


public class MarkSupplierCommandTest {
    private static final String SUPPLIER_STATUS_STUB = "active";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Supplier firstSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier editedSupplier = new SupplierBuilder(firstSupplier).withStatus(SUPPLIER_STATUS_STUB).build();
        MarkSupplierCommand markCommand = new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(editedSupplier.getStatus().status));
        String expectedMessage = String.format(MarkSupplierCommand.MESSAGE_MARK_SUPPLIER_SUCCESS,
                INDEX_FIRST_SUPPLIER.getOneBased(), editedSupplier.getStatus().status);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSupplier(firstSupplier, editedSupplier);
        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Supplier firstSupplier = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());
        Supplier editedSupplier = new SupplierBuilder(
                model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased()))
                .withStatus(SUPPLIER_STATUS_STUB).build();
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(editedSupplier.getStatus().status));
        String expectedMessage = String.format(MarkSupplierCommand.MESSAGE_MARK_SUPPLIER_SUCCESS,
                INDEX_FIRST_SUPPLIER.getOneBased(), editedSupplier.getStatus().status);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSupplier(firstSupplier, editedSupplier);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(outOfBoundIndex,
                new SupplierStatus(SUPPLIER_STATUS_STUB));
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSupplierIndexFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getSupplierList().size());
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(outOfBoundIndex,
                new SupplierStatus(SUPPLIER_STATUS_STUB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final MarkSupplierCommand standardCommand = new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(SUPPLIER_STATUS_STUB));

        // same values -> returns true
        MarkSupplierCommand commandWithSameValues = new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(SUPPLIER_STATUS_STUB));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MarkSupplierCommand(INDEX_SECOND_SUPPLIER,
                new SupplierStatus(VALID_STATUS_AMY))));

        // different status -> returns false
        assertFalse(standardCommand.equals(new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(VALID_STATUS_BOB))));
    }
}
