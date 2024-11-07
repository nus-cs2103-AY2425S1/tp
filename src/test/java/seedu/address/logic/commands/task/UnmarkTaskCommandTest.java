package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                model.getFilteredTaskList().size() + 1,
                1, model.getFilteredTaskList().size()), () -> command.execute(model));
    }

    @Test
    public void execute_taskAlreadyUnmarked_doesNotChangeStatus() throws Exception {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        taskToUnmark.markAsUndone();

        UnmarkTaskCommand command = new UnmarkTaskCommand(Set.of(INDEX_FIRST));
        command.execute(model);

        assertFalse(taskToUnmark.getIsDone(), "The task should remain unmarked as not done.");
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
        assertEquals(command1, command1);

        // same values -> returns true
        UnmarkTaskCommand command1Copy = new UnmarkTaskCommand(Set.of(INDEX_FIRST));
        assertEquals(command1, command1Copy);

        // null -> returns false
        assertNotEquals(null, command1);

        // different task indexes -> returns false
        assertNotEquals(command1, command2);
    }

    @AfterEach
    public void tearDown() {
        for (Task task : model.getFilteredTaskList()) {
            task.markAsUndone();
        }
    }
}
