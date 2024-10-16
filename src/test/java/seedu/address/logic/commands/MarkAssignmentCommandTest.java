package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.StudentBuilder;


public class MarkAssignmentCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentAndAssignmentIndex_markSuccessful() {
        Assignment assignment = new AssignmentBuilder()
                .withAssignmentName("Math Homework")
                .withMaxScore(100)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(
                INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT);
        String expectedMessage = String.format(MarkAssignmentCommand.MESSAGE_MARK_SUCCESS,
                "Math Homework", student.getName());
        Model expectedModel = new ModelManager(new AddressBook(), model.getUserPrefs());
        Assignment expectedAssignment = new AssignmentBuilder()
                .withAssignmentName("Math Homework")
                .withMaxScore(100)
                .withHasSubmitted(true)
                .build();
        Student expectedStudent = new StudentBuilder().withAssignments(new ArrayList<>()).build()
                .addAssignment(expectedAssignment);
        expectedModel.addStudent(expectedStudent);
        assertCommandSuccess(markAssignmentCommand, model, expectedMessage, expectedModel);
        assertTrue(student.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased()).getHasSubmitted());
    }

    @Test
    public void execute_invalidStudentIndex_markFailure() {
        Assignment assignment = new AssignmentBuilder()
                .withAssignmentName("Math Homework")
                .withMaxScore(100)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(
                Index.fromOneBased(2),
                INDEX_FIRST_ASSIGNMENT);
        String expectedMessage = Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
        assertCommandFailure(markAssignmentCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidAssignmentIndex_markFailure() {
        Assignment assignment = new AssignmentBuilder()
                .withAssignmentName("Math Homework")
                .withMaxScore(100)
                .build();
        Student student = new StudentBuilder().withAssignments(new ArrayList<>()).build().addAssignment(assignment);
        model.addStudent(student);
        MarkAssignmentCommand markAssignmentCommand = new MarkAssignmentCommand(
                INDEX_FIRST_STUDENT,
                Index.fromOneBased(2));
        String expectedMessage = Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX;
        assertCommandFailure(markAssignmentCommand, model, expectedMessage);
    }
}
