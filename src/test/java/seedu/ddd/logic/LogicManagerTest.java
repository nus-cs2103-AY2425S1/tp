package seedu.ddd.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
import static seedu.ddd.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ddd.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.ddd.logic.commands.CommandResult;
import seedu.ddd.logic.commands.ListContactCommand;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.storage.JsonAddressBookStorage;
import seedu.ddd.storage.JsonUserPrefsStorage;
import seedu.ddd.storage.StorageManager;

public class LogicManagerTest {
    // private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    // private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listContactCommand = ListContactCommand.COMMAND_WORD;
        int size = model.getFilteredContactList().size();
        assertCommandSuccess(listContactCommand, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, size), model);
    }

    // @Test
    // public void execute_storageThrowsIoException_throwsCommandException() {
    //     assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
    //             LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    // }

    // @Test
    // public void execute_storageThrowsAdException_throwsCommandException() {
    //     assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
    //             LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    // }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredContactList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    // /**
    //  * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
    //  *
    //  * @param e the exception to be thrown by the Storage component
    //  * @param expectedMessage the message expected inside exception thrown by the Logic component
    //  */
    // private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
    //     Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

    //     // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
    //     JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
    //         @Override
    //         public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
    //                 throws IOException {
    //             throw e;
    //         }
    //     };

    //     JsonUserPrefsStorage userPrefsStorage =
    //             new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
    //     StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

    //     logic = new LogicManager(model, storage);

    //     // Triggers the saveAddressBook method by executing an add command
    //     String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
    //             + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
    //     Contact expectedContact = new ClientBuilder(AMY).withTags().build();
    //     ModelManager expectedModel = new ModelManager();
    //     expectedModel.addContact(expectedContact);
    //     assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    // }
}
