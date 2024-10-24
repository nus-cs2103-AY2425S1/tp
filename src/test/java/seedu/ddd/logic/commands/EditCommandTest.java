package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_CLIENT_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_CONTACT_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_VENDOR_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_SERVICE_BOB;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ddd.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.EditCommand.EditClientDescriptor;
import seedu.ddd.logic.commands.EditCommand.EditContactDescriptor;
import seedu.ddd.logic.commands.EditCommand.EditVendorDescriptor;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.ClientBuilder;
import seedu.ddd.testutil.EditClientDescriptorBuilder;
import seedu.ddd.testutil.EditContactDescriptorBuilder;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;
import seedu.ddd.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_contactAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        Contact editedContact = (Contact) new ClientBuilder().withId(targeContact.getId().contactId).build();
        EditContactDescriptor editContactDescriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditCommand(targetIndex, editContactDescriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        Client editedContact = (Client) new ClientBuilder().withId(targeContact.getId().contactId).build();
        EditClientDescriptor editClientDescriptor = new EditClientDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditCommand(targetIndex, editClientDescriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_vendorAllFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        Contact targeContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        Vendor editedContact = (Vendor) new VendorBuilder().withId(targeContact.getId().contactId).build();
        EditVendorDescriptor editClientDescriptor = new EditVendorDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditCommand(targetIndex, editClientDescriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targeContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientSomeFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        ClientBuilder clientBuilder = new ClientBuilder((Client) targetContact);
        Contact editedContact = clientBuilder.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withDate(VALID_DATE_AMY).withTags(VALID_TAG_HUSBAND).build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(targetIndex, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_vendorSomeFieldsSpecifiedUnfilteredList_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        VendorBuilder bobInList = new VendorBuilder((Vendor) targetContact);
        Contact editedContact = bobInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withService(VALID_SERVICE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withService(VALID_SERVICE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(targetIndex, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT, new EditContactDescriptor());
        Contact editedContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactInFilteredList = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact editedContact = new ClientBuilder((Client) contactInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
                new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_byId_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        Contact targetContact = model.getFilteredContactList().get(targetIndex.getZeroBased());

        ClientBuilder clientBuilder = new ClientBuilder((Client) targetContact);
        Contact editedContact = clientBuilder.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withDate(VALID_DATE_AMY).withTags(VALID_TAG_HUSBAND).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_HUSBAND).withId(targetContact.getId().contactId).build();
        EditCommand editCommand = new EditCommand(null, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(targetContact, editedContact);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateContactList_failure() {
        Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        EditContactDescriptor descriptor = new EditClientDescriptorBuilder((Client) firstContact)
                .withId(VALID_ID_AMY).build();
        EditCommand editCommand = new EditCommand(INDEX_THIRD_CONTACT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateId_failure() {
        Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        EditContactDescriptor descriptor = new EditClientDescriptorBuilder((Client) firstContact).build();
        EditCommand editCommand = new EditCommand(INDEX_THIRD_CONTACT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidParameter_failure() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT, new EditVendorDescriptorBuilder().build());
        assertCommandFailure(editCommand, model, String.format(
                EditCommand.MESSAGE_EDIT_INVALID_PARAMETER, PREFIX_SERVICE));

        editCommand = new EditCommand(INDEX_SECOND_CONTACT, new EditClientDescriptorBuilder().build());
        assertCommandFailure(editCommand, model, String.format(
                EditCommand.MESSAGE_EDIT_INVALID_PARAMETER, PREFIX_DATE));
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        EditContactDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);
        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CONTACT, DESC_CONTACT_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_CONTACT_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CONTACT, DESC_CONTACT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CONTACT, DESC_VENDOR_BOB)));

        EditCommand clientCommand = new EditCommand(INDEX_FIRST_CONTACT, DESC_CLIENT_AMY);
        copyDescriptor = DESC_CLIENT_AMY.copy();
        commandWithSameValues = new EditCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(clientCommand.equals(commandWithSameValues));

        EditCommand vendorCommand = new EditCommand(INDEX_FIRST_CONTACT, DESC_VENDOR_BOB);
        copyDescriptor = DESC_VENDOR_BOB.copy();
        commandWithSameValues = new EditCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(vendorCommand.equals(commandWithSameValues));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditClientDescriptor editContactDescriptor = new EditClientDescriptor();
        EditCommand editCommand = new EditCommand(index, editContactDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editContactDescriptor + "}";
        assertEquals(expected, editCommand.toString());

        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        editCommand = new EditCommand(index, editClientDescriptor);
        expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editClientDescriptor + "}";
        assertEquals(expected, editCommand.toString());

        EditVendorDescriptor editVendorDescriptor = new EditVendorDescriptor();
        editCommand = new EditCommand(index, editVendorDescriptor);
        expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editVendorDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
