package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

public class UniqueTaskListTest {
    private static final Task TODO_TASK = new Todo("Buy groceries");
    private static final Task EVENT_TASK = new Event("Team meeting", "2023-10-01", "2023-10-02");
    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();


    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(TODO_TASK));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(TODO_TASK);
        assertTrue(uniqueTaskList.contains(TODO_TASK));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(TODO_TASK);
        Task todoTaskCopy = new Todo("Buy groceries"); // Same description as TODO_TASK
        assertTrue(uniqueTaskList.contains(todoTaskCopy));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(TODO_TASK);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(TODO_TASK));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, TODO_TASK));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(TODO_TASK, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setTask(TODO_TASK, TODO_TASK));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        uniqueTaskList.add(TODO_TASK);
        uniqueTaskList.setTask(TODO_TASK, TODO_TASK);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(TODO_TASK);
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(TODO_TASK);
        Task editedTodoTask = new Todo("Buy groceries");
        uniqueTaskList.setTask(TODO_TASK, editedTodoTask);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(editedTodoTask);
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(TODO_TASK);
        uniqueTaskList.setTask(TODO_TASK, EVENT_TASK);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(EVENT_TASK);
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(TODO_TASK);
        uniqueTaskList.add(EVENT_TASK);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(TODO_TASK, EVENT_TASK));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(TODO_TASK));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(TODO_TASK);
        uniqueTaskList.remove(TODO_TASK);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(TODO_TASK);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(EVENT_TASK);
        uniqueTaskList.setTasks(expectedTaskList);
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(TODO_TASK);
        List<Task> taskList = Collections.singletonList(EVENT_TASK);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedTaskList = new UniqueTaskList();
        expectedTaskList.add(EVENT_TASK);
        assertEquals(expectedTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(TODO_TASK, TODO_TASK);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTasks(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        uniqueTaskList.add(TODO_TASK);
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void hashCode_sameInternalList_returnsSameHashCode() {
        uniqueTaskList.add(TODO_TASK);
        UniqueTaskList anotherUniqueTaskList = new UniqueTaskList();
        anotherUniqueTaskList.add(TODO_TASK);
        assertEquals(uniqueTaskList.hashCode(), anotherUniqueTaskList.hashCode());
    }

    @Test
    public void hashCode_differentInternalList_returnsDifferentHashCode() {
        uniqueTaskList.add(TODO_TASK);
        UniqueTaskList anotherUniqueTaskList = new UniqueTaskList();
        anotherUniqueTaskList.add(EVENT_TASK);
        assertFalse(uniqueTaskList.hashCode() == anotherUniqueTaskList.hashCode());
    }

    @Test
    public void toString_returnsCorrectStringRepresentation() {
        uniqueTaskList.add(TODO_TASK);
        assertEquals(Collections.singletonList(TODO_TASK).toString(), uniqueTaskList.toString());
    }


    @Test
    public void getTask_taskExists_returnsTask() {
        uniqueTaskList.add(TODO_TASK);
        Task retrievedTask = uniqueTaskList.getTask(TODO_TASK);
        assertEquals(TODO_TASK, retrievedTask, "The retrieved task should match the original task.");
    }

    @Test
    public void getTask_taskDoesNotExist_throwsNoSuchElementException() {
        Task nonExistentTask = new Todo("Non-existent task");
        assertThrows(NoSuchElementException.class, () -> uniqueTaskList.getTask(nonExistentTask),
                "Expected getTask to throw NoSuchElementException for a non-existent task.");
    }

    @Test
    public void iterator_iterateOverList_returnsCorrectOrder() {
        uniqueTaskList.add(TODO_TASK);
        uniqueTaskList.add(EVENT_TASK);
        List<Task> taskList = Arrays.asList(TODO_TASK, EVENT_TASK);
        Iterator<Task> iterator = uniqueTaskList.iterator();
        for (Task task : taskList) {
            assertTrue(iterator.hasNext());
            assertEquals(task, iterator.next());
        }
    }

    @Test
    public void equals() {
        // Same object
        assertTrue(uniqueTaskList.equals(uniqueTaskList), "A UniqueTaskList should equal itself.");

        // Null comparison
        assertFalse(uniqueTaskList.equals(null), "A UniqueTaskList should not equal null.");

        // Different type comparison
        assertFalse(uniqueTaskList.equals("String object"),
                "A UniqueTaskList should not equal an object of a different type.");

        // Identical tasks in both lists
        uniqueTaskList.add(TODO_TASK);
        UniqueTaskList identicalTaskList = new UniqueTaskList();
        identicalTaskList.add(TODO_TASK);
        assertTrue(uniqueTaskList.equals(identicalTaskList),
                "UniqueTaskLists with identical tasks should be equal.");

        // Different tasks in lists
        UniqueTaskList differentTaskList = new UniqueTaskList();
        differentTaskList.add(EVENT_TASK);
        assertFalse(uniqueTaskList.equals(differentTaskList),
                "UniqueTaskLists with different tasks should not be equal.");
    }

}

