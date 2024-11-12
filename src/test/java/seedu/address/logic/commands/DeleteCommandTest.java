package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.saveAddressBook();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.saveAddressBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_undoRedoSingleValidIndex_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Person personToDelete = model.getFilteredPersonList().get(lastPersonIndex.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(lastPersonIndex);

        // Update expected model
        expectedModel.deletePerson(personToDelete);
        expectedModel.saveAddressBook();

        // Execute delete command on test model
        deleteCommand.execute(model);
        assertFalse(model.getFilteredPersonList().contains(personToDelete));

        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().contains(personToDelete));

        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_REDO_SUCCESS, expectedModel);
        assertFalse(model.getFilteredPersonList().contains(personToDelete));
    }

    @Test
    public void execute_undoRedoMultipleValidIndex_success() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ArrayList<Person> deletedPersons = new ArrayList<>();

        //Perform multiple deletions, saving deleted persons and updating expected model
        for (int i = 0; i < 3; i++) {
            Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
            Person personToDelete = model.getFilteredPersonList().get(lastPersonIndex.getZeroBased());

            DeleteCommand deleteCommand = new DeleteCommand(lastPersonIndex);

            // Update expected model
            expectedModel.deletePerson(personToDelete);
            expectedModel.saveAddressBook();

            // Execute delete command on test model
            deleteCommand.execute(model);

            // Add deleted person to list and verify they are not in the test model list
            deletedPersons.add(personToDelete);
            assertFalse(model.getFilteredPersonList().contains(personToDelete));
        }

        for (int i = deletedPersons.size() - 1; i >= 0; i--) {
            Person lastDeletedPerson = deletedPersons.get(i);

            // Undo deletion
            expectedModel.undoAddressBook();
            assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
            assertTrue(model.getFilteredPersonList().contains(lastDeletedPerson));
        }

        for (Person personToRedoDelete : deletedPersons) {
            // Redo deletion
            expectedModel.redoAddressBook();
            assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_REDO_SUCCESS, expectedModel);
            assertFalse(model.getFilteredPersonList().contains(personToRedoDelete));
        }
    }

    @Test
    public void execute_undoRedoInvalidIndex_failure() {
        Index invalidIndex = Index.fromOneBased(Integer.MAX_VALUE);
        DeleteCommand deleteCommand = new DeleteCommand(invalidIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no changes made, so undo and redo should fail
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_UNDO_FAILURE);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_REDO_FAILURE);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
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
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
