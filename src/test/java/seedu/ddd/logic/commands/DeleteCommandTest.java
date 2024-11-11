package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ddd.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.Messages;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteDependentClientAfterEvent_success() {
        model.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        expectedModel.deleteContact(expectedModel.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased()));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        DeleteCommand deleteEvent = new DeleteCommand(INDEX_FIRST_EVENT);

        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        expectedModel.deleteEvent(expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        String expectedEventMessage = String.format(Messages.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));
        assertCommandSuccess(deleteEvent, model, expectedEventMessage, expectedModel);

        model.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        DeleteCommand deleteSecondClient = new DeleteCommand(INDEX_SECOND_CONTACT);

        expectedModel.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        Contact dependentClientToDelete = model.getFilteredContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        expectedModel.deleteContact(expectedModel.getFilteredContactList().get(INDEX_SECOND_CONTACT.getZeroBased()));
        String expectedDeleteSecondClientMessage = String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(dependentClientToDelete));
        assertCommandSuccess(deleteSecondClient, model, expectedDeleteSecondClientMessage, expectedModel);
    }

    @Test
    public void execute_deleteDependentClientBeforeEvent_throwsCommandException() {
        model.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteFirstClient = new DeleteCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        expectedModel.deleteContact(expectedModel.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased()));
        assertCommandSuccess(deleteFirstClient, model, expectedMessage, expectedModel);

        DeleteCommand deleteSecondClient = new DeleteCommand(INDEX_SECOND_CONTACT);
        String expectedExceptionMessage = String.format(Messages.MESSAGE_DEPENDENT_EVENT, WEDDING_A.getEventId());
        assertCommandFailure(deleteSecondClient, model, expectedExceptionMessage);
    }

    @Test
    public synchronized void execute_deleteDependentVendorBeforeEvent_throwsCommandException() {
        model.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);
        ObservableList<Contact> listToUse = model.getFilteredContactList();
        Contact contactToDelete = listToUse.get(INDEX_SECOND_CONTACT.getZeroBased());
        DeleteCommand deleteFirstVendor = new DeleteCommand(INDEX_SECOND_CONTACT);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));
        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredContactList(Model.PREDICATE_SHOW_ALL_CONTACTS);

        ArrayList<Contact> contactsList = new ArrayList<>(expectedModel.getFilteredContactList());
        expectedModel.deleteContact(contactsList.get(INDEX_SECOND_CONTACT.getZeroBased()));
        assertCommandSuccess(deleteFirstVendor, model, expectedMessage, expectedModel);

        DeleteCommand deleteSecondVendor = new DeleteCommand(INDEX_THIRD_CONTACT);
        String expectedExceptionMessage = String.format(Messages.MESSAGE_DEPENDENT_EVENT, WEDDING_A.getEventId());
        assertCommandFailure(deleteSecondVendor, model, expectedExceptionMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_DISPLAYED_INDEX_TOO_LARGE);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_DISPLAYED_INDEX_TOO_LARGE);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CONTACT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CONTACT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);
        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
