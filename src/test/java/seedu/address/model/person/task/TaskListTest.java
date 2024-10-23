package seedu.address.model.person.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.GRADING_TASK;
import static seedu.address.testutil.TypicalTasks.MARKING_TASK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.storage.JsonAdaptedTask;
import seedu.address.testutil.TaskBuilder;

public class TaskListTest {
    private final TaskList taskList = new TaskList();

    @Test
    public void sort_correct() {
        TaskList tl1 = new TaskList();
        List<Task> tasks = new ArrayList<>();
        tasks.add(GRADING_TASK);
        tasks.add(MARKING_TASK);
        tl1.setTasks(tasks);
        tl1.sortByDeadline();

        TaskList tl2 = new TaskList();
        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(MARKING_TASK);
        tasks2.add(GRADING_TASK);
        tl2.setTasks(tasks2);

        assertEquals(tl2, tl1);
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.add(null));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> taskList.remove(GRADING_TASK));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        taskList.add(MARKING_TASK);
        taskList.remove(MARKING_TASK);
        TaskList expectedTaskList = new TaskList();
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((TaskList) null));
    }

    @Test
    public void setTasks_taskList_replacesOwnListWithProvidedTaskList() {
        taskList.add(MARKING_TASK);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(GRADING_TASK);
        taskList.setTasks(expectedTaskList);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        taskList.add(MARKING_TASK);
        List<Task> taskListNew = Collections.singletonList(GRADING_TASK);
        taskList.setTasks(taskListNew);
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(GRADING_TASK);
        assertEquals(expectedTaskList, taskList);
    }

    @Test
    public void setTasks_sameTaskDescriptions_showsSameDescription() {
        TaskList tl1 = new TaskList();
        List<Task> tasks = new ArrayList<>();
        tasks.add(GRADING_TASK);
        tasks.add(MARKING_TASK);
        tl1.setTasks(tasks);

        TaskList tl2 = new TaskList();
        List<Task> tasks2 = new ArrayList<>();
        tasks2.add(GRADING_TASK);
        tasks2.add(MARKING_TASK);
        tl2.setTasks(tasks2);

        assertEquals(tl1.toDescription(), tl2.toDescription());
    }

    @Test
    public void updateTask_validIndex_success() {
        TaskList taskList = new TaskList();
        taskList.add(MARKING_TASK); // Assume this task has a valid deadline
        Task updatedTask = GRADING_TASK; // Example of updated task

        // Update the task at index 0
        TaskList updatedTaskList = taskList.updateTask(Index.fromZeroBased(0), updatedTask);

        // Create the expected task list
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.add(updatedTask);

        assertEquals(expectedTaskList, updatedTaskList);
    }

    @Test
    public void updateTask_nullUpdatedTask_throwsNullPointerException() {
        TaskList taskList = new TaskList();
        taskList.add(MARKING_TASK);

        // Ensure NullPointerException is thrown if the updated task is null
        assertThrows(NullPointerException.class, () -> taskList.updateTask(Index.fromZeroBased(0),
                null));
    }

    @Test
    public void updateTask_indexOutOfRange_throwsIndexOutOfBoundsException() {
        TaskList taskList = new TaskList();
        taskList.add(MARKING_TASK); // Only 1 task in the list

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.updateTask(Index.fromZeroBased(1),
                GRADING_TASK));
    }

    @Test
    public void updateTask_emptyTaskList_throwsIndexOutOfBoundsException() {
        TaskList taskList = new TaskList(); // No tasks in the list

        assertThrows(IndexOutOfBoundsException.class, () -> taskList.updateTask(Index.fromZeroBased(0),
                GRADING_TASK));
    }

    @Test
    public void updateTask_sortedAfterUpdate_taskListSortedByDeadline() {
        Task task1 = new TaskBuilder().withTaskDescription("Task 1").withTaskDeadline("2024-12-25").build();
        Task task2 = new TaskBuilder().withTaskDescription("Task 1").withTaskDeadline("2024-11-25").build();
        TaskList taskList = new TaskList();
        taskList.add(task1);
        taskList.add(task2);

        // Update task1 to have an earlier deadline
        Task updatedTask = new TaskBuilder(task1).withTaskDeadline("2024-01-01").build();
        TaskList updatedTaskList = taskList.updateTask(Index.fromZeroBased(0), updatedTask);

        // Check that the updated task list is sorted by deadline
        assertEquals(updatedTask, updatedTaskList.get(0));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> taskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void getjsonAdaptedTaskList_correct() {
        List<JsonAdaptedTask> converted = new ArrayList<>();
        converted.add(new JsonAdaptedTask("Mark homework", "2024-01-01"));
        converted.add(new JsonAdaptedTask("Grade assignment", "2024-12-31"));

        taskList.add(MARKING_TASK);
        taskList.add(GRADING_TASK);
        assertEquals(converted, taskList.getjsonAdaptedTaskList());
    }
}
