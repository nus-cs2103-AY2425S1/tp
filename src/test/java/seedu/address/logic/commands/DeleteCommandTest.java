package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsDeletePredicate;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Retrieve the person to delete based on the first index of the unfiltered list
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Create the predicate to match the person based on the name
        NameContainsKeywordsDeletePredicate predicate = new NameContainsKeywordsDeletePredicate(
                Arrays.asList(personToDelete.getName().fullName.split("\\s+")));

        // Create the DeleteCommand using the predicate
        DeleteCommand deleteCommand = new DeleteCommand(predicate);

        // Expected success message after the person is deleted
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        // Create the expected model and perform the delete operation on it
        ModelManager expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        // Perform the command and verify that the expected model and actual model match
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        NameContainsKeywordsDeletePredicate predicate = new NameContainsKeywordsDeletePredicate(
                Arrays.asList("InvalidName".split("\\s+")));
        DeleteCommand deleteCommand = new DeleteCommand(predicate);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }


    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(new NameContainsKeywordsDeletePredicate(
                Arrays.asList(personToDelete.getName().fullName.split("\\s+"))));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(new NameContainsKeywordsDeletePredicate(
                Arrays.asList("first".split("\\s+"))));
        DeleteCommand deleteSecondCommand = new DeleteCommand(new NameContainsKeywordsDeletePredicate(
                Arrays.asList("second".split("\\s+"))));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(new NameContainsKeywordsDeletePredicate(
                Arrays.asList("first".split("\\s+"))));
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
        NameContainsKeywordsDeletePredicate predicate = new NameContainsKeywordsDeletePredicate(
                Arrays.asList("first".split("\\s+")));
        DeleteCommand deleteCommand = new DeleteCommand(predicate);
        String expected = DeleteCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
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
