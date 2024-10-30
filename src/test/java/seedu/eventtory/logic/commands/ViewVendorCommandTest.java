package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.logic.commands.CommandTestUtil.showVendorAtIndex;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ViewVendorCommand}.
 */
public class ViewVendorCommandTest {

    private Model model = new ModelManager(getTypicalEventTory(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Vendor vendorToView = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        ViewVendorCommand viewCommand = new ViewVendorCommand(INDEX_FIRST_VENDOR);

        String expectedMessage = String.format(ViewVendorCommand.MESSAGE_SUCCESS, Messages.format(vendorToView));

        ModelManager expectedModel = new ModelManager(model.getEventTory(), new UserPrefs());
        expectedModel.viewVendor(vendorToView);

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        ViewVendorCommand viewCommand = new ViewVendorCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);

        Index outOfBoundIndex = INDEX_SECOND_VENDOR;
        // ensures that outOfBoundIndex is still in bounds of EventTory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventTory().getVendorList().size());

        ViewVendorCommand viewCommand = new ViewVendorCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewVendorCommand viewFirstCommand = new ViewVendorCommand(INDEX_FIRST_VENDOR);
        ViewVendorCommand viewSecondCommand = new ViewVendorCommand(INDEX_SECOND_VENDOR);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewVendorCommand viewFirstCommandCopy = new ViewVendorCommand(INDEX_FIRST_VENDOR);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
