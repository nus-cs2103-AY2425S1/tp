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

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Model model;

    /**
     * Sets up the test environment with the required model and storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage =
                new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
    }

    /**
     * Tests the successful deletion of a person given a valid index.
     */
    @Test
    public void execute_validNricUnfilteredList_success() throws IOException {

        String nricInput = "S1234567Z";
        Nric nric = new Nric(nricInput);


        // Find the person to delete based on their NRIC from the list
        Person personToDelete = null;
        for (Person person : model.getFilteredPersonList()) {
            if (person.getNric().equals(nric)) {
                personToDelete = person;
                break;
            }
        }

        // Ensure that the person was found, if not throw an exception
        if (personToDelete == null) {
            throw new AssertionError("Person with NRIC " + nricInput + " not found in the list");
        }

        DeleteCommand deleteCommand = new DeleteCommand(nric);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        // Create the expected model after deletion
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getStorage());
        expectedModel.deletePerson(personToDelete);

        // Assert that the command executes successfully
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNricUnfilteredList_throwsCommandException() {
        Nric invalidNric = new Nric("S0000000A");
        DeleteCommand deleteCommand = new DeleteCommand(invalidNric);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NRIC);
    }

    @Test
    public void execute_validNricFilteredList_success() throws IOException {

        // Show a filtered list of persons, with the first person being shown
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Get the person to delete using the filtered list and their NRIC
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Nric nric = personToDelete.getNric(); // Retrieve the NRIC from the person

        // Create DeleteCommand using the NRIC instead of the Index
        DeleteCommand deleteCommand = new DeleteCommand(nric);

        // Format the expected success message
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        // Create the expected model and delete the person
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getStorage());
        expectedModel.deletePerson(personToDelete);

        // Update the expected model to show no person after deletion
        showNoPerson(expectedModel);

        // Assert that the command executes successfully
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNricFilteredList_throwsCommandException() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Create an NRIC that doesn't exist in the filtered list (invalid NRIC)
        String invalidNricInput = "S9999999Z"; // Ensure this NRIC doesn't exist in the list
        Nric invalidNric = new Nric(invalidNricInput);

        // Create DeleteCommand with the invalid NRIC
        DeleteCommand deleteCommand = new DeleteCommand(invalidNric);

        // Ensure that the command throws an exception for the invalid NRIC
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NRIC);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws IOException {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getStorage());
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
    public void execute_validIndexFilteredList_success() throws IOException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getStorage());
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
    public void equals() {
        Nric nricFirst = new Nric("S1234567A"); // Example NRIC for the first command
        Nric nricSecond = new Nric("S7654321B"); // Example NRIC for the second command

        DeleteCommand deleteFirstCommand = new DeleteCommand(nricFirst);
        DeleteCommand deleteSecondCommand = new DeleteCommand(nricSecond);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(nricFirst);
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
        Nric targetNric = new Nric("S1234567A"); // Example NRIC
        DeleteCommand deleteCommand = new DeleteCommand(targetNric);

        // Create the expected string representation
        String expected = DeleteCommand.class.getCanonicalName() + "{targetNric=" + targetNric + "}";

        // Assert that the string representation is as expected
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
