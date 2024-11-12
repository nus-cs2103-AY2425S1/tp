package tuteez.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tuteez.logic.commands.CommandTestUtil.assertCommandFailure;
import static tuteez.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tuteez.logic.commands.CommandTestUtil.showPersonAtIndex;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static tuteez.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.model.Model;
import tuteez.model.ModelManager;
import tuteez.model.UserPrefs;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

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
    public void execute_validName_success() {
        Person personAdded = new PersonBuilder().withName(VALID_NAME_AMY).build();
        model.addPerson(personAdded);
        Name targetName = personAdded.getName();
        Person personToDelete = model.findPersonByName(targetName);
        DeleteCommand deleteCommand = new DeleteCommand(targetName);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Person personAdded = new PersonBuilder().withName(VALID_NAME_AMY).build();
        model.addPerson(personAdded);
        Name invalidName = new Name("Amyy Beee");
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME,
                invalidName);
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteLastViewedPerson_removesLastViewedPerson() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        model.updateLastViewedPerson(personToDelete);
        expectedModel.updateLastViewedPerson(personToDelete);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        expectedModel.deletePerson(personToDelete);
        expectedModel.removeLastViewedPerson();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        // Verify last viewed person was removed
        assertFalse(model.getLastViewedPerson().get().isPresent(),
                "Expected last viewed person to be removed after deleting the viewed person");
    }

    @Test
    public void execute_deleteNonLastViewedPerson_keepsLastViewedPerson() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person lastViewedPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        model.updateLastViewedPerson(lastViewedPerson);
        expectedModel.updateLastViewedPerson(lastViewedPerson);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        // Verify last viewed person was not removed
        assertTrue(model.getLastViewedPerson().get().isPresent(),
                "Expected last viewed person to be retained when deleting a different person");
        assertEquals(lastViewedPerson, model.getLastViewedPerson().get().get(),
                "Expected last viewed person to remain unchanged");
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
        DeleteCommand deleteCommandWithIndex = new DeleteCommand(targetIndex);
        String expectedWithIndex = DeleteCommand.class.getCanonicalName()
                    + "{targetIndex=" + targetIndex + ", targetName=null}";
        assertEquals(expectedWithIndex, deleteCommandWithIndex.toString());

        Name targetName = new Name("Mary Jane");
        DeleteCommand deleteCommandWithName = new DeleteCommand(targetName);
        String expectedWithName = DeleteCommand.class.getCanonicalName()
                    + "{targetIndex=null, targetName=" + targetName + "}";
        assertEquals(expectedWithName, deleteCommandWithName.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
