package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonByNric;
import static seedu.address.testutil.TypicalNrics.NRIC_FIRST_PERSON;
import static seedu.address.testutil.TypicalNrics.NRIC_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

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
        Optional<Person> personWithMatchingNric = model.getFilteredPersonList().stream()
                .filter(person -> NRIC_FIRST_PERSON.equals(person.getNric()))
                .findFirst();
        Person personToDelete;

        personToDelete = personWithMatchingNric.orElse(null);

        assertNotNull(personToDelete);

        DeleteCommand deleteCommand = new DeleteCommand(NRIC_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNricUnfilteredList_throwsCommandException() {
        Nric invalidNric = new Nric("A0000000A");
        DeleteCommand deleteCommand = new DeleteCommand(invalidNric);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void execute_validNricFilteredList_success() {
        showPersonByNric(model, NRIC_FIRST_PERSON);

        Optional<Person> personWithMatchingNric = model.getFilteredPersonList().stream()
                .filter(person -> NRIC_FIRST_PERSON.equals(person.getNric()))
                .findFirst();
        Person personToDelete = personWithMatchingNric.orElse(null);

        assertNotNull(personToDelete);

        DeleteCommand deleteCommand = new DeleteCommand(NRIC_FIRST_PERSON);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNricFilteredList_throwsCommandException() {
        showPersonByNric(model, NRIC_FIRST_PERSON);

        Nric invalidNric = NRIC_SECOND_PERSON;
        // ensures that invalidNric is still in bounds of address book list
        Person invalidNricPerson = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getNric().equals(NRIC_SECOND_PERSON))
                .findFirst()
                .orElse(null);
        assertNotNull(invalidNricPerson);

        DeleteCommand deleteCommand = new DeleteCommand(invalidNric);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NO_PERSON_FOUND);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(NRIC_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(NRIC_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(NRIC_FIRST_PERSON);
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
        Nric targetNric = new Nric("S1231231D");
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
