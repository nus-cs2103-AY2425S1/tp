package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalTasks;

public class DeleteTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Set up a model with a few typical tasks and one person with a task
        model = new ModelManager();
        List<Task> taskList = TypicalTasks.getTypicalTasks();
        model.addTask(taskList.get(0));
    }


    @Test
    public void execute_validIndex_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);

        CommandResult result = deleteTaskCommand.execute(model);

        String expectedMessage = String.format(
                Messages.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Ensure the task is removed from the model
        assertFalse(model.getFilteredTaskList().contains(taskToDelete),
                "The task should be removed from the model.");
        model.addTask(new Todo("buy groceries"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(invalidIndex);
        assertThrows(CommandException.class, String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                        model.getFilteredTaskList().size() + 1, 1,
                        model.getFilteredTaskList().size()), () -> deleteTaskCommand.execute(model)
        );
    }

    @Test
    public void execute_taskAssignedToPerson_updatesPersonTasks() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        Person personWithTask = new PersonBuilder().withTasks("todo: Buy groceries").build();
        model.addPerson(personWithTask);
        deleteTaskCommand.execute(model);

        // Ensure the task is removed from each person's task list
        assertFalse(model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased()).hasTask(taskToDelete),
                "The person should no longer have the deleted task.");
    }


    @Test
    public void execute_taskNotAssignedToAnyPerson_noChangesToPersons() throws Exception {
        Task unassignedTask = new Todo("unassigned task");
        Person personWithNoTask = new PersonBuilder().withName("notask").build();
        model.addPerson(personWithNoTask);
        model.addTask(unassignedTask);
        Index unassignedTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());


        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(unassignedTaskIndex);
        deleteTaskCommand.execute(model);

        // Ensure no person was affected since the task was unassigned

        assertFalse(personWithNoTask.hasTask(unassignedTask),
                "Persons without task should remain unaffected.");
        model.deletePerson(personWithNoTask);
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
        model.deletePerson(person1);
        model.deletePerson(person2);
        model.addTask(new Todo("buy groceries"));

    }

    @Test
    public void execute_equalsMethod() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different task -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void toString_includesTargetIndex() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        String toStringResult = deleteTaskCommand.toString();
        assertTrue(toStringResult.contains("targetIndex=" + targetIndex),
                "toString() should include 'targetIndex' with the correct value.");
    }


}
