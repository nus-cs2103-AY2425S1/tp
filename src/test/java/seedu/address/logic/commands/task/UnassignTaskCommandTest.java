package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_TASK_NOT_FOUND_IN_CONTACT;
import static seedu.address.logic.Messages.MESSAGE_UNASSIGN_TASK_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;

public class UnassignTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<Task> taskList = getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
        model.addPerson(CARL);
        model.assignVendor(CARL);
    }

    @Test
    public void execute_validVendorAndTask_success() throws Exception {
        Person validPerson = CARL;
        Task taskToUnassign = validPerson.getTasks().iterator().next();

        UnassignTaskCommand command = new UnassignTaskCommand(Index.fromZeroBased(0), Set.of(INDEX_FIRST));

        CommandResult result = command.execute(model);

        String removedTasks = taskToUnassign.toString().replaceAll("[\\[\\]]", "");
        String expectedMessage = String.format(MESSAGE_UNASSIGN_TASK_SUCCESS, removedTasks, validPerson.getName());

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // Set up a command with an invalid person index
        UnassignTaskCommand command = new UnassignTaskCommand(Index.fromOneBased(10), Set.of(INDEX_FIRST));

        // Command should throw a CommandException for invalid person index
        assertThrows(CommandException.class, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                1, model.getFilteredPersonList().size()), () -> command.execute(model));
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        // Use a valid vendor but with an invalid task index
        UnassignTaskCommand command = new UnassignTaskCommand(Index.fromZeroBased(0),
                Set.of(Index.fromOneBased(10)));

        // Command should throw a CommandException for invalid task index
        assertThrows(
                CommandException.class,
                String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, 10, 1, 1), () -> command.execute(model)
        );
    }

    @Test
    public void execute_taskNotAssigned_throwsCommandException() {
        Person personWithNoTasks = new PersonBuilder().withName("No Task Person").build();
        model.addPerson(personWithNoTasks);

        UnassignTaskCommand command = new UnassignTaskCommand(Index.fromZeroBased(1), Set.of(INDEX_FIRST));

        // Command should throw a CommandException for task not found
        assertThrows(CommandException.class, MESSAGE_TASK_NOT_FOUND_IN_CONTACT, () -> command.execute(model));
    }

    @Test
    public void equals() {
        UnassignTaskCommand command1 = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        UnassignTaskCommand command2 = new UnassignTaskCommand(INDEX_SECOND, Set.of(INDEX_SECOND));

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        UnassignTaskCommand command1Copy = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        assertEquals(command1, command1Copy);

        // null -> returns false
        assertNotEquals(null, command1);

        // different values -> returns false
        assertNotEquals(command1, command2);

        //different type -> false
        UnassignTaskCommand command = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        String differentType = "not a command";
        assertNotEquals(command, differentType);
    }

    @Test
    public void execute_taskNotFoundInPerson_throwsCommandException() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personWithDifferentTask = new PersonBuilder().withName("Person with no Task").build();
        model.setPerson(firstPerson, personWithDifferentTask);
        Task unrelatedTask = new Task("Unrelated Task");
        model.addTask(unrelatedTask);

        // try to unassign a task that the person does not have
        UnassignTaskCommand command = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        assertThrows(CommandException.class, MESSAGE_TASK_NOT_FOUND_IN_CONTACT, () -> command.execute(model));
    }
}
