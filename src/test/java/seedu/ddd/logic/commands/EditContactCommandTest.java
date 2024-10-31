package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_CONTACT_DESCRIPTOR;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EDIT_VENDOR_DESCRIPTOR;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ddd.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_EDITED_CONTACT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_VENDOR_SERVICE_1;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.ddd.logic.commands.EditContactCommand.EditVendorDescriptor;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.EditContactDescriptorBuilder;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;
import seedu.ddd.testutil.contact.ClientBuilder;
import seedu.ddd.testutil.contact.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditContactCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_contactAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact editedContact = (Contact) new ClientBuilder()
                .withId(targeContact.getId().id)
                .build();

        EditContactDescriptor editContactDescriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditContactCommand(targetIndex, editContactDescriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Client editedContact = (Client) new ClientBuilder()
                .withId(targeContact.getId().id)
                .build();

        EditContactDescriptor editContactDescriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditContactCommand(targetIndex, editContactDescriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_vendorAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Vendor editedContact = (Vendor) new VendorBuilder()
                .withId(targeContact.getId().id)
                .build();

        EditVendorDescriptor editContactDescriptor = new EditVendorDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditContactCommand(targetIndex, editContactDescriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientSomeFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact editedContact = new ClientBuilder((Client) targetContact)
                .withName(VALID_CLIENT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withTags(VALID_TAG_1)
                .build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_CLIENT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withTags(VALID_TAG_1)
                .build();
        EditCommand editCommand = new EditContactCommand(targetIndex, descriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_vendorSomeFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact editedContact = new VendorBuilder((Vendor) targetContact)
                .withName(VALID_VENDOR_NAME)
                .withPhone(VALID_VENDOR_PHONE)
                .withService(VALID_VENDOR_SERVICE_1)
                .withTags(VALID_TAG_1)
                .build();

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .withPhone(VALID_VENDOR_PHONE)
                .withService(VALID_VENDOR_SERVICE_1)
                .withTags(VALID_TAG_1)
                .build();
        EditCommand editCommand = new EditContactCommand(targetIndex, descriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact editedContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        EditCommand editCommand = new EditContactCommand(targetIndex, new EditContactDescriptor());

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        showContactAtIndex(model, targetIndex);
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact contactInFilteredList = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact editedContact = new ClientBuilder((Client) contactInFilteredList)
                .withName(VALID_VENDOR_NAME)
                .build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .build();
        EditCommand editCommand = new EditContactCommand(targetIndex, descriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byId_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());
        Contact editedContact = new ClientBuilder((Client) targetContact)
                .withName(VALID_CLIENT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withTags(VALID_TAG_1)
                .build();

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_CLIENT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withTags(VALID_TAG_1)
                .withId(targetContact.getId().id)
                .build();
        EditCommand editCommand = new EditContactCommand(null, descriptor);

        String expectedMessage = String.format(
                EditContactCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateId_failure() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(targetContact).build();
        EditCommand editCommand = new EditContactCommand(INDEX_THIRD_CONTACT, descriptor);

        assertCommandFailure(editCommand, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactList_failure() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(targetContact)
                .withId(VALID_EDITED_CONTACT_ID)
                .build();
        EditCommand editCommand = new EditContactCommand(INDEX_THIRD_CONTACT, descriptor);
        assertCommandFailure(editCommand, model, EditContactCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidParameter_failure() {
        Index targetIndex = INDEX_FIRST_CONTACT;

        EditContactDescriptor descriptor = new EditVendorDescriptorBuilder().build();
        EditCommand editCommand = new EditContactCommand(targetIndex, descriptor);
        assertCommandFailure(editCommand, model, String.format(
                EditContactCommand.MESSAGE_EDIT_INVALID_PARAMETER, PREFIX_SERVICE));
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .build();
        EditCommand editCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        showContactAtIndex(model, targetIndex);
        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .build();
        EditCommand editCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditContactCommand(INDEX_FIRST_CONTACT, VALID_EDIT_CONTACT_DESCRIPTOR);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(VALID_EDIT_CONTACT_DESCRIPTOR);
        EditCommand commandWithSameValues = new EditContactCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_SECOND_CONTACT,
                VALID_EDIT_CONTACT_DESCRIPTOR)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(INDEX_FIRST_CONTACT, VALID_EDIT_VENDOR_DESCRIPTOR)));

        EditContactCommand contactCommand = new EditContactCommand(INDEX_FIRST_CONTACT, VALID_EDIT_CONTACT_DESCRIPTOR);
        copyDescriptor = VALID_EDIT_CONTACT_DESCRIPTOR.copy();
        commandWithSameValues = new EditContactCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(contactCommand.equals(commandWithSameValues));

        EditCommand vendorCommand = new EditContactCommand(INDEX_FIRST_CONTACT, VALID_EDIT_VENDOR_DESCRIPTOR);
        copyDescriptor = VALID_EDIT_VENDOR_DESCRIPTOR.copy();
        commandWithSameValues = new EditContactCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(vendorCommand.equals(commandWithSameValues));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        EditContactCommand editCommand = new EditContactCommand(index, editContactDescriptor);
        String expected = EditContactCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editContactDescriptor + "}";
        assertEquals(expected, editCommand.toString());

        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        editCommand = new EditContactCommand(index, editVendorDescriptor);
        expected = EditContactCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editVendorDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
