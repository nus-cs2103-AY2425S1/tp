package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class ListIncompleteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noTasks_listIsEmpty() {
        // Ensure no tasks are in the filtered list
        model.updateFilteredTaskList(task -> false);
        assertCommandSuccess(new ListIncompleteCommand(), model, ListIncompleteCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_tasksPresent_filtersOnlyIncomplete() {
        Task incompleteTask = new TaskBuilder().withDescription("Incomplete Task").build();
        Task completeTask = new TaskBuilder().withDescription("Complete Task").markTaskComplete().build();
        model.addTask(incompleteTask);
        model.addTask(completeTask);

        expectedModel.addTask(incompleteTask);
        expectedModel.addTask(completeTask);
        // Expect only incomplete tasks
        expectedModel.updateFilteredTaskList(task -> !task.isCompleteProperty().get());

        assertCommandSuccess(new ListIncompleteCommand(), model, ListIncompleteCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getFilteredTaskList().contains(completeTask));
        assertEquals(model.getFilteredTaskList(), expectedModel.getFilteredTaskList());
    }


}
