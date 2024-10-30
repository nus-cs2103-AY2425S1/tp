package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static keycontacts.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.logic.Messages;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.UserPrefs;
import keycontacts.model.student.Group;
import keycontacts.model.student.Student;
import keycontacts.testutil.EditStudentDescriptorBuilder;
import keycontacts.testutil.StudentBuilder;

/**
 * Contains testing of lesson sync across groups for the different commands.
 * All tests related to lesson syncing should be found here. Functionality that does not involve syncing should be
 * tested with the respective commands.
 */
public class GroupSyncTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    }

    @Test
    public void addCommandExecute_addStudentToGroup_success() {
        Student firstStudent = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        Student validStudentWithGroup = new StudentBuilder().withGroup(firstStudent.getGroup().groupName).build();

        Student validStudentSynced = validStudentWithGroup.withLessons(firstStudent.getRegularLesson(),
                firstStudent.getCancelledLessons(), firstStudent.getMakeupLessons());
        Model expectedModel = new ModelManager(model.getStudentDirectory(), new UserPrefs());
        expectedModel.addStudent(validStudentSynced);

        assertCommandSuccess(new AddCommand(validStudentWithGroup), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validStudentWithGroup)),
                expectedModel);
    }

    @Test
    public void editCommandExecute_editStudentToGroup_success() {
        Student firstStudent = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student secondStudent = model.getStudentList().get(INDEX_SECOND_STUDENT.getZeroBased());

        Student editedFirstStudent = new StudentBuilder(firstStudent).withGroup(secondStudent.getGroup().groupName)
                .build();

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedFirstStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT, descriptor);

        Student firstStudentSynced = editedFirstStudent.withLessons(secondStudent.getRegularLesson(),
                secondStudent.getCancelledLessons(), secondStudent.getMakeupLessons());
        Model expectedModel = new ModelManager(model.getStudentDirectory(), new UserPrefs());
        expectedModel.setStudent(firstStudent, firstStudentSynced);


        assertCommandSuccess(editCommand, model,
                String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedFirstStudent)),
                expectedModel);
    }

    @Test
    public void editCommandExecute_removeStudentFromGroup_success() {
        Student firstStudent = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());

        Student editedFirstStudent = new StudentBuilder(firstStudent).withGroup(Group.NO_GROUP_STRING).build();

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedFirstStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT, descriptor);

        Student firstStudentSynced = editedFirstStudent.withLessons(null, new HashSet<>(), new HashSet<>());
        Model expectedModel = new ModelManager(model.getStudentDirectory(), new UserPrefs());
        expectedModel.setStudent(firstStudent, firstStudentSynced);

        assertCommandSuccess(editCommand, model,
                String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(editedFirstStudent)),
                expectedModel);
    }

    @Test
    public void editCommandExecute_createNewGroupForStudentInNoGroup_success() {
        // setup
        Student firstStudent = model.getStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student firstStudentWithNoGroup = new StudentBuilder(firstStudent).withGroup(Group.NO_GROUP_STRING).build();
        model.setStudent(firstStudent, firstStudentWithNoGroup);

        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDENT, descriptor);

        Model expectedModel = new ModelManager(model.getStudentDirectory(), new UserPrefs());
        expectedModel.setStudent(firstStudentWithNoGroup, firstStudent);

        assertCommandSuccess(editCommand, model,
                String.format(EditCommand.MESSAGE_EDIT_STUDENT_SUCCESS, Messages.format(firstStudent)),
                expectedModel);
    }
}
