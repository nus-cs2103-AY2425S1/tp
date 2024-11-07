package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;

public class TaskStatusModifierTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<Task> taskList = getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
        Person personWithTask = new PersonBuilder().withTasks("todo: buy groceries").build();
        model.addPerson(personWithTask);
        model.assignVendor(personWithTask);
    }

    @Test
    public void modifyTasks_markTasks_success() throws Exception {
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), true);
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());

        Set<Task> modifiedTasks = modifier.modifyTasks(model);

        assertEquals(Set.of(taskToMark), modifiedTasks, "The modified tasks should contain only the marked task.");
        assertTrue(taskToMark.getIsDone(), "The task should be marked as done.");
    }

    @Test
    public void modifyTasks_updatePersonsWithModifiedTask_success() throws Exception {
        Task taskToModify = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), true);

        // Add multiple persons with the task to modify
        Person person1 = new PersonBuilder().withName("Person 1").withTasks("todo: buy groceries").build();
        Person person2 = new PersonBuilder().withName("Person 2").withTasks("todo: buy groceries").build();
        model.addPerson(person1);
        model.addPerson(person2);

        // Execute the modification
        modifier.modifyTasks(model);

        // Verify that both persons have the updated (marked) task
        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToModify)) {
                assertTrue(person.getTask(taskToModify).getIsDone(),
                        "Task in person's task list should be marked as done.");
            }
        }

        TaskStatusModifier demodifier = new TaskStatusModifier(Set.of(INDEX_FIRST), false);
        demodifier.modifyTasks(model);
        model.deletePerson(person1);
        model.deletePerson(person2);
    }

    @Test
    public void modifyTasks_updatePersonsWithModifiedTask_setUpdatedTaskSuccess() throws Exception {
        // Create a task to be modified
        Task taskToModify = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());

        // Add multiple persons, each with the task to modify
        Person person1 = new PersonBuilder().withName("Person 1").withTasks("todo: buy groceries").build();
        Person person2 = new PersonBuilder().withName("Person 2").withTasks("todo: buy groceries").build();
        model.addPerson(person1);
        model.addPerson(person2);

        // Create the TaskStatusModifier with markAsDone set to true
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), true);

        // Execute the modification
        modifier.modifyTasks(model);

        // Verify that each person has the updated (marked) task in their task list
        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToModify)) {
                Task updatedTask = person.getTask(taskToModify);
                assertTrue(updatedTask.getIsDone(),
                        "The task should be marked as done in each person's task list.");
            }
        }
    }



    @Test
    public void modifyTasks_unmarkTasks_success() throws Exception {
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), false);
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        taskToUnmark.markAsDone();

        Set<Task> modifiedTasks = modifier.modifyTasks(model);

        assertEquals(Set.of(taskToUnmark), modifiedTasks,
                "The modified tasks should contain only the unmarked task.");
        assertFalse(taskToUnmark.getIsDone(), "The task should be unmarked as not done.");
    }

    @Test
    public void modifyTasks_markTasksAssignedToPerson_success() throws Exception {
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), true);
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Person personWithSameTask = new PersonBuilder().withName("newperson").withTasks("todo: buy groceries").build();
        model.addPerson(personWithSameTask);
        model.assignVendor(personWithSameTask);

        Set<Task> modifiedTasks = modifier.modifyTasks(model);

        assertEquals(Set.of(taskToMark), modifiedTasks, "The modified tasks should contain only the marked task.");
        assertTrue(taskToMark.getIsDone(), "The task should be marked as done.");
    }

    @Test
    public void modifyTasks_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(outOfBoundIndex), true);

        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                outOfBoundIndex.getOneBased(),
                1, model.getFilteredTaskList().size()), () -> modifier.modifyTasks(model));
    }

    @Test
    public void modifyTasks_updatesPersonsWithMarkedTask() throws Exception {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), true);
        modifier.modifyTasks(model);

        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToMark)) {
                assertTrue(person.getTask(taskToMark).getIsDone(),
                        "Person should have the task marked as done.");
            }
        }
    }

    @Test
    public void modifyTasks_updatesPersonsWithUnmarkedTask() throws Exception {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        taskToUnmark.markAsDone();
        TaskStatusModifier modifier = new TaskStatusModifier(Set.of(INDEX_FIRST), false);
        modifier.modifyTasks(model);

        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToUnmark)) {
                assertFalse(person.getTask(taskToUnmark).getIsDone(),
                        "Person should have the task unmarked as not done.");
            }
        }
    }

    @Test
    public void equals() {
        TaskStatusModifier modifier1 = new TaskStatusModifier(Set.of(INDEX_FIRST), true);
        TaskStatusModifier modifier2 = new TaskStatusModifier(Set.of(INDEX_FIRST), true);
        TaskStatusModifier modifierDifferentIndex = new TaskStatusModifier(Set.of(INDEX_SECOND), true);
        TaskStatusModifier modifierDifferentFlag = new TaskStatusModifier(Set.of(INDEX_FIRST), false);

        // Same values -> returns true
        assertEquals(modifier1, modifier2);

        // Same object -> returns true
        assertEquals(modifier1, modifier1);

        // Different indexes -> returns false
        assertNotEquals(modifier1, modifierDifferentIndex);

        // Different markAsDone flag -> returns false
        assertNotEquals(modifier1, modifierDifferentFlag);

        // Null -> returns false
        assertNotEquals(null, modifier1);
    }
}
