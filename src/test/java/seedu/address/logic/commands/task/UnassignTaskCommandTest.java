package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        // Use a valid vendor but with an invalid task index
        UnassignTaskCommand command = new UnassignTaskCommand(Index.fromZeroBased(0), Set.of(Index.fromOneBased(10)));

        // Command should throw a CommandException for invalid task index
        assertThrows(CommandException.class, MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () -> command.execute(model));
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
        assertTrue(command1.equals(command1));

        // same values -> returns true
        UnassignTaskCommand command1Copy = new UnassignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different values -> returns false
        assertFalse(command1.equals(command2));
    }
}
