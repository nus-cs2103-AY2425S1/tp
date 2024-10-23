package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.DEADLINE_TASK;
import static seedu.address.testutil.TypicalTasks.EVENT_TASK;
import static seedu.address.testutil.TypicalTasks.TODO_TASK;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateTaskCommand}.
 */
public class CreateTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_addValidTask_success() throws Exception {
        // Prepare a set of tasks to add
        HashSet<Task> tasksToAdd = new HashSet<>();
        tasksToAdd.add(TODO_TASK);
        tasksToAdd.add(DEADLINE_TASK);

        // Create a CreateTaskCommand with valid tasks
        CreateTaskCommand command = new CreateTaskCommand(tasksToAdd);

        String expectedMessage = String.format(CreateTaskCommand.MESSAGE_SUCCESS, tasksToAdd);

        CommandResult result = command.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        // Add a task to the model to simulate a duplicate
        model.addTask(TODO_TASK);

        // Prepare a set of tasks with a duplicate task
        HashSet<Task> tasksToAdd = new HashSet<>();
        tasksToAdd.add(TODO_TASK);

        CreateTaskCommand command = new CreateTaskCommand(tasksToAdd);

        assertThrows(CommandException.class, () -> command.execute(model), CreateTaskCommand.MESSAGE_DUPLICATE_TASK);
    }
    @Test
    public void getTaskToAdd_validTaskSet_returnsCorrectSet() {
        // Prepare a set of tasks to add
        HashSet<Task> tasksToAdd = new HashSet<>();
        tasksToAdd.add(TODO_TASK);

        CreateTaskCommand command = new CreateTaskCommand(tasksToAdd);

        assertEquals(tasksToAdd, command.getTaskToAdd());
    }
    @Test
    public void toString_validTaskSet_returnsCorrectString() {
        // Prepare a set of tasks to add
        HashSet<Task> tasksToAdd = new HashSet<>();
        tasksToAdd.add(TODO_TASK);

        CreateTaskCommand command = new CreateTaskCommand(tasksToAdd);

        String expectedString = "seedu.address.logic.commands.CreateTaskCommand{taskToAdd=[[T][ ] Buy groceries]}";
        assertEquals(expectedString, command.toString());
    }

    @Test
    public void equals() {
        HashSet<Task> taskSet1 = new HashSet<>();
        taskSet1.add(TODO_TASK);

        HashSet<Task> taskSet2 = new HashSet<>();
        taskSet2.add(EVENT_TASK);

        CreateTaskCommand addFirstTaskCommand = new CreateTaskCommand(taskSet1);
        CreateTaskCommand addSecondTaskCommand = new CreateTaskCommand(taskSet2);

        // same object -> returns true
        assertEquals(addFirstTaskCommand, addFirstTaskCommand);

        // same values -> returns true
        CreateTaskCommand addFirstTaskCommandCopy = new CreateTaskCommand(taskSet1);
        assertEquals(addFirstTaskCommand, addFirstTaskCommandCopy);

        // different types -> returns false
        assertFalse(addFirstTaskCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstTaskCommand.equals(null));

        // different task sets -> returns false
        assertFalse(addFirstTaskCommand.equals(addSecondTaskCommand));
    }

}
