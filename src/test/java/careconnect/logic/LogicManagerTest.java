package careconnect.logic;

import static careconnect.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static careconnect.logic.Messages.MESSAGE_NO_AUTOCOMPLETE_OPTIONS;
import static careconnect.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import careconnect.logic.Logic.ValidateSyntaxResultEnum;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.AddCommand;
import careconnect.logic.commands.ClearCommand;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.CommandTestUtil;
import careconnect.logic.commands.ListCommand;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.ReadOnlyAddressBook;
import careconnect.model.UserPrefs;
import careconnect.model.person.Person;
import careconnect.storage.JsonAddressBookStorage;
import careconnect.storage.JsonUserPrefsStorage;
import careconnect.storage.StorageManager;
import careconnect.testutil.Assert;
import careconnect.testutil.PersonBuilder;
import careconnect.testutil.TypicalPersons;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

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
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void autocompleteCommand_noAvailableOptions_throwsAutocompleteException() {
        assertAutocompleteException("findd", String.format(
                MESSAGE_NO_AUTOCOMPLETE_OPTIONS, "findd"));
        assertAutocompleteException("xs", String.format(MESSAGE_NO_AUTOCOMPLETE_OPTIONS,
                "xs"));
    }

    @Test
    public void autocompleteCommand_availableOptions_success() throws Exception {
        assertAutocompleteSuccess("fi", "find");
        assertAutocompleteSuccess("ad", "add");
    }

    @Test
    public void commandConfirmation_noCommandToConfirm_throwsCommandException() {
        LogicManager.setCommandToConfirm(null);
        assertCommandException("y", Messages.MESSAGE_NO_EXECUTABLE_COMMAND);
        assertCommandException("n", Messages.MESSAGE_NO_EXECUTABLE_COMMAND);
    }

    @Test
    public void commandConfirmation_commandYetToConfirm_throwsCommandException() {
        LogicManager.setCommandToConfirm(new ClearCommand());
        assertCommandException("clear", Messages.MESSAGE_UNCOMFIRMED_COMMAND);
        LogicManager.setCommandToConfirm(null);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void validateSyntax_validInputs_correctResults() {
        assertEquals(ValidateSyntaxResultEnum.INVALID_COMMAND, logic.validateSyntax("fa"));
        assertEquals(ValidateSyntaxResultEnum.INVALID_COMMAND, logic.validateSyntax("add n/"));
        assertEquals(ValidateSyntaxResultEnum.VALID_COMMAND_WORD, logic.validateSyntax("add"));
        assertEquals(ValidateSyntaxResultEnum.VALID_FULL_COMMAND, logic.validateSyntax("add n/Betsy Crowe "
                + "t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal"));

        // extra spaces should not invalidate syntax
        assertEquals(ValidateSyntaxResultEnum.VALID_COMMAND_WORD, logic.validateSyntax("add "));
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
     * Autocompletes the input command and confirms that
     * - no exceptions are thrown
     * - the autocompleted suggestion is equal to {@code expectedSuggestion}
     * @see #assertAutocompleteException(String, String)
     */
    private void assertAutocompleteSuccess(String inputCommand,
            String expectedSuggestion) throws AutocompleteException {
        assertEquals(expectedSuggestion, logic.autocompleteCommand(inputCommand));

    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Autocompletes the input command, confirms that a AutocompleteException is thrown and that the result
     * message is correct.
     */
    private void assertAutocompleteException(String inputCommand, String expectedMessage) {
        Assert.assertThrows(AutocompleteException.class, expectedMessage, () -> logic.autocompleteCommand(
                inputCommand));
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
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
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

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY
                + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
