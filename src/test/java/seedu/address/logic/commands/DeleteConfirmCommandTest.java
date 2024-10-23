package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteConfirmCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        IdentityNumber identityNumber = personToDelete.getIdentityNumber();
        DeleteConfirmCommand deleteConfirmCommand = new DeleteConfirmCommand(identityNumber);

        String expectedMessage = String.format(DeleteConfirmCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteConfirmCommand, model, expectedMessage, expectedModel);
    }

    // TODO: Add test for invalid identity number once message constraints are updated, change to invalid IdentityNumber
    //    @Test
    //    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
    //        IdentityNumber invalidIdentityNumber = new IdentityNumber("INVALID_ID");
    //        DeleteCommand deleteCommand = new DeleteCommand(invalidIdentityNumber);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        IdentityNumber identityNumber = personToDelete.getIdentityNumber();
        DeleteConfirmCommand deleteConfirmCommand = new DeleteConfirmCommand(identityNumber);

        String expectedMessage = String.format(DeleteConfirmCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteConfirmCommand, model, expectedMessage, expectedModel);
    }

    // TODO: Add test for invalid identity number once message constraints are updated
    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
    //
    //        // Simulate an invalid IdentityNumber based on out-of-bound index
    //        IdentityNumber invalidIdentityNumber = new IdentityNumber("INVALID_ID");
    //        DeleteCommand deleteCommand = new DeleteCommand(invalidIdentityNumber);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        IdentityNumber firstIdentityNumber = firstPerson.getIdentityNumber();
        IdentityNumber secondIdentityNumber = secondPerson.getIdentityNumber();
        DeleteConfirmCommand deleteFirstCommand = new DeleteConfirmCommand(firstIdentityNumber);
        DeleteConfirmCommand deleteSecondCommand = new DeleteConfirmCommand(secondIdentityNumber);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteConfirmCommand deleteFirstCommandCopy = new DeleteConfirmCommand(firstIdentityNumber);
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
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        IdentityNumber identityNumber = person.getIdentityNumber();
        DeleteConfirmCommand deleteConfirmCommand = new DeleteConfirmCommand(identityNumber);
        String expected = DeleteConfirmCommand.class.getCanonicalName() + "{identityNumber=" + identityNumber + "}";
        assertEquals(expected, deleteConfirmCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
