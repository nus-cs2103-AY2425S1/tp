package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index targetIndex = INDEX_FIRST_PERSON;
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndex_cancelled() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index targetIndex = INDEX_FIRST_PERSON;
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CANCELLED, personToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(invalidIndex);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = personToDelete.getName();
        DeleteCommand deleteCommand = new DeleteCommand(targetName, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, targetName);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_validName_cancelled() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = personToDelete.getName();
        DeleteCommand deleteCommand = new DeleteCommand(targetName, false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CANCELLED, targetName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Name invalidName = new Name("Nonexistent Name");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model, String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_INDEX);
    }


    @Test
    public void execute_negativeIndex_throwsIndexOutOfBoundsException() {
        int negativeIndexValue = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Index negativeIndex = Index.fromOneBased(negativeIndexValue);
            new DeleteCommand(negativeIndex);
        });
    }
    @Test
    public void equals() {
        DeleteCommand deleteNameCommand = new DeleteCommand(new Name("Alice"));
        DeleteCommand deleteNameCommandCopy = new DeleteCommand(new Name("Alice"));
        DeleteCommand deleteIndexCommand = new DeleteCommand(Index.fromOneBased(1));

        // same object -> returns true
        assertTrue(deleteNameCommand.equals(deleteNameCommand));

        // same values -> returns true
        assertTrue(deleteNameCommand.equals(deleteNameCommandCopy));

        // different types -> returns false
        assertFalse(deleteNameCommand.equals(1));

        // null -> returns false
        assertFalse(deleteNameCommand.equals(null));

        // different names -> returns false
        assertFalse(deleteNameCommand.equals(deleteIndexCommand));

        // different index -> returns false
        assertFalse(deleteIndexCommand.equals(deleteNameCommand));
    }


    @Test
    public void toStringMethod() {
        // Testing with name
        Name targetName = new Name("John Doe");
        DeleteCommand deleteCommandByName = new DeleteCommand(targetName);
        String expectedNameString = String.format("DeleteCommand[targetName=%s]", targetName);
        assertEquals(expectedNameString, deleteCommandByName.toString());

        // Testing with index
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommandByIndex = new DeleteCommand(targetIndex);
        String expectedIndexString = String.format("DeleteCommand[targetIndex=%d]", targetIndex.getOneBased());
        assertEquals(expectedIndexString, deleteCommandByIndex.toString());
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
