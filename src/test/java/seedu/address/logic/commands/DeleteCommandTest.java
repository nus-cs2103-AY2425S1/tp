package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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

        // Initialize the model with the address book and storage
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);

        // Save the initial address book data to the storage file
        storage.saveAddressBook(model.getAddressBook());
    }

    /**
     * Tests the successful deletion of a person given a valid index.
     */
    @Test
    public void execute_validNricUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(0);
        Nric nric = personToDelete.getNric();

        DeleteCommand deleteCommand = new DeleteCommand(nric);

        CommandResult commandResult = deleteCommand.execute(model);

        // Check that the person is deleted from the model
        assertFalse(model.getAddressBook().getPersonList().contains(personToDelete));

        // Check that the feedback message contains the expected strings
        assertTrue(commandResult.getFeedbackToUser().contains("Deleted Patient:"));
        assertTrue(commandResult.getFeedbackToUser().contains(personToDelete.getName().fullName));
        assertTrue(commandResult
                .getFeedbackToUser()
                .contains("Description: delete_" + personToDelete.getName().fullName));
    }

    @Test
    public void execute_invalidNricUnfilteredList_throwsCommandException() {
        Nric invalidNric = new Nric("S0000000A");
        DeleteCommand deleteCommand = new DeleteCommand(invalidNric);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_NRIC);
    }

    @Test
    public void execute_validNricFilteredList_success() throws Exception {
        // Filter the list to show only the first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(0);
        Nric nric = personToDelete.getNric();

        DeleteCommand deleteCommand = new DeleteCommand(nric);

        CommandResult commandResult = deleteCommand.execute(model);

        // Check that the person is deleted from the address book
        assertFalse(model.getAddressBook().getPersonList().contains(personToDelete));

        // Check that the filtered list is now empty
        assertTrue(model.getFilteredPersonList().isEmpty());

        // Check that the feedback message contains expected strings
        assertTrue(commandResult.getFeedbackToUser().contains("Deleted Patient:"));
        assertTrue(commandResult.getFeedbackToUser().contains(personToDelete.getName().fullName));
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
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        CommandResult commandResult = deleteCommand.execute(model);

        // Check that the person is deleted from the model
        assertFalse(model.getAddressBook().getPersonList().contains(personToDelete));

        // Check that the feedback message contains the expected strings
        assertTrue(commandResult.getFeedbackToUser().contains("Deleted Patient:"));
        assertTrue(commandResult.getFeedbackToUser().contains(personToDelete.getName().fullName));
        assertTrue(commandResult
                .getFeedbackToUser()
                .contains("Description: delete_" + personToDelete.getName().fullName));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        // Filter the list to show only the first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        CommandResult commandResult = deleteCommand.execute(model);

        // Check that the person is deleted from the address book
        assertFalse(model.getAddressBook().getPersonList().contains(personToDelete));

        // Check that the filtered list is now empty
        assertTrue(model.getFilteredPersonList().isEmpty());

        // Check that the feedback message contains expected strings
        assertTrue(commandResult.getFeedbackToUser().contains("Deleted Patient:"));
        assertTrue(commandResult.getFeedbackToUser().contains(personToDelete.getName().fullName));
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
