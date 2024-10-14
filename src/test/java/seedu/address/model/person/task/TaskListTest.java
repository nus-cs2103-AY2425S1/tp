package seedu.address.model.person.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.GRADING_TASK;
import static seedu.address.testutil.TypicalTasks.MARKING_TASK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.TaskNotFoundException;
import seedu.address.storage.JsonAdaptedTask;

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