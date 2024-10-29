package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.DANIEL;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Name;

public class AddToLessonCommandTest {

    private Model model;

    private final Index validLessonIndex1WithAlice = Index.fromOneBased(1);
    private final Index validLessonIndex5 = Index.fromOneBased(5);
    private final ObservableList<Name> allInvalidStudentNames = FXCollections.observableArrayList(
            new Name(AMY.getName().fullName), new Name(BOB.getName().fullName));
    private final ObservableList<Name> containsAliceNames = FXCollections.observableArrayList(
            new Name(ALICE.getName().fullName));
    private final ObservableList<Name> someInvalidStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(BOB.getName().fullName));
    private final ObservableList<Name> validStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(DANIEL.getName().fullName));
    private final ObservableList<Name> duplicateStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(CARL.getName().fullName));
    private final ObservableList<Index> validStudentIndicesContainsAlice = FXCollections.observableArrayList(
            INDEX_FIRST_STUDENT, INDEX_SECOND_STUDENT); // contains Alice and Benson
    private final ObservableList<Index> validStudentIndicesNoAlice = FXCollections.observableArrayList(
            INDEX_SECOND_STUDENT); // contains Benson
    private final ObservableList<Index> duplicateIndices = FXCollections.observableArrayList(
            INDEX_FIRST_STUDENT, INDEX_FIRST_STUDENT);
    private ObservableList<Index> allInvalidIndices;
    private ObservableList<Index> someInvalidIndices;


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        allInvalidIndices = FXCollections.observableArrayList(
                Index.fromOneBased(model.getFilteredStudentList().size() + 1),
                Index.fromOneBased(model.getFilteredStudentList().size() + 2));
        someInvalidIndices = FXCollections.observableArrayList(
                INDEX_FIRST_STUDENT,
                Index.fromOneBased(model.getFilteredStudentList().size() + 1));
    }

    @Test
    public void execute_addStudentsToLessonNamesAndIndices_success() throws Exception {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                validStudentNames, validStudentIndicesContainsAlice);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandResult result = command.execute(model);

        Lesson targetLesson = expectedModel.getFilteredLessonList()
                .get(validLessonIndex5.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        editedLesson.addStudent(CARL);
        editedLesson.addStudent(DANIEL);
        editedLesson.addStudent(ALICE);
        editedLesson.addStudent(BENSON);
        expectedModel.setLesson(targetLesson, editedLesson);

        String expectedMessage = String.format(AddToLessonCommand.MESSAGE_ADD_TO_LESSON_SUCCESS,
                Messages.format(editedLesson));

        assertEquals(result.getFeedbackToUser(), expectedMessage);
        assertEquals(expectedModel.getFilteredLessonList().get(validLessonIndex5.getZeroBased()),
                model.getFilteredLessonList().get(validLessonIndex5.getZeroBased()));
    }

    @Test
    public void execute_addStudentsToLessonIndicesOnly_success() throws Exception {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                FXCollections.observableArrayList(), validStudentIndicesContainsAlice);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandResult result = command.execute(model);

        Lesson targetLesson = expectedModel.getFilteredLessonList()
                .get(validLessonIndex5.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        editedLesson.addStudent(ALICE);
        editedLesson.addStudent(BENSON);
        expectedModel.setLesson(targetLesson, editedLesson);

        String expectedMessage = String.format(AddToLessonCommand.MESSAGE_ADD_TO_LESSON_SUCCESS,
                Messages.format(editedLesson));

        assertEquals(result.getFeedbackToUser(), expectedMessage);
        assertEquals(expectedModel.getFilteredLessonList().get(validLessonIndex5.getZeroBased()),
                model.getFilteredLessonList().get(validLessonIndex5.getZeroBased()));
    }

    @Test
    public void execute_addStudentsToLessonNamesOnly_success() throws Exception {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                validStudentNames, FXCollections.observableArrayList());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandResult result = command.execute(model);

        Lesson targetLesson = expectedModel.getFilteredLessonList()
                .get(validLessonIndex5.getZeroBased());
        Lesson editedLesson = new Lesson(targetLesson);

        editedLesson.addStudent(CARL);
        editedLesson.addStudent(DANIEL);
        expectedModel.setLesson(targetLesson, editedLesson);

        String expectedMessage = String.format(AddToLessonCommand.MESSAGE_ADD_TO_LESSON_SUCCESS,
                Messages.format(editedLesson));

        assertEquals(result.getFeedbackToUser(), expectedMessage);
        assertEquals(expectedModel.getFilteredLessonList().get(validLessonIndex5.getZeroBased()),
                model.getFilteredLessonList().get(validLessonIndex5.getZeroBased()));
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        Index invalidLessonIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        AddToLessonCommand command = new AddToLessonCommand(invalidLessonIndex,
                validStudentNames, validStudentIndicesContainsAlice);

        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX,
                invalidLessonIndex.getOneBased()));
    }

    @Test
    public void execute_allInvalidStudentIndices_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                validStudentNames, allInvalidIndices);

        String formattedOutOfBoundIndices = allInvalidIndices.stream()
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someInvalidStudentIndices_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                validStudentNames, someInvalidIndices);

        String formattedOutOfBoundIndices = someInvalidIndices.stream()
                .filter(index -> index.getZeroBased() >= model.getFilteredStudentList().size())
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_allInvalidStudentNames_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                allInvalidStudentNames, validStudentIndicesContainsAlice);

        Lesson lesson = new Lesson(
                new Date("2024-10-20"),
                new Time("14:00"),
                FXCollections.observableArrayList(),
                Map.of());
        String studentNotFoundMessage = String.format(AddToLessonCommand.MESSAGE_STUDENT_NOT_FOUND, AMY.getName());

        assertCommandFailure(command, model, studentNotFoundMessage);
    }

    @Test
    public void execute_someInvalidStudentNames_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                someInvalidStudentNames, validStudentIndicesContainsAlice);

        String studentNotFoundMessage = String.format(AddToLessonCommand.MESSAGE_STUDENT_NOT_FOUND, BOB.getName());

        assertCommandFailure(command, model, studentNotFoundMessage);
    }

    @Test
    public void execute_duplicateStudentByIndexInCommand_throwsCommandException() {

        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                validStudentNames, duplicateIndices);

        String error = String.format(AddToLessonCommand.MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_INDEX,
                ALICE.getName().fullName,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_duplicateStudentByNameInCommand_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex5,
                duplicateStudentNames, validStudentIndicesContainsAlice);

        String error = String.format(AddToLessonCommand.MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_NAME,
                CARL.getName().fullName);

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_duplicateStudentByIndexAlreadyInLesson_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex1WithAlice,
                validStudentNames, validStudentIndicesContainsAlice);

        String error = String.format(AddToLessonCommand.MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_INDEX,
                ALICE.getName().fullName,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_duplicateStudentByNameAlreadyInLesson_throwsCommandException() {
        AddToLessonCommand command = new AddToLessonCommand(validLessonIndex1WithAlice,
                containsAliceNames, validStudentIndicesNoAlice);

        String error = String.format(AddToLessonCommand.MESSAGE_DUPLICATE_STUDENT_IN_LESSON_BY_NAME,
                ALICE.getName().fullName);

        assertCommandFailure(command, model, error);
    }
}
