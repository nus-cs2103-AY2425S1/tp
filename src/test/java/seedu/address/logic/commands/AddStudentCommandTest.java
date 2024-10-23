package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.State;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.addcommands.AddStudentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;

public class AddStudentCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        Model model = new ModelManager();
        Student validStudent = new PersonBuilder().build();

        CommandResult commandResult = new AddStudentCommand(validStudent).execute(model);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
            commandResult.getFeedbackToUser());
        assertTrue(model.hasPerson(validStudent));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Student validStudent = new PersonBuilder().build();
        AddStudentCommand addCommand = new AddStudentCommand(validStudent);
        ModelStub modelStub = new ModelStubWithPerson(validStudent);

        assertThrows(CommandException.class, AddStudentCommand.MESSAGE_DUPLICATE_PERSON, () ->
            addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new PersonBuilder().withName("Alice").build();
        Student bob = new PersonBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddStudentCommand addCommand = new AddStudentCommand(ALICE);
        String expected = AddStudentCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getMostRecentGroupTaskDisplay() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMostRecentGroupTaskDisplay() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMostRecentGroupTaskDisplay(String string) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(Group target, Group updatedGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPersonToGroup(Student student, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonInGroup(Student student, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTaskInGroup(Task task, Group group) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasTask(Task task) {
            return false;
        }

        @Override
        public Student getPersonByNumber(StudentNumber studentNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortPersonList(Comparator<Student> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortGroupList(Comparator<Group> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortTaskList(Comparator<Task> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStateStudents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStateGroups() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStateGroupTask() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStateTasks() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public State getState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean containsGroupName(GroupName groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudentFromGroup(Group group, Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group groupToBeDeleted) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTaskToGroup(Task task, Group group) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteTaskFromGroup(Task task, Group group) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteTask(Task task) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void increaseGroupWithTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void decreaseGroupWithTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Student student;

        ModelStubWithPerson(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasPerson(Student student) {
            requireNonNull(student);
            return this.student.isSamePerson(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Student> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Student student) {
            requireNonNull(student);
            return personsAdded.stream().anyMatch(student::isSamePerson);
        }

        @Override
        public void addPerson(Student student) {
            requireNonNull(student);
            personsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
