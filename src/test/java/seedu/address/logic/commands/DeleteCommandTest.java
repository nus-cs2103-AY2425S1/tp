package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonOfId;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.ID_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

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
    public void execute_validIdUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getId());

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIdUnfilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int outOfBoundId = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1).getId() + 2;
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundId);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_DELETE_PERSON_FAILURE);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonOfId(model, model.getFilteredPersonList().get(INDEX_FIRST_PERSON).getId());
        Person personToDelete = model.getFilteredPersonById(model.getFilteredPersonList(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON).getId());
        DeleteCommand deleteCommand = new DeleteCommand(model
                .getFilteredPersonList().get(INDEX_FIRST_PERSON).getId());

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(ID_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(ID_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(ID_FIRST_PERSON);
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
        int targetId = 1;
        DeleteCommand deleteCommand = new DeleteCommand(targetId);
        String expected = DeleteCommand.class.getCanonicalName() + "{person Id=" + targetId + "}";
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
