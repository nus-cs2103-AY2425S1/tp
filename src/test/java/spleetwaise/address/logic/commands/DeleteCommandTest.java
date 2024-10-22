package spleetwaise.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.UserPrefs;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalIndexes;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private AddressBookModel addressBookModel = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private TransactionBookModel transactionBookModel = new TransactionBookModelManager();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = addressBookModel.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)
        );

        AddressBookModelManager expectedModel = new AddressBookModelManager(addressBookModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, addressBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(addressBookModel.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, addressBookModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showPersonAtIndex(addressBookModel, TypicalIndexes.INDEX_FIRST_PERSON);

        Person personToDelete = addressBookModel.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)
        );

        AddressBookModel expectedModel = new AddressBookModelManager(addressBookModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, addressBookModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showPersonAtIndex(addressBookModel, TypicalIndexes.INDEX_FIRST_PERSON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < addressBookModel.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, addressBookModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON);
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
    private void showNoPerson(AddressBookModel model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
