package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.OTHER_VALID_GROUP_NAME;
import static seedu.address.logic.commands.CommandTestUtil.OTHER_VALID_STUDENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;

public class GroupCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validGroup_success() {
        List<String> studentsNames = List
                .of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName().fullName);
        GroupCommand groupCommand = new GroupCommand("StudyGroup1", studentsNames);

        String expectedMessage = String.format(GroupCommand.MESSAGE_SUCCESS, "StudyGroup1", studentsNames.size());

        List<Person> students = List.of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        expectedModel.addGroup(new Group("StudyGroup1", students));

        assertCommandSuccess(groupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroup_failure() {
        List<String> studentsNames = List
                .of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName().fullName);
        GroupCommand groupCommand = new GroupCommand("StudyGroup1", studentsNames);
        List<Person> students = List.of(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        model.addGroup(new Group("StudyGroup1", students));

        String expectedMessage = GroupCommand.MESSAGE_DUPLICATE_GROUP;

        assertCommandFailure(groupCommand, model, expectedMessage);
    }

    @Test
    public void execute_noMatchingStudents_failure() {
        List<String> students = List.of(); // Empty list of students
        GroupCommand groupCommand = new GroupCommand("StudyGroup1", students);

        String expectedMessage = GroupCommand.MESSAGE_NO_STUDENTS_FOUND;

        assertCommandFailure(groupCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateStudentNamesInInput_throwsCommandException() {
        // Prepare duplicate student names
        String duplicateStudentName = model.getFilteredPersonList().get(0).getName().fullName;
        List<String> studentNames = List.of(duplicateStudentName, duplicateStudentName);
        String groupName = "StudyGroup2";

        GroupCommand groupCommand = new GroupCommand(groupName, studentNames);

        String expectedMessage = String.format(GroupCommand.DUPLICATE_STUDENT_FOUND, duplicateStudentName);

        assertCommandFailure(groupCommand, model, expectedMessage);
    }

    @Test
    public void execute_emptyStudentName_throwsCommandException() {
        // Prepare student names with an empty string
        List<String> studentNames = List.of("");
        String groupName = "StudyGroup4";

        GroupCommand groupCommand = new GroupCommand(groupName, studentNames);

        assertCommandFailure(groupCommand, model, GroupCommand.EMPTY_STUDENT);
    }

    @Test
    public void execute_noMatchingStudentsFound_throwsCommandException() {
        // Prepare student names that do not match any existing person
        List<String> studentNames = List.of("NonExistentStudent");
        String groupName = "StudyGroup5";

        GroupCommand groupCommand = new GroupCommand(groupName, studentNames);

        assertCommandFailure(groupCommand, model, String.format(GroupCommand.STUDENTS_NOT_FOUND,
                "NonExistentStudent"));
    }

    @Test
    public void execute_partialMatchingStudentsFound_throwsCommandException() {
        // Prepare a mix of valid and invalid student names
        String validStudentName = model.getFilteredPersonList().get(0).getName().fullName;
        String invalidStudentName = "NonExistentStudent";
        List<String> studentNames = List.of(validStudentName, invalidStudentName);
        String groupName = "StudyGroup6";

        GroupCommand groupCommand = new GroupCommand(groupName, studentNames);

        String expectedMessage = String.format(GroupCommand.STUDENTS_NOT_FOUND, invalidStudentName);

        assertCommandFailure(groupCommand, model, expectedMessage);
    }

    @Test
    public void constructor_nullGroupName_throwsNullPointerException() {
        List<String> studentNames = new ArrayList<>();
        Assert.assertThrows(NullPointerException.class, () -> new GroupCommand(null, studentNames));
    }

    @Test
    public void constructor_nullStudentsList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GroupCommand("GroupName", null));
    }

    @Test
    public void execute_emptyStudentsList_throwsCommandException() {
        List<String> studentNames = new ArrayList<>();
        String groupName = "StudyGroup7";

        GroupCommand groupCommand = new GroupCommand(groupName, studentNames);

        assertCommandFailure(groupCommand, model, GroupCommand.MESSAGE_NO_STUDENTS_FOUND);
    }

    @Test
    public void equals() {
        final GroupCommand standardCommand = new GroupCommand(VALID_GROUP_NAME, VALID_STUDENTS);

        // same values -> returns true
        GroupCommand commandWithSameValues = new GroupCommand(VALID_GROUP_NAME, VALID_STUDENTS);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different groupname -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(OTHER_VALID_GROUP_NAME, VALID_STUDENTS)));

        // different students -> returns false
        assertFalse(standardCommand.equals(new GroupCommand(VALID_GROUP_NAME, OTHER_VALID_STUDENTS)));
    }
}
