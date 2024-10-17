package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVendorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.address.testutil.TypicalVendorsEventsCombined.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteVendorCommand}.
 */
public class DeleteVendorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Vendor vendorToDelete = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        DeleteVendorCommand deleteCommand = new DeleteVendorCommand(INDEX_FIRST_VENDOR);

        String expectedMessage = String.format(DeleteVendorCommand.MESSAGE_DELETE_VENDOR_SUCCESS,
                Messages.format(vendorToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVendor(vendorToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        DeleteVendorCommand deleteCommand = new DeleteVendorCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);

        Vendor vendorToDelete = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        DeleteVendorCommand deleteCommand = new DeleteVendorCommand(INDEX_FIRST_VENDOR);

        String expectedMessage = String.format(DeleteVendorCommand.MESSAGE_DELETE_VENDOR_SUCCESS,
                Messages.format(vendorToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteVendor(vendorToDelete);
        showNoVendor(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);

        Index outOfBoundIndex = INDEX_SECOND_VENDOR;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getVendorList().size());

        DeleteVendorCommand deleteCommand = new DeleteVendorCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_associatedVendor_throwsCommandException() {
        Vendor vendorToDelete = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        Event eventToAssignTo = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        model.assignVendorToEvent(vendorToDelete, eventToAssignTo);

        DeleteVendorCommand deleteCommand = new DeleteVendorCommand(INDEX_FIRST_VENDOR);

        String expectedMessage = String.format(DeleteVendorCommand.DELETE_VENDOR_FAILED_DUE_TO_EXISTING_ASSOCIATIONS,
                Messages.format(vendorToDelete));

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteVendorCommand deleteFirstCommand = new DeleteVendorCommand(INDEX_FIRST_VENDOR);
        DeleteVendorCommand deleteSecondCommand = new DeleteVendorCommand(INDEX_SECOND_VENDOR);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteVendorCommand deleteFirstCommandCopy = new DeleteVendorCommand(INDEX_FIRST_VENDOR);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different vendor -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoVendor(Model model) {
        model.updateFilteredVendorList(p -> false);

        assertTrue(model.getFilteredVendorList().isEmpty());
    }
}
