package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;

public class MarkTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<Task> taskList = getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
        Person personWithTask = new PersonBuilder().withTasks("todo: buy groceries").build();
        Task task = new Task(new Description("buy groceries"));
        model.addTask(task);
        model.addPerson(personWithTask);
        model.assignVendor(personWithTask);

    }

    @Test
    public void execute_validTaskIndex_marksTaskSuccessfully() throws Exception {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        MarkTaskCommand command = new MarkTaskCommand(Set.of(INDEX_FIRST));

        CommandResult result = command.execute(model);

        assertEquals(String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, Set.of(taskToMark)),
                result.getFeedbackToUser());
        assertTrue(taskToMark.getIsDone(), "The task should be marked as done.");
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        MarkTaskCommand command = new MarkTaskCommand(Set.of(outOfBoundIndex));

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                model.getFilteredTaskList().size() + 1,
                1, model.getFilteredTaskList().size()), ()
                -> command.execute(model));
    }

    @Test
    public void execute_taskAlreadyMarked_throwsError() throws Exception {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        taskToMark.markAsDone();

        MarkTaskCommand command = new MarkTaskCommand(Set.of(INDEX_FIRST));

        assertCommandFailure(command, model, Messages.MESSAGE_TASK_ALREADY_COMPLETED);
    }

    @Test
    public void execute_taskAssignedToPerson_updatesPersonWithMarkedTask() throws Exception {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        MarkTaskCommand command = new MarkTaskCommand(Set.of(INDEX_FIRST));
        command.execute(model);

        for (Person person : model.getFilteredPersonList()) {
            if (person.hasTask(taskToMark)) {
                assertTrue(person.getTask(taskToMark).getIsDone(),
                        "Person should have the task marked as done.");
            }
        }
    }

    @Test
    public void equals() {
        MarkTaskCommand command1 = new MarkTaskCommand(Set.of(INDEX_FIRST));
        MarkTaskCommand command2 = new MarkTaskCommand(Set.of(INDEX_SECOND));

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        MarkTaskCommand command1Copy = new MarkTaskCommand(Set.of(INDEX_FIRST));
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

