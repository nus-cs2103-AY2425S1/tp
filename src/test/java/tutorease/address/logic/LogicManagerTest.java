package tutorease.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorease.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tutorease.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tutorease.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalLessons.MATH_LESSON;
import static tutorease.address.testutil.TypicalLessons.getTypicalLessons;
import static tutorease.address.testutil.TypicalStudents.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.commands.CommandResult;
import tutorease.address.logic.commands.ContactCommand;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.UserPrefs;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;
import tutorease.address.storage.JsonLessonScheduleStorage;
import tutorease.address.storage.JsonTutorEaseStorage;
import tutorease.address.storage.JsonUserPrefsStorage;
import tutorease.address.storage.StorageManager;
import tutorease.address.testutil.StudentBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonTutorEaseStorage tutorEaseStorage =
                new JsonTutorEaseStorage(temporaryFolder.resolve("tutorease.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonLessonScheduleStorage lessonStorage = new JsonLessonScheduleStorage(
                temporaryFolder.resolve("lessonschedule.json"));

        StorageManager storage = new StorageManager(tutorEaseStorage, userPrefsStorage, lessonStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
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
        Model expectedModel = new ModelManager(model.getTutorEase(), new UserPrefs(), getTypicalLessons());
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

        // Inject LogicManager with an TutorEaseStorage that throws the IOException e when saving
        JsonTutorEaseStorage tutorEaseStorage = new JsonTutorEaseStorage(prefPath) {
            @Override
            public void saveTutorEase(ReadOnlyTutorEase tutorEase, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        JsonLessonScheduleStorage lessonStorage = new JsonLessonScheduleStorage(
                temporaryFolder.resolve("lessonschedule.json"));
        StorageManager storage = new StorageManager(tutorEaseStorage, userPrefsStorage, lessonStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveTutorEase method by executing an add command
        String addContactCommand = ContactCommand.COMMAND_WORD + " " + AddContactCommand.COMMAND_WORD
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ROLE_DESC_AMY;
        Person expectedPerson = new StudentBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addContactCommand, CommandException.class, expectedMessage, expectedModel);
    }

    /**
     * Tests that the correct file path for TutorEase data is returned from the model.
     * Ensures that the LogicManager correctly retrieves the file path for storing TutorEase data.
     */
    @Test
    public void getTutorEaseFilePath_validPath_success() {
        Path expectedPath = Path.of("data/tutorease.json");
        assertEquals(expectedPath, logic.getTutorEaseFilePath()); // Ensure the model method was called
    }

    /**
     * Tests that the filtered lesson list is updated correctly after adding a lesson.
     * Ensures that the LogicManager retrieves the correct ObservableList with the added lesson.
     */
    @Test
    public void getFilteredLessonList_addLesson_success() {
        model.addLesson(MATH_LESSON);
        ObservableList<Lesson> expectedList = FXCollections.observableArrayList(MATH_LESSON);
        assertEquals(expectedList, logic.getFilteredLessonList());
    }



}
