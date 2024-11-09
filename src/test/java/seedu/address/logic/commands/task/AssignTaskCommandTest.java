package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.logic.Messages;
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

        String addedTasks = taskToAssign.toString().replaceAll("[\\[\\]]", "");
        String expectedMessage = String.format(
                Messages.MESSAGE_ASSIGN_TASK_SUCCESS, addedTasks, validPerson.getName()
        );

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // Arrange: Set up a command with an invalid person index
        AssignTaskCommand command = new AssignTaskCommand(Index.fromOneBased(10), Set.of(INDEX_FIRST));

        // Assert: Command should throw a CommandException for invalid person index
        assertThrows(
                CommandException.class, String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        1, model.getFilteredPersonList().size()), () -> command.execute(model)
        );
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
        assertThrows(
                CommandException.class, String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX,
                        10, 1, model.getFilteredTaskList().size()), () -> command.execute(model)
        );
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
        assertThrows(CommandException.class, String.format(Messages.MESSAGE_DUPLICATE_TASK_IN_PERSON,
                taskAlreadyAssigned.toString(), CARL.getName()), () -> command.execute(model));
    }

    @Test
    public void execute_personIsNotVendor_throwsCommandException() {
        // Set up a valid person who is not a vendor
        Person nonVendorPerson = new PersonBuilder().withName("Non Vendor").build();
        model.addPerson(nonVendorPerson);

        // Try to assign a task to a non-vendor person
        AssignTaskCommand command = new AssignTaskCommand(Index.fromZeroBased(0), Set.of(INDEX_FIRST));

        // Command should throw a CommandException for non-vendor person
        assertThrows(CommandException.class, String.format(Messages.MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK,
                nonVendorPerson.getName()), () -> command.execute(model));
    }

    @Test
    public void equals() {
        AssignTaskCommand command1 = new AssignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        AssignTaskCommand command2 = new AssignTaskCommand(INDEX_SECOND, Set.of(INDEX_SECOND));

        // same object -> returns true
        assertEquals(command1, command1);

        // same values -> returns true
        AssignTaskCommand command1Copy = new AssignTaskCommand(INDEX_FIRST, Set.of(INDEX_FIRST));
        assertEquals(command1, command1Copy);

        // null -> returns false
        assertNotEquals(null, command1);

        // different values -> returns false
        assertNotEquals(command1, command2);
    }
}
