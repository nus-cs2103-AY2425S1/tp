package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.ddd.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.commands.EditCommand.EditContactDescriptor;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // TODO: fix this
        // Client editedContact = new ClientBuilder().build();
        // EditContactDescriptor descriptor = new EditClientDescriptorBuilder(editedContact).build();
        // EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT, descriptor);

        // String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
        // Messages.format(editedContact));

        // Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        // assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // TODO: fix this
        // Index indexLastContact = Index.fromOneBased(model.getFilteredContactList().size());
        // Contact lastContact = model.getFilteredContactList().get(indexLastContact.getZeroBased());

        // ClientBuilder personInList = new ClientBuilder((Client) lastContact);
        // Contact editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        //         .withTags(VALID_TAG_HUSBAND).build();

        // EditContactDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB)
        //         .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        // EditCommand editCommand = new EditCommand(indexLastContact, descriptor);

        // String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
        // Messages.format(editedPerson));

        // Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // expectedModel.setContact(lastContact, editedPerson);

        // assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // TODO: fix this
        // EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT, new EditContactDescriptor());
        // Contact editedPerson = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());

        // String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
        // Messages.format(editedPerson));

        // Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // TODO: fix this
        // showPersonAtIndex(model, INDEX_FIRST_CONTACT);

        // Contact contactInFilteredList = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        // Contact editedPerson = new ClientBuilder((Client) contactInFilteredList).withName(VALID_NAME_BOB).build();
        // EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
        //         new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        // String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
        // Messages.format(editedPerson));

        // Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        // expectedModel.setContact(model.getFilteredContactList().get(0), editedPerson);

        // assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateContactUnfilteredList_failure() {
        // TODO: fix this
        // Contact firstContact = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        // EditContactDescriptor descriptor = new EditClientDescriptorBuilder((Client) firstContact).build();
        // EditCommand editCommand = new EditCommand(INDEX_SECOND_CONTACT, descriptor);

        // assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredList_failure() {
        // TODO: fix this
        // showPersonAtIndex(model, INDEX_FIRST_CONTACT);

        // // edit person in filtered list into a duplicate in address book
        // Contact personInList = model.getAddressBook().getContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        // EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
        //         new EditClientDescriptorBuilder((Client) personInList).build());

        // assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        // TODO: fix this
        // Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        // EditContactDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        // EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        // TODO: fix this
        // showPersonAtIndex(model, INDEX_FIRST_CONTACT);
        // Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // // ensures that outOfBoundIndex is still in bounds of address book list
        // assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        // EditCommand editCommand = new EditCommand(outOfBoundIndex,
        //         new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        // assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CONTACT, DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CONTACT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CONTACT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditContactDescriptor editPersonDescriptor = new EditContactDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
