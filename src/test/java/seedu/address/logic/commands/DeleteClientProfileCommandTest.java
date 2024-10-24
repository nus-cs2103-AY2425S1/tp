package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonWithName;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalNames;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteClientProfileCommand}.
 */
public class DeleteClientProfileCommandTest {

    private static final Name DO_NOT_EXIST_NAME = new Name("DO NOT EXIST NAME");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());


    @Test
    public void execute_validNameUnfilteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        Person personToDelete = model.getPersonByName(typicalNames.get(randomIndex));
        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(personToDelete.getName());

        String expectedMessage = String.format(DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getPhone(), personToDelete.getEmail());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(DO_NOT_EXIST_NAME);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 1);
        showPersonWithName(model, typicalNames.get(randomIndex));

        Person personToDelete = model.getPersonByName(typicalNames.get(randomIndex));
        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(personToDelete.getName());

        String expectedMessage = String.format(DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getPhone(), personToDelete.getEmail());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Random random = new Random();
        List<Name> typicalNames = getTypicalNames();
        int randomIndex = random.nextInt(typicalNames.size() - 2);
        showPersonWithName(model, typicalNames.get(randomIndex));

        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(typicalNames
                                                                            .get(randomIndex + 1));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void equals() {
        DeleteClientProfileCommand deleteFirstCommand = new DeleteClientProfileCommand(ALICE.getName());
        DeleteClientProfileCommand deleteSecondCommand = new DeleteClientProfileCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientProfileCommand deleteFirstCommandCopy = new DeleteClientProfileCommand(ALICE.getName());
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
        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(ALICE.getName());
        String expected = DeleteClientProfileCommand.class.getCanonicalName() + "{targetName=" + ALICE.getName() + "}";
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
