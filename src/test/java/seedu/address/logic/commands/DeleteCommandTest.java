package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithNric;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNricUnfilteredList_success() {
        Person personToDelete = ALICE;
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getNric());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPatientlinkdeletion() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToDelete = ALICE;

        showPersonWithNric(expectedModel, personToDelete.getNric());
        expectedModel.addLink(ALICE, BENSON);
        assertTrue(expectedModel.hasLink(ALICE, BENSON));
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);
        assertFalse(expectedModel.hasLink(ALICE, BENSON));

    }

    @Test
    public void execute_validCaregiverlinkdeletion() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToDelete = BENSON;

        showPersonWithNric(expectedModel, personToDelete.getNric());
        expectedModel.addLink(ALICE, BENSON);
        assertTrue(expectedModel.hasLink(ALICE, BENSON));
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);
        assertFalse(expectedModel.hasLink(ALICE, BENSON));

    }

    @Test
    public void execute_validNricFilteredList_success() {
        Person personToDelete = ALICE;
        showPersonWithNric(model, personToDelete.getNric());

        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getNric());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(ALICE.getNric());
        DeleteCommand deleteSecondCommand = new DeleteCommand(BENSON.getNric());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(ALICE.getNric());
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
        Nric targetNric = ALICE.getNric();
        DeleteCommand deleteCommand = new DeleteCommand(targetNric);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetNric=" + targetNric + "}";
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
