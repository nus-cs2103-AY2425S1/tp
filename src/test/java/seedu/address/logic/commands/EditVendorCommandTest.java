package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VENDOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVendorAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBookWithVendors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.util.EditVendorDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Vendor;
import seedu.address.testutil.EditVendorDescriptorBuilder;
import seedu.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditVendorCommand.
 */
public class EditVendorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithVendors(), new UserPrefs());
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Vendor editedVendor = new VendorBuilder().build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredVendorList().get(0), editedVendor);
        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastVendor = Index.fromOneBased(model.getFilteredVendorList().size());
        Vendor lastVendor = (Vendor) model.getFilteredVendorList().get(indexLastVendor.getZeroBased());
        VendorBuilder vendorInList = new VendorBuilder(lastVendor);
        Vendor editedVendor = vendorInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withCompany(VALID_COMPANY_BOB).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(indexLastVendor, descriptor);
        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastVendor, editedVendor);
        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_PERSON, new EditVendorDescriptor());
        Vendor editedVendor = (Vendor) model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);
        Vendor vendorInFilteredList = (Vendor) model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        Vendor editedVendor = new VendorBuilder(vendorInFilteredList).withName(VALID_NAME_BOB).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_PERSON,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());
        String expectedMessage = String.format(EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS,
                Messages.format(editedVendor));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredVendorList().get(0), editedVendor);
        assertCommandSuccess(editVendorCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVendorUnfilteredList_failure() {
        Vendor firstVendor = (Vendor) model.getFilteredVendorList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(firstVendor).build();
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_SECOND_PERSON, descriptor);
        assertCommandFailure(editVendorCommand, model, EditVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void execute_duplicateVendorFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);
        // edit vendor in filtered list into a duplicate in address book
        Vendor vendorInList = (Vendor) model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditVendorCommand editVendorCommand = new EditVendorCommand(INDEX_FIRST_PERSON,
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
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidVendorIndexFilteredList_failure() {
        showVendorAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        EditVendorCommand editVendorCommand = new EditVendorCommand(outOfBoundIndex,
                new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB).build());
        assertCommandFailure(editVendorCommand, model, Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVendorCommand standardCommand = new EditVendorCommand(INDEX_FIRST_PERSON, DESC_VENDOR_AMY);
        // same values -> returns true
        EditVendorDescriptor copyDescriptor = new EditVendorDescriptor(DESC_VENDOR_AMY);
        EditVendorCommand commandWithSameValues = new EditVendorCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_SECOND_PERSON, DESC_VENDOR_AMY)));
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditVendorCommand(INDEX_FIRST_PERSON, DESC_VENDOR_BOB)));
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
