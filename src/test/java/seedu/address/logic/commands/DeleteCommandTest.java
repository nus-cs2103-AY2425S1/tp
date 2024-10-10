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

    // Test for valid index-based deletion in an unfiltered list
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // Test for valid name-based deletion in an unfiltered list
    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String nameToDelete = personToDelete.getName().fullName;
        DeleteCommand deleteCommand = new DeleteCommand(nameToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // Test for invalid name-based deletion in an unfiltered list
    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        String invalidName = "Non Existent Person";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model, String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }

    // Test for valid index-based deletion in a filtered list
    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // Test for valid name-based deletion in a filtered list
    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String nameToDelete = personToDelete.getName().fullName;
        DeleteCommand deleteCommand = new DeleteCommand(nameToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    // Test for invalid name-based deletion in a filtered list
    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        String invalidName = "Non Existent Person";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertCommandFailure(deleteCommand, model, String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, invalidName));
    }

    // Equality tests for both index-based and name-based commands
    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteNameCommand = new DeleteCommand("John Doe");

        // Same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // Same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // Different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // Different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // Null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // Different name -> returns false
        assertFalse(deleteFirstCommand.equals(deleteNameCommand));

        // Same name -> returns true
        DeleteCommand deleteNameCommandCopy = new DeleteCommand("John Doe");
        assertTrue(deleteNameCommand.equals(deleteNameCommandCopy));
    }

    @Test
    public void toString_indexBasedDeletion() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", targetName=null}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toString_nameBasedDeletion() {
        String targetName = "John Doe";
        DeleteCommand deleteCommand = new DeleteCommand(targetName);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=null, targetName=" + targetName + "}";
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
