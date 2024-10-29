package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.task.Task;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateCommand.
 */
public class UpdateTaskCommandTest {
    private static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    private static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student updatedStudent = model.getAddressBook().getStudentList().get(1);
        Task updatedTask = updatedStudent.getTaskList().get(0);
        UpdateTaskDescriptor descriptor = new UpdateTaskDescriptorBuilder(updatedTask).build();
        UpdateTaskCommand updateTaskCommand =
                new UpdateTaskCommand(updatedStudent.getName(), INDEX_FIRST_TASK, descriptor);

        String expectedMessage =
                String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS,
                        updatedTask.getTaskDescription(), updatedStudent.getName(), updatedTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        Optional<Student> studentToUpdate = model.getAddressBook()
                .getStudentList()
                .stream()
                .filter(student -> student.getName().equals(updatedStudent.getName()))
                .findFirst();

        if (studentToUpdate.isPresent()) {
            expectedModel.setStudent(studentToUpdate.get(), updatedStudent);
        } else {
            throw new IllegalStateException("Student to update not found");
        }

        assertCommandSuccess(updateTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_onlyTaskDescriptionSpecifiedUnfilteredList_success() {
        Student targetStudent = model.getAddressBook().getStudentList().get(1);
        Task lastTask = targetStudent.getTaskList().get(1);

        TaskBuilder newTask = new TaskBuilder(lastTask);
        Task updatedTask = newTask.withTaskDescription("Prepare test").build();

        StudentBuilder studentInList = new StudentBuilder(targetStudent);
        Student updatedStudent = studentInList.withTaskList(targetStudent.getTaskList().get(0), updatedTask).build();

        UpdateTaskDescriptor descriptor = new UpdateTaskDescriptorBuilder().withTaskDescription("Prepare test").build();
        UpdateTaskCommand updateTaskCommand =
                new UpdateTaskCommand(targetStudent.getName(), INDEX_SECOND_TASK, descriptor);

        String expectedMessage =
                String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS,
                        updatedTask.getTaskDescription(), targetStudent.getName(), lastTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(targetStudent, updatedStudent);

        assertCommandSuccess(updateTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_onlyTaskDeadlineSpecifiedUnfilteredList_success() {
        Student targetStudent = model.getAddressBook().getStudentList().get(1);
        Task lastTask = targetStudent.getTaskList().get(1);

        TaskBuilder newTask = new TaskBuilder(lastTask);
        Task updatedTask = newTask.withTaskDeadline("2024-10-31").build();

        StudentBuilder studentInList = new StudentBuilder(targetStudent);
        Student updatedStudent = studentInList.withTaskList(targetStudent.getTaskList().get(0), updatedTask).build();

        UpdateTaskDescriptor descriptor = new UpdateTaskDescriptorBuilder().withTaskDeadline("2024-10-31").build();
        UpdateTaskCommand updateTaskCommand =
                new UpdateTaskCommand(targetStudent.getName(), INDEX_SECOND_TASK, descriptor);

        String expectedMessage =
                String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS,
                        lastTask.getTaskDescription(), targetStudent.getName(), updatedTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(targetStudent, updatedStudent);

        assertCommandSuccess(updateTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Student updatedStudent = model.getFilteredStudentList().get(1);
        Task updatedTask = updatedStudent.getTaskList().get(0);
        UpdateTaskCommand updateTaskCommand =
                new UpdateTaskCommand(updatedStudent.getName(), INDEX_FIRST_TASK, new UpdateTaskDescriptor());

        String expectedMessage =
                String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS,
                        updatedTask.getTaskDescription(), updatedStudent.getName(), updatedTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(updateTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_SECOND_STUDENT);

        Student studentInFilteredList = model.getFilteredStudentList().get(0);

        TaskBuilder newTask = new TaskBuilder(studentInFilteredList.getTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        Task updatedTask = newTask.withTaskDeadline("2024-10-31").build();

        Student updatedStudent = new StudentBuilder(studentInFilteredList)
                .withTaskList(updatedTask, studentInFilteredList.getTaskList().get(INDEX_SECOND_TASK.getZeroBased()))
                .build();

        UpdateTaskCommand updateTaskCommand = new UpdateTaskCommand(studentInFilteredList.getName(), INDEX_FIRST_TASK,
                new UpdateTaskDescriptorBuilder().withTaskDeadline("2024-10-31").build());

        String expectedMessage =
                String.format(UpdateTaskCommand.MESSAGE_UPDATE_TASK_SUCCESS,
                        updatedTask.getTaskDescription(), updatedStudent.getName(), updatedTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), updatedStudent);

        assertCommandSuccess(updateTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        Student targetStudent = model.getFilteredStudentList().get(0);
        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UpdateTaskCommand updateTaskCommand =
                new UpdateTaskCommand(targetStudent.getName(), outOfBoundIndex, updateTaskDescriptor);

        String expectedMessage = String.format(UpdateTaskCommand.MESSAGE_TASK_NOT_FOUND,
                outOfBoundIndex.getOneBased(), targetStudent.getName());

        assertCommandFailure(updateTaskCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final UpdateTaskCommand standardTaskCommand =
                new UpdateTaskCommand(new Name(VALID_NAME_AMY), INDEX_FIRST_TASK, DESC_TASK_AMY);

        // same values -> returns true
        UpdateTaskDescriptor copyDescriptor = new UpdateTaskDescriptor(DESC_TASK_AMY);
        UpdateTaskCommand commandWithSameValues =
                new UpdateTaskCommand(new Name(VALID_NAME_AMY), INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardTaskCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardTaskCommand.equals(standardTaskCommand));

        // null -> returns false
        assertFalse(standardTaskCommand.equals(null));

        // different types -> returns false
        assertFalse(standardTaskCommand.equals(new ClearCommand()));

        // different names -> returns false
        assertFalse(standardTaskCommand.equals(
                new UpdateTaskCommand(new Name(VALID_NAME_BOB), INDEX_FIRST_TASK, DESC_TASK_AMY)));

        // different indexes -> returns false
        assertFalse(standardTaskCommand.equals(
                new UpdateTaskCommand(new Name(VALID_NAME_AMY), INDEX_SECOND_TASK, DESC_TASK_AMY)));

        // different descriptor -> returns false
        assertFalse(standardTaskCommand.equals(
                new UpdateTaskCommand(new Name(VALID_NAME_AMY), INDEX_FIRST_TASK, DESC_TASK_BOB)));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("Test name");
        UpdateTaskDescriptor updateTaskDescriptor = new UpdateTaskDescriptor();
        UpdateTaskCommand updateTaskCommand = new UpdateTaskCommand(name, INDEX_FIRST_TASK, updateTaskDescriptor);
        String expected = UpdateTaskCommand.class.getCanonicalName() + "{name=" + name + ", task index="
                + INDEX_FIRST_TASK + ", updateTaskDescriptor="
                + updateTaskDescriptor + "}";
        assertEquals(expected, updateTaskCommand.toString());
    }
}
