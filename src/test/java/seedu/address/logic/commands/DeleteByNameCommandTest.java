package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteByNameCommand}.
 */
public class DeleteByNameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validName_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        DeleteCommand deleteCommand = new DeleteByNameCommand(nameToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validLowerCasedName_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        String lowerCasedName = nameToDelete.toString().toLowerCase();
        Name lowerCasedNameToDelete = new Name(lowerCasedName);
        DeleteCommand deleteCommand = new DeleteByNameCommand(lowerCasedNameToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validUpperCasedName_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        String upperCasedName = nameToDelete.toString().toUpperCase();
        Name upperCasedNameToDelete = new Name(upperCasedName);
        DeleteCommand deleteCommand = new DeleteByNameCommand(upperCasedNameToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_partOfName_throwsCommandException() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        String nameStringToDelete = nameToDelete.toString();
        String partOfNameString = nameStringToDelete.substring(0, nameStringToDelete.length() - 1);
        Name partOfNameToDelete = new Name(partOfNameString);

        DeleteCommand deleteCommand = new DeleteByNameCommand(partOfNameToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void execute_noSpacingName_throwsCommandException() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name nameToDelete = personToDelete.getName();
        String nameStringToDelete = nameToDelete.toString();
        String noSpacingNameString = nameStringToDelete.replaceAll(" ", "");
        Name noSpacingNameToDelete = new Name(noSpacingNameString);

        DeleteCommand deleteCommand = new DeleteByNameCommand(noSpacingNameToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        Name firstName = new Name("John");
        Name secondName = new Name("Jane");
        DeleteCommand deleteFirstCommand = new DeleteByNameCommand(firstName);
        DeleteCommand deleteSecondCommand = new DeleteByNameCommand(secondName);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteByNameCommand(firstName);
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
        Name targetName = new Name("Alice");
        DeleteByNameCommand deleteCommand = new DeleteByNameCommand(targetName);
        String expected = DeleteByNameCommand.class.getCanonicalName() + "{targetName=" + targetName + "}";
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
