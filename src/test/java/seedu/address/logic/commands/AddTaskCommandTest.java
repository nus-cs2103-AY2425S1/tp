package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {

    private final ModelManager model = new ModelManager();

    @Test
    public void constructor_nullTaskDescription_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new AddTaskCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, "Buy medication"));
    }

    @Test
    public void execute_indexDoesNotExist_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTaskCommand addTaskCommand = new AddTaskCommand(outOfBoundIndex, "Take medication");

        assertThrows(CommandException.class,
                String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX), () ->
                        addTaskCommand.execute(model));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Person validPerson = new PersonBuilder().build();
        Task validTask = new Task(validPerson, "Buy medication");
        modelStub.addPerson(validPerson);
        Index index = Index.fromOneBased(1);

        AddTaskCommand addTaskCommand = new AddTaskCommand(index, validTask.getDescription());

        CommandResult commandResult = addTaskCommand.execute(modelStub);

        modelStub.addPerson(validPerson);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask.getDescription()),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();

        Person validPerson = new PersonBuilder().withName("Amy Bee").build();

        modelStub.addPerson(validPerson);

        Task validTask = new Task(validPerson, "Buy medication");

        modelStub.addTask(validTask);

        Index index = Index.fromOneBased(1);

        AddTaskCommand addTaskCommand = new AddTaskCommand(index, validTask.getDescription());

        assertThrows(CommandException.class, AddTaskCommand.MESSAGE_DUPLICATE_TASK, () ->
                addTaskCommand.execute(modelStub));
    }


    @Test
    public void equals() {
        Task task1 = new TaskBuilder().withDescription("Buy meds").build();
        Task task2 = new TaskBuilder().withDescription("Visit hospital").build();

        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        AddTaskCommand addTask1Command = new AddTaskCommand(index1, task1.getDescription());
        AddTaskCommand addTask2Command = new AddTaskCommand(index2, task2.getDescription());

        // same object -> returns true
        assertTrue(addTask1Command.equals(addTask1Command));

        // same values -> returns true
        AddTaskCommand addTask1CommandCopy = new AddTaskCommand(index1, task1.getDescription());
        assertTrue(addTask1Command.equals(addTask1CommandCopy));

        // different types -> returns false
        assertFalse(addTask1Command.equals(1));

        // null -> returns false
        assertFalse(addTask1Command.equals(null));

        // different task -> returns false
        assertFalse(addTask1Command.equals(addTask2Command));

    }

    @Test
    public void equals_sameTaskDescriptionDifferentIndex_returnsFalse() {
        Task task1 = new TaskBuilder().withDescription("Buy meds")
                .withPatient(new PersonBuilder().withName("Alice").build()).build();
        Task task2 = new TaskBuilder().withDescription("Buy meds")
                .withPatient(new PersonBuilder().withName("Bob").build()).build();

        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);

        AddTaskCommand addTask1Command = new AddTaskCommand(index1, task1.getDescription());
        AddTaskCommand addTask2Command = new AddTaskCommand(index2, task2.getDescription());

        // Different personName should return false
        assertFalse(addTask1Command.equals(addTask2Command));
    }

    @Test
    public void equals_sameIndexDifferentDescriptionObjects_returnsTrue() {
        Task task1 = new TaskBuilder().withDescription("Buy meds")
                .withPatient(new PersonBuilder().withName("Alice").build()).build();
        Task task2 = new TaskBuilder().withDescription("Buy meds")
                .withPatient(new PersonBuilder().withName("Alice").build()).build();

        Index index1 = Index.fromOneBased(1);

        AddTaskCommand addTask1Command = new AddTaskCommand(index1, task1.getDescription());
        AddTaskCommand addTask2Command = new AddTaskCommand(index1, task2.getDescription());

        // Different Person objects but same name, should return true
        assertTrue(addTask1Command.equals(addTask2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssociatedTasks(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetPersonPriority(Person target) {
            // No operation needed for this stub; this line is necessary to satisfy the interface.
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTasksForPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasPerson(Person person) {
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(personsAdded);
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(t -> t.equals(task));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
