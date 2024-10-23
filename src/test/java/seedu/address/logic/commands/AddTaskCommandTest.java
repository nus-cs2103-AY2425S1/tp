package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTaskCommand.
 */
public class AddTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullNameOrTask_throwsNullPointerException() {
        Task validTask = new TaskBuilder().build();
        Name validName = new Name("John Doe");

        // Test null task
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(validName, null));
        // Test null name
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, validTask));
    }

    // Update using new PersonBuilder
    @Test
    public void execute_taskAcceptedByModel_addSuccessful() {
        // Get an existing person from the typical address book
        Person person = new PersonBuilder(model.getAddressBook().getPersonList().get(0)).build(); // Deep copy

        Task validTask = new TaskBuilder().build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(person.getName(), validTask);
        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS,
                validTask.getTaskDescription(), person.getName(), validTask.getTaskDeadline());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person updatedPerson = new PersonBuilder(person).build(); // Create a deep copy
        updatedPerson.getTaskList().add(validTask); // Modify the deep copy
        expectedModel.setPerson(person, updatedPerson);

        // Checks that initial and expected state of model is correct
        assertEquals(model, new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertNotEquals(expectedModel, model);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        // Get an existing person with the default task
        Person person = model.getAddressBook().getPersonList().get(1);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Task validTask = new TaskBuilder().build();
        Name validName = person.getName();
        AddTaskCommand addTaskCommand = new AddTaskCommand(validName, validTask);

        assertThrows(CommandException.class,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK, () -> addTaskCommand.execute(expectedModel));
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        // Get an existing person with the default task
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Task validTask = new TaskBuilder().build();
        Name validName = new Name("Darren Watkins Junior");
        AddTaskCommand addTaskCommand = new AddTaskCommand(validName, validTask);

        assertThrows(CommandException.class,
                AddTaskCommand.MESSAGE_PERSON_NOT_FOUND, () -> addTaskCommand.execute(expectedModel));
    }

    @Test
    public void equals() {
        Task task1 = new TaskBuilder().withTaskDescription("Homework").build();
        Task task2 = new TaskBuilder().withTaskDescription("Chores").build();
        Name name1 = new Name("John Doe");
        Name name2 = new Name("Jane Doe");
        AddTaskCommand addTask1Command = new AddTaskCommand(name1, task1);
        AddTaskCommand addTask2Command = new AddTaskCommand(name2, task2);

        // same object -> returns true
        assertTrue(addTask1Command.equals(addTask1Command));

        // same values -> returns true
        AddTaskCommand addTask1CommandCopy = new AddTaskCommand(name1, task1);
        assertTrue(addTask1Command.equals(addTask1CommandCopy));

        // different types -> returns false
        assertFalse(addTask1Command.equals(1));

        // null -> returns false
        assertFalse(addTask1Command.equals(null));

        // different task and name -> returns false
        assertFalse(addTask1Command.equals(addTask2Command));
    }

    @Test
    public void toStringMethod() {
        Name name = new Name("Test name");
        Task taskToAdd = new TaskBuilder().withTaskDescription("Test task").withTaskDeadline("2024-10-10").build();
        AddTaskCommand addTaskCommand = new AddTaskCommand(name, taskToAdd);
        String expected = AddTaskCommand.class.getCanonicalName() + "{name=" + name + ", taskToAdd=" + taskToAdd + "}";
        assertEquals(expected, addTaskCommand.toString());
    }
}
