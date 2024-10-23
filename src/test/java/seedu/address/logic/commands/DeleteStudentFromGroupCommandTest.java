package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.State;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletecommands.DeleteStudentFromGroupCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentFromGroupCommand}.
 */
public class DeleteStudentFromGroupCommandTest {
    private static final Group validGroup = new Group(new GroupName("Team 1"));
    private static final Student validStudent = new PersonBuilder().build();
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelStubDeleteStudentFromGroup();
    }

    @Test
    public void execute_studentExistsInGroup_success() throws CommandException {
        Model model = new ModelManager();
        model.addPerson(validStudent);
        model.addGroup(validGroup);
        model.addPersonToGroup(validStudent, validGroup);
        DeleteStudentFromGroupCommand command = new DeleteStudentFromGroupCommand(validGroup.getGroupName(),
            validStudent.getStudentNumber());
        CommandResult commandResult = command.execute(model);
        assertEquals(String.format(DeleteStudentFromGroupCommand.MESSAGE_DELETE_PERSON_SUCCESS,
            validStudent.getStudentNumber(), validGroup.getGroupName()), commandResult.getFeedbackToUser());
        assertFalse(model.hasPersonInGroup(validStudent, validGroup));
    }

    @Test
    public void execute_studentDoesNotExistInGroup_throwsCommandException() {
        DeleteStudentFromGroupCommand command = new DeleteStudentFromGroupCommand(validGroup.getGroupName(),
            new StudentNumber("A0123456Q"));

        assertThrows(CommandException.class, Messages.MESSAGE_STUDENT_NO_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void execute_groupDoesNotExist_throwsCommandException() {
        DeleteStudentFromGroupCommand command = new DeleteStudentFromGroupCommand(new GroupName("Team 5"),
            validStudent.getStudentNumber());
        assertThrows(CommandException.class, Messages.MESSAGE_GROUP_NAME_NOT_FOUND, () -> command.execute(model));
    }

    @Test
    public void equals() {
        GroupName teamOneName = new GroupName("Team 1");
        GroupName teamTwoName = new GroupName("Team 2");
        StudentNumber studentNumberOne = new StudentNumber("A0123456Z");
        StudentNumber studentNumberTwo = new StudentNumber("A0654321Z");

        DeleteStudentFromGroupCommand deleteStudentOneFromTeamOneCommand =
            new DeleteStudentFromGroupCommand(teamOneName, studentNumberOne);
        DeleteStudentFromGroupCommand deleteStudentTwoFromTeamOneCommand =
            new DeleteStudentFromGroupCommand(teamOneName, studentNumberTwo);
        DeleteStudentFromGroupCommand deleteStudentOneFromTeamTwoCommand =
            new DeleteStudentFromGroupCommand(teamTwoName, studentNumberOne);

        assertTrue(deleteStudentOneFromTeamOneCommand.equals(deleteStudentOneFromTeamOneCommand));
        DeleteStudentFromGroupCommand deleteStudentOneFromTeamOneCommandCopy =
            new DeleteStudentFromGroupCommand(teamOneName, studentNumberOne);
        assertTrue(deleteStudentOneFromTeamOneCommand.equals(deleteStudentOneFromTeamOneCommandCopy));
        assertFalse(deleteStudentOneFromTeamOneCommand.equals(1));
        assertFalse(deleteStudentOneFromTeamOneCommand.equals(null));
        assertFalse(deleteStudentOneFromTeamOneCommand.equals(deleteStudentTwoFromTeamOneCommand));
        assertFalse(deleteStudentOneFromTeamOneCommand.equals(deleteStudentOneFromTeamTwoCommand));
    }

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
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

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
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
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
        public void addPerson(Student student) {
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
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(Group target, Group editedGroup) {
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

    private class ModelStubDeleteStudentFromGroup extends ModelStub {
        private final ArrayList<Group> groups = new ArrayList<Group>();
        private final ArrayList<Student> students = new ArrayList<Student>();

        ModelStubDeleteStudentFromGroup() {
            validGroup.add(validStudent);
            this.groups.add(validGroup);
            this.students.add(validStudent);
        }

        @Override
        public boolean containsGroupName(GroupName groupName) {
            return groups.stream().anyMatch(group -> group.getGroupName().equals(groupName));
        }

        @Override
        public void deleteStudentFromGroup(Group group, Student student) {
            group.delete(student);
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            for (Group group : groups) {
                if (groupName.equals(group.getGroupName())) {
                    return group;
                }
            }
            throw new GroupNotFoundException();
        }
    }


}
