package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_ADD_TASK_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK;
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

public class AssignTaskCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        List<Task> taskList = getTypicalTasks();
        for (Task task : taskList) {
            model.addTask(task);
        }
    }

    @Test
    public void execute_validVendorAndTask_success() throws Exception {
        Person validPerson = new PersonBuilder().withName("John Doe").build();
        model.addPerson(validPerson);
        model.assignVendor(validPerson);

        Task taskToAssign = model.getFilteredTaskList().get(INDEX_FIRST.getZeroBased());
        AssignTaskCommand command = new AssignTaskCommand(Index.fromZeroBased(0), Set.of(INDEX_FIRST));

        CommandResult result = command.execute(model);

        String expectedMessage = String.format(MESSAGE_ADD_TASK_SUCCESS,
                taskToAssign.getDescription(), validPerson.getName());
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // Arrange: Set up a command with an invalid person index
        AssignTaskCommand command = new AssignTaskCommand(Index.fromOneBased(10), Set.of(INDEX_FIRST));

        // Assert: Command should throw a CommandException for invalid person index
        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() {
        // Arrange: Set up a valid vendor but with an invalid task index
        Person validPerson = new PersonBuilder().withName("Jane Doe").build();
        model.addPerson(validPerson);
        model.assignVendor(validPerson); // Assign the person as a vendor

        // Assign task with an invalid index (out of bounds)
        AssignTaskCommand command = new AssignTaskCommand(Index.fromZeroBased(0),
                Set.of(Index.fromOneBased(10)));

        // Assert: Command should throw a CommandException for invalid task index
        assertThrows(CommandException.class, MESSAGE_INVALID_TASK_DISPLAYED_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_taskAlreadyAssigned_throwsCommandException() {
        model.addPerson(CARL);
        model.assignVendor(CARL);

        // Get the task already assigned to Carl
        Task taskAlreadyAssigned = CARL.getTasks().iterator().next();

        // Try to assign the same task again
        AssignTaskCommand command = new AssignTaskCommand(Index.fromZeroBased(0), Set.of(INDEX_FIRST));

        // Assert: Command should throw a CommandException for duplicate task assignment
        assertThrows(CommandException.class, String.format(AssignTaskCommand.MESSAGE_DUPLICATE_TASK,
                taskAlreadyAssigned.getDescription(), CARL.getName()), () -> command.execute(model));
    }


    @Test
    public void execute_personIsNotVendor_throwsCommandException() {
        // Arrange: Set up a valid person who is not a vendor
        Person nonVendorPerson = new PersonBuilder().withName("Non Vendor").build();
        model.addPerson(nonVendorPerson);

        // Try to assign a task to a non-vendor person
        AssignTaskCommand command = new AssignTaskCommand(Index.fromZeroBased(0), Set.of(INDEX_FIRST));

        // Assert: Command should throw a CommandException for non-vendor person
        assertThrows(CommandException.class, MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK, () -> command.execute(model));
    }

    @Test
    public void equals() {
        AssignTaskCommand command1 = new AssignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        AssignTaskCommand command2 = new AssignTaskCommand(INDEX_SECOND, Set.of(INDEX_SECOND));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AssignTaskCommand command1Copy = new AssignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different values -> returns false
        assertFalse(command1.equals(command2));
    }
}