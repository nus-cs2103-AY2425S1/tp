package seedu.hiredfiredpro.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.hiredfiredpro.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.INTERVIEW_SCORE_DESC_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.SKILL_DESC_PYTHON;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.hiredfiredpro.logic.commands.AddCommand;
import seedu.hiredfiredpro.logic.commands.CommandResult;
import seedu.hiredfiredpro.logic.commands.ListCommand;
import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.UserPrefs;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.storage.JsonHiredFiredProStorage;
import seedu.hiredfiredpro.storage.JsonUserPrefsStorage;
import seedu.hiredfiredpro.storage.StorageManager;
import seedu.hiredfiredpro.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonHiredFiredProStorage hiredFiredProStorage =
                new JsonHiredFiredProStorage(temporaryFolder.resolve("hiredFiredPro.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(hiredFiredProStorage, userPrefsStorage);
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
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
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
        Model expectedModel = new ModelManager(model.getHiredFiredPro(), new UserPrefs());
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

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an HiredFiredProStorage that throws the IOException e when saving
        JsonHiredFiredProStorage hiredFiredProStorage = new JsonHiredFiredProStorage(prefPath) {
            @Override
            public void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(hiredFiredProStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveHiredFiredPro method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + JOB_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + SKILL_DESC_PYTHON + INTERVIEW_SCORE_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
