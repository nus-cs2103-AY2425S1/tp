package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.task.Task;
import seedu.address.model.student.task.TaskDeadline;
import seedu.address.model.student.task.TaskDescription;
import seedu.address.model.student.task.TaskList;
import seedu.address.testutil.StudentBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {
    private static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);

    private Model model;
    private Task testTask = new Task(new TaskDescription("First Assignment"), new TaskDeadline("2024-10-16"));
    private Student targetStudent;

    @BeforeEach
    public void setUp() {
        // ensure the model has task to delete
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        targetStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student updatedStudent = new StudentBuilder(model.getStudentByName(targetStudent.getName()))
                .build();
        TaskList updatedTaskList = updatedStudent.getTaskList();
        updatedTaskList.add(testTask);
        model.setStudent(targetStudent, updatedStudent);

        // reinitialise target student after updating the model
        targetStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
    }

    @Test
    public void execute_validArgument_success() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetStudent.getName(), INDEX_FIRST_TASK);

        Task targetTask = targetStudent.getTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                targetTask.getTaskDescription(), targetStudent.getName(), targetTask.getTaskDeadline());

        // Ensure initial and final state of the model is actually different
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Student updatedStudent = new StudentBuilder(expectedModel.getStudentByName(targetStudent.getName()))
                .build();
        TaskList updatedTaskList = updatedStudent.getTaskList();
        updatedTaskList.remove(targetTask);
        expectedModel.setStudent(targetStudent, updatedStudent);
        assertEquals(expectedModel, new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertNotEquals(model, expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Student targetStudent = model.getFilteredStudentList().get(0);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetStudent.getName(), outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(new Name("UNKNOWN NAME"), INDEX_FIRST_TASK);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        Name student1 = new Name("Ada");
        Name student2 = new Name("Bob");
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        DeleteTaskCommand deleteFirstStudentFirstIndexCommand = new DeleteTaskCommand(student1, index1);
        DeleteTaskCommand deleteFirstStudentSecondIndexCommand = new DeleteTaskCommand(student1, index2);
        DeleteTaskCommand deleteSecondStudentFirstIndexCommand = new DeleteTaskCommand(student2, index1);

        //same object -> returns true
        assertTrue(deleteFirstStudentFirstIndexCommand.equals(deleteFirstStudentFirstIndexCommand));

        //same values -> returns true
        DeleteTaskCommand deleteFirstStudentFirstIndexCommandCopy = new DeleteTaskCommand(student1, index1);
        assertTrue(deleteFirstStudentFirstIndexCommand.equals(deleteFirstStudentFirstIndexCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstStudentFirstIndexCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstStudentFirstIndexCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstStudentFirstIndexCommand.equals(deleteSecondStudentFirstIndexCommand));

        // different index -> returns false
        assertFalse(deleteFirstStudentFirstIndexCommand.equals(deleteFirstStudentSecondIndexCommand));
    }

    @Test
    public void toStringMethod() {
        Name targetName = new Name("Bob");
        Index targetIndex = Index.fromOneBased(1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetName, targetIndex);
        String expected = DeleteTaskCommand.class.getCanonicalName()
                + "{targetName=" + targetName + ", "
                + "targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTaskCommand.toString());
    }
}
