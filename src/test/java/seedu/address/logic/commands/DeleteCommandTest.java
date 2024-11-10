package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
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
        List<Person> personsToDelete = List.of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personsToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personsToDelete.get(0));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        List<Person> personsToDelete = List.of(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personsToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personsToDelete.get(0));
        expectedModel.deletePerson(personsToDelete.get(1));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allUnfilteredList_success() {
        List<Person> personsToDelete = model.getFilteredPersonList();
        DeleteCommand deleteCommand = new DeleteCommand();

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personsToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Person person : personsToDelete) {
            expectedModel.deletePerson(person);
        }

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allOnEmptyList_throwsCommandException() throws CommandException {
        model.updateFilteredPersonList(p -> false);
        DeleteCommand deleteCommand = new DeleteCommand();

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_EMPTY_CONTACTS);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws CommandException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_singleValidIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        List<Person> personsToDelete = List.of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personsToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personsToDelete.get(0));
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
    public void equals_deleteAll() {
        DeleteCommand deleteAllCommand = new DeleteCommand();
        DeleteCommand deleteAllCommandCopy = new DeleteCommand();

        // Same object -> returns true
        assertTrue(deleteAllCommand.equals(deleteAllCommand));

        // Same values -> returns true
        assertTrue(deleteAllCommand.equals(deleteAllCommandCopy));

        // Different command -> returns false
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertFalse(deleteAllCommand.equals(deleteFirstCommand));

        // Different types -> returns false
        assertFalse(deleteAllCommand.equals(1));

        // Null -> returns false
        assertFalse(deleteAllCommand.equals(null));
    }

    @Test
    public void toStringMethod() throws CommandException {
        List<Index> targetIndices = List.of(Index.fromOneBased(1));
        DeleteCommand deleteCommand = new DeleteCommand(targetIndices);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=" + targetIndices
                + ", deleteAll=false}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toString_deleteAll() {
        DeleteCommand deleteAllCommand = new DeleteCommand();
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=null, deleteAll=true}";
        assertEquals(expected, deleteAllCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
