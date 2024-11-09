package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Tests for the LogicManager class.
 */
public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;
    private StorageManager storage;

    /**
     * Sets up the test environment with the required model and storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
        logic = new LogicManager(model, storage);
    }

    /**
     * Tests the execution of an invalid command format, expecting a ParseException.
     */
    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsParseException() {
        String deleteCommand = "delete invalidNRIC"; // An invalid NRIC format
        assertThrows(ParseException.class, () -> logic.execute(deleteCommand));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws IOException {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() throws IOException {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage)
            throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command and confirms that the specified
     * {@code expectedException} is thrown and the message is correct.
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) throws IOException {
        // Create a snapshot of key model attributes before injecting faulty storage
        ReadOnlyAddressBook initialAddressBook = model.getAddressBook();
        UserPrefs initialUserPrefs = new UserPrefs(model.getUserPrefs());

        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + AGE_DESC_AMY + GENDER_DESC_AMY + NRIC_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertThrows(CommandException.class, expectedMessage, () -> logic.execute(addCommand));

        // Verify that the model's state remains unchanged after the failure
        assertEquals(initialAddressBook, model.getAddressBook());
        assertEquals(initialUserPrefs, model.getUserPrefs());
    }

    @Test
    public void execute_commandRequiresConfirmation_confirmationPrompted() throws Exception {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());
        model.backupData("test backup");

        // Execute the restore command which requires confirmation
        String restoreCommandText = "restore 0";
        CommandResult result = logic.execute(restoreCommandText);

        // Verify that confirmation is required
        assertTrue(result.requiresConfirmation());

        // Verify the confirmation message
        String expectedMessage = RestoreCommand.MESSAGE_CONFIRMATION + "\n" + RestoreCommand.MESSAGE_BACKUP_REMINDER;
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_confirmationYes_commandExecuted() throws Exception {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());
        model.backupData("test backup");

        // First, execute the restore command to initiate confirmation
        logic.execute("restore 0");

        // Now, simulate the user input 'Y' for confirmation
        CommandResult result = logic.execute("Y");

        // Verify that the restore was successful
        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SUCCESS, 0);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_confirmationNo_commandCancelled() throws Exception {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());
        model.backupData("test backup");

        // First, execute the restore command to initiate confirmation
        logic.execute("restore 0");

        // Now, simulate the user input 'N' for cancellation
        CommandResult result = logic.execute("N");

        // Verify that the operation was cancelled
        assertEquals(RestoreCommand.MESSAGE_CANCELLED, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidResponseDuringConfirmation_operationCancelled() throws Exception {
        // Save the address book to ensure the file exists
        storage.saveAddressBook(model.getAddressBook());
        model.backupData("test backup");

        // Execute the restore command which requires confirmation
        logic.execute("restore 0");

        // Simulate invalid user input during confirmation
        CommandResult result = logic.execute("invalid input");

        // Verify that the operation was cancelled
        assertEquals(RestoreCommand.MESSAGE_CANCELLED, result.getFeedbackToUser());
    }
}
