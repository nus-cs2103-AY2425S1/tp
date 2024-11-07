package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
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
import seedu.address.testutil.PersonBuilder;

public class UnmarkTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<Task> taskList = getTypicalTasks();
        for (Task task : taskList) {
            task.markAsDone();
            model.addTask(task);
        }
        Person personWithTask = new PersonBuilder().withTasks("todo: buy groceries").build();
        model.addPerson(personWithTask);
        model.assignVendor(personWithTask);
    }

    @Test
    public void execute_validTaskIndex_unmarksTaskSuccessfully() throws Exception {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        UnmarkTaskCommand command = new UnmarkTaskCommand(Set.of(INDEX_FIRST));

        CommandResult result = command.execute(model);

        assertEquals(String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS, Set.of(taskToUnmark)),
                result.getFeedbackToUser());
        assertFalse(taskToUnmark.getIsDone(), "The task should be unmarked as not done.");
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        UnmarkTaskCommand command = new UnmarkTaskCommand(Set.of(outOfBoundIndex));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX
                        + ": " + (model.getFilteredTaskList().size() + 1), () -> command.execute(model));
    }

    @Test
    public void execute_taskAlreadyUnmarked_throwsErrir() throws Exception {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        taskToUnmark.markAsUndone();

        UnmarkTaskCommand command = new UnmarkTaskCommand(Set.of(INDEX_FIRST));

        assertCommandFailure(command, model, Messages.MESSAGE_TASK_ALREADY_UNCOMPLETED);
    }

    @Test
    public void execute_taskAssignedToPerson_updatesPersonWithUnmarkedTask() throws Exception {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        UnmarkTaskCommand command = new UnmarkTaskCommand(Set.of(INDEX_FIRST));
        command.execute(model);

        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToUnmark)) {
                assertFalse(person.getTask(taskToUnmark).getIsDone(),
                        "Person should have the task unmarked as not done.");
            }
        }
    }

    @Test
    public void equals() {
        UnmarkTaskCommand command1 = new UnmarkTaskCommand(Set.of(INDEX_FIRST));
        UnmarkTaskCommand command2 = new UnmarkTaskCommand(Set.of(INDEX_SECOND));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        UnmarkTaskCommand command1Copy = new UnmarkTaskCommand(Set.of(INDEX_FIRST));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different task indexes -> returns false
        assertFalse(command1.equals(command2));
    }

    @AfterEach
    public void tearDown() {
        for (Task task : model.getFilteredTaskList()) {
            task.markAsUndone();
        }
    }
}
