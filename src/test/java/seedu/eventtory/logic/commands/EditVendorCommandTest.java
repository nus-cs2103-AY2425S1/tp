package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.eventtory.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.logic.commands.CommandTestUtil.showVendorAtIndex;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.EditVendorDescriptorBuilder;
import seedu.eventtory.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditVendorCommand.
 */
public class EditVendorCommandTest {

    private Model model = new ModelManager(getTypicalEventTory(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Vendor editedVendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, descriptor);

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new EventTory(model.getEventTory()), new UserPrefs());
        expectedModel.setVendor(model.getFilteredVendorList().get(0), editedVendor);

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastVendor = Index.fromOneBased(model.getFilteredVendorList().size());
        Vendor lastVendor = model.getFilteredVendorList().get(indexLastVendor.getZeroBased());

        VendorBuilder vendorInList = new VendorBuilder(lastVendor);
        Vendor editedVendor = vendorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(indexLastVendor, descriptor);

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new EventTory(model.getEventTory()), new UserPrefs());
        expectedModel.setVendor(lastVendor, editedVendor);

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, new EditVendorDescriptor());
        Vendor editedVendor = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new EventTory(model.getEventTory()), new UserPrefs());

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);

        Vendor vendorInFilteredList = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        Vendor editedVendor = new VendorBuilder(vendorInFilteredList).withName(VALID_NAME_BOB).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));

        Model expectedModel = new ModelManager(new EventTory(model.getEventTory()), new UserPrefs());
        expectedModel.setVendor(model.getFilteredVendorList().get(0), editedVendor);

        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVendorUnfilteredList_failure() {
        Vendor firstVendor = model.getFilteredVendorList().get(INDEX_FIRST_VENDOR.getZeroBased());
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(firstVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_SECOND_VENDOR, descriptor);

        assertCommandFailure(editVendorCommand, model, EditVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_duplicateVendorFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);

        // edit vendor in filtered list into a duplicate in eventtory book
        Vendor vendorInList = model.getEventTory().getVendorList().get(INDEX_SECOND_VENDOR.getZeroBased());
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_VENDOR,
                new EditVendorDescriptorBuilder(vendorInList).build());

        assertCommandFailure(editVendorCommand, model, EditVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_invalidVendorIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVendorList().size() + 1);
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editVendorCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of EventTory list.
     */
    @Test
    public void execute_invalidVendorIndexFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);
        Index outOfBoundIndex = INDEX_SECOND_VENDOR;
        // ensures that outOfBoundIndex is still in bounds of EventTory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventTory().getVendorList().size());

        EditVendorCommand editVendorCommand = new EditVendorCommand(outOfBoundIndex,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editVendorCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVendorCommand standardCommand = new EditVendorCommand(INDEX_FIRST_VENDOR, DESC_AMY);

        // same values -> returns true
        EditVendorDescriptor copyDescriptor = new EditVendorDescriptor(DESC_AMY);
        EditVendorCommand commandWithSameValues = new EditVendorCommand(INDEX_FIRST_VENDOR, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_SECOND_VENDOR, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_FIRST_VENDOR, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        EditVendorCommand editVendorCommand = new EditVendorCommand(index, editVendorDescriptor);
        String expected = EditVendorCommand.class.getCanonicalName() + "{index=" + index + ", editVendorDescriptor="
                + editVendorDescriptor + "}";
        assertEquals(expected, editVendorCommand.toString());
    }
}
