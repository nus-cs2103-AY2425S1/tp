package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalTasks;

public class DeleteTaskCommandTest {

    private Model model;
    private Model originalModelState;

    @BeforeEach
    public void setUp() {
        // Set up a model with a few typical tasks and one person with a task
        model = new ModelManager();
        List<Task> taskList = TypicalTasks.getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
        Person personWithTask = new PersonBuilder().withTasks("todo: buy groceries").build();
        model.addPerson(personWithTask);

        // Save original state for resetting
        originalModelState = new ModelManager();
        for (Task task : taskList) {
            originalModelState.addTask(task);
        }
        originalModelState.addPerson(personWithTask);
    }

    @AfterEach
    public void tearDown() {
        // Reset the model to the original state
        model = new ModelManager();
        for (Task task : originalModelState.getFilteredTaskList()) {
            model.addTask(task);
        }
        for (Person person : originalModelState.getFilteredPersonList()) {
            model.addPerson(person);
        }
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);

        CommandResult result = deleteTaskCommand.execute(model);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Ensure the task is removed from the model
        assertFalse(model.getFilteredTaskList().contains(taskToDelete), "The task should be removed from the model.");
    }

    @Test
    public void execute_taskAssignedToPerson_updatesPersonTasks() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        deleteTaskCommand.execute(model);

        // Ensure the task is removed from each person's task list
        for (Person person : model.getFilteredPersonList()) {
            assertFalse(person.hasTask(taskToDelete), "The person should no longer have the deleted task.");
        }
    }


    @Test
    public void execute_taskNotAssignedToAnyPerson_noChangesToPersons() throws Exception {
        Task unassignedTask = new Todo("unassigned task");
        model.addTask(unassignedTask);
        Index unassignedTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());


        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(unassignedTaskIndex);
        deleteTaskCommand.execute(model);

        // Ensure no person was affected since the task was unassigned
        for (Person person : model.getFilteredPersonList()) {
            assertFalse(person.hasTask(unassignedTask), "Persons without task should remain unaffected.");
        }
    }

    @Test
    public void execute_taskRemovedFromMultiplePersons() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Person person1 = new PersonBuilder().withName("Person 1").withTasks("todo: buy groceries").build();
        Person person2 = new PersonBuilder().withName("Person 2").withTasks("todo: buy groceries").build();
        model.addPerson(person1);
        model.addPerson(person2);

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        deleteTaskCommand.execute(model);

        // Verify task is removed from both persons
        assertFalse(person1.hasTask(taskToDelete), "Person 1 should no longer have the deleted task.");
        assertFalse(person2.hasTask(taskToDelete), "Person 2 should no longer have the deleted task.");
    }

    @Test
    public void execute_equalsMethod() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
