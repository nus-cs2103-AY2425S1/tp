package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        model = new ModelManager();
        List<Task> taskList = TypicalTasks.getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
        Person newTodoPerson = new PersonBuilder().withTasks("todo: buy groceries").build();
        model.addPerson(newTodoPerson);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);

        CommandResult commandResult = deleteTaskCommand.execute(model);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_taskAssignedToPerson_taskRemovedFromPerson() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        deleteTaskCommand.execute(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        // Verify the task is removed from each person's task list
        for (Person person : model.getFilteredPersonList()) {
            assertFalse(person.hasTask(taskToDelete), "Person should no longer have the deleted task.");
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, ()
                -> deleteTaskCommand.execute(model));
    }

    @Test
    public void execute_personListUpdatedIfTaskAssignedToPerson() throws Exception {
        model.addTask(new Todo("buy groceries"));
        Person newPerson = new PersonBuilder().withName("test").withTasks("todo: buy groceries").build();
        model.addPerson(newPerson);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);

        deleteTaskCommand.execute(model);

        // Verify that the person list update is only triggered if any person had the task
        boolean anyPersonHadTask = false;
        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToDelete)) {
                anyPersonHadTask = true;
                break;
            }
        }

        if (anyPersonHadTask) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        model.deletePerson(newPerson);
    }

    @Test
    public void execute_taskNotAssignedToPerson_noChangeToPerson() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Person personWithoutTask = new PersonBuilder().withName("Unrelated Person").build();
        model.addPerson(personWithoutTask);

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        deleteTaskCommand.execute(model);

        // Verify that the unrelated person remains unchanged
        assertFalse(personWithoutTask.hasTask(taskToDelete), "Person without task should remain unaffected.");
    }

    @Test
    public void execute_taskNotAssignedToAnyPerson_success() throws Exception {
        Task unassignedTask = new Todo("unassigned task");
        model.addTask(unassignedTask);
        Index unassignedTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(unassignedTaskIndex);
        CommandResult result = deleteTaskCommand.execute(model);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, unassignedTask);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify task is deleted from the model
        assertFalse(model.getFilteredTaskList().contains(unassignedTask),
                "The task should be removed from the model.");
    }

    @Test
    public void execute_taskRemovedFromAllRelevantPersons() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        Person personWithTask = new PersonBuilder().withName("task person").withTasks("todo: buy groceries").build();
        Person personWithoutTask = new PersonBuilder().withName("Unrelated Person").build();
        model.addPerson(personWithTask);
        model.addPerson(personWithoutTask);

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        deleteTaskCommand.execute(model);

        // Verify the task is removed from person with the task
        assertFalse(personWithTask.hasTask(taskToDelete), "Person with the task should have it removed.");

        // Verify the unrelated person remains unaffected
        assertFalse(personWithoutTask.hasTask(taskToDelete), "Person without task should remain unaffected.");

        model.deletePerson(personWithTask);
        model.deletePerson(personWithoutTask);
    }



    @Test
    public void toString_validIndex_returnsCorrectString() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST);
        String expectedString = "seedu.address.logic.commands.task."
                + "DeleteTaskCommand{targetIndex=seedu.address.commons.core.index.Index{zeroBasedIndex=0}}";
        assertEquals(expectedString, deleteTaskCommand.toString());
    }

    @Test
    public void equals() {
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
