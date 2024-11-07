package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalAssignments.DEADLINE_C;
import static seedu.address.testutil.TypicalAssignments.GRADE_90;
import static seedu.address.testutil.TypicalAssignments.GRADE_NULL;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalAssignments.STATUS_N;
import static seedu.address.testutil.TypicalAssignments.STATUS_Y;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

class EditAssignmentCommandTest {

    private Model model;
    private Student validStudent;
    private Assignment originalAssignment;
    private AssignmentQuery assignmentQuery;

    @BeforeEach
    void setUp() {
        // Create a valid student with an assignment
        model = new ModelManager(new AddressBook(), new UserPrefs());
        validStudent = new StudentBuilder(HUGH).build();
        originalAssignment = MATH_ASSIGNMENT_SUBMITTED;
        validStudent.addAssignment(originalAssignment);
        assignmentQuery = new AssignmentQuery(null, null, null, null);
        model.addStudent(validStudent);
    }

    @Test
    void execute_editAssignment_success() throws CommandException {
        // Create and execute the EditAssignmentCommand
        AssignmentQuery assignmentQuery = new AssignmentQuery(
                null, DEADLINE_C, STATUS_Y, GRADE_NULL);
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery);
        CommandResult result = command.execute(model);

        // Verify the command result
        assertEquals(String.format(EditAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                result.getFeedbackToUser());

        // Verify that the assignment was edited
        Assignment updatedAssignment = validStudent.getAssignments().get(0);
        assertEquals(DEADLINE_C, updatedAssignment.getDeadline());
        assertEquals(STATUS_Y, updatedAssignment.getSubmissionStatus());
        assertEquals(GRADE_NULL, updatedAssignment.getGrade());
    }

    @Test
    void execute_noStudentFound_throwsCommandException() {
        // Create the EditAssignmentCommand
        EditAssignmentCommand command = new EditAssignmentCommand(DIDDY.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                assignmentQuery);

        // Expect a CommandException due to no student found
        assertThrows(CommandException.class, () -> command.execute(model),
                EditAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);
    }

    @Test
    void execute_noAssignmentFound_throwsCommandException() {

        // Create an EditAssignmentCommand with a non-existent assignment
        AssignmentName nonExistentAssignmentName = new AssignmentName("Nonexistent Assignment");
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                nonExistentAssignmentName, assignmentQuery);

        // Expect a CommandException due to no assignment found
        assertThrows(CommandException.class, () -> command.execute(model),
                EditAssignmentCommand.MESSAGE_NO_ASSIGNMENT_FOUND);
    }

    @Test
    void execute_duplicateStudents_throwsCommandException() {
        // Mock model behavior for multiple students with the same name
        Student anotherStudent = new StudentBuilder(HUGH).withStudentNumber("A1234321B").build();
        model.addStudent(anotherStudent);

        // Create the EditAssignmentCommand
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery);

        // Expect a CommandException due to duplicate students
        assertThrows(CommandException.class, () -> command.execute(model),
                String.format(EditAssignmentCommand.MESSAGE_DUPLICATE_STUDENT,
                        HUGH.getStudentNumber().toString() + ", " + anotherStudent.getStudentNumber().toString()));
    }

    @Test
    void execute_specifyStudentNumber_success() throws CommandException {
        // Create and execute the EditAssignmentCommand
        AssignmentQuery assignmentQuery = new AssignmentQuery(
                null, DEADLINE_C, STATUS_Y, GRADE_NULL);
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery, HUGH.getStudentNumber());
        CommandResult result = command.execute(model);

        // Verify the command result
        assertEquals(String.format(EditAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                result.getFeedbackToUser());

        // Verify that the assignment was edited
        Assignment updatedAssignment = validStudent.getAssignments().get(0);
        assertEquals(DEADLINE_C, updatedAssignment.getDeadline());
        assertEquals(STATUS_Y, updatedAssignment.getSubmissionStatus());
        assertEquals(GRADE_NULL, updatedAssignment.getGrade());
    }

    @Test
    void execute_invalidAssignmentAfterEdit_throwsCommandException() {
        AssignmentQuery assignmentQuery1 = new AssignmentQuery(
                null, DEADLINE_C, STATUS_N, GRADE_90);
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery1);

        assertThrows(CommandException.class, () -> command.execute(model),
                String.format(EditAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT,
                        "not submitted", "ungraded"));
    }

    @Test
    void execute_invalidAssignmentAfterEdit_throwsCommandException2() {
        AssignmentQuery assignmentQuery1 = new AssignmentQuery(
                null, DEADLINE_C, STATUS_N, GRADE_90);
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery1, HUGH.getStudentNumber());

        assertThrows(CommandException.class, () -> command.execute(model),
                String.format(EditAssignmentCommand.MESSAGE_INVALID_ASSIGNMENT,
                        "not submitted", "ungraded"));
    }

    @Test
    void execute_undoEdit_success() throws CommandException {
        AssignmentQuery assignmentQuery1 = new AssignmentQuery(
                null, null, STATUS_Y, null);
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), assignmentQuery1, HUGH.getStudentNumber());
        command.execute(model);
        assertEquals(originalAssignment,
                new Assignment(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                        MATH_ASSIGNMENT_SUBMITTED.getDeadline(),
                        STATUS_Y,
                        MATH_ASSIGNMENT_SUBMITTED.getGrade()));

        command.undo(model);

        assertEquals(MATH_ASSIGNMENT_SUBMITTED, originalAssignment);
    }

    @Test
    void execute_noStudentFoundStudentNumberSpecified_throwsCommandException() {
        EditAssignmentCommand command = new EditAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                assignmentQuery, DIDDY.getStudentNumber());

        // Expect a CommandException due to no student found
        assertThrows(CommandException.class, () -> command.execute(model),
                EditAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);
    }
}
