package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalAssignments.SCIENCE_ASSIGNMENT_GRADED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.StudentBuilder;

public class DeleteAssignmentCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_deleteAssignment_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        expectedModel.addStudent(hughCopy);
        HUGH.addAssignment(MATH_ASSIGNMENT_SUBMITTED);

        model.addStudent(HUGH);
        assertCommandSuccess(new DeleteAssignmentCommand(HUGH.getName(), MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()),
                model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }

    @Test
    public void undo_after_execute() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(HUGH);

        model.addStudent(hughCopy);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(HUGH.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName());
        assertCommandSuccess(deleteAssignmentCommand, model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);

        assertTrue(deleteAssignmentCommand.undo(model));
        assertTrue(hughCopy.getAssignments().size() == 1);
        assertTrue(hughCopy.getAssignments().get(0).equals(MATH_ASSIGNMENT_SUBMITTED));
    }

    @Test
    public void undo_before_execute() {
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        hughCopy.addAssignment(SCIENCE_ASSIGNMENT_GRADED);

        model.addStudent(hughCopy);
        DeleteAssignmentCommand deleteAssignmentCommand = new DeleteAssignmentCommand(hughCopy.getName(),
                MATH_ASSIGNMENT_SUBMITTED.getAssignmentName());
        assertFalse(deleteAssignmentCommand.undo(model));
        assertTrue(hughCopy.getAssignments().size() == 2); // No change to the assignments list
    }

    @Test
    public void execute_deleteAssignment_success2() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        HUGH.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        Student hughCopy = new StudentBuilder(HUGH).build();
        expectedModel.addStudent(hughCopy);

        model.addStudent(HUGH);
        assertCommandSuccess(new DeleteAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                        new StudentNumber(VALID_STUDENT_NUMBER_HUGH)),
                model,
                String.format(DeleteAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }

    @Test
    public void execute_deleteAssignmentWithDuplicateStudents_failure() {
        Student hughCopy = new StudentBuilder(HUGH)
                .withStudentNumber("A1234567J")
                .build();
        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy);
        model.addStudent(hughCopy2);

        assertCommandFailure(new DeleteAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()),
                model,
                String.format(DeleteAssignmentCommand.MESSAGE_DUPLICATE_STUDENT,
                        "A1234567J, " + HUGH.getStudentNumber()));

    }

    @Test
    public void execute_deleteAssignmentWithNoStudents_failure() {
        assertCommandFailure(new DeleteAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()),
                model,
                DeleteAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);

    }

    @Test
    public void execute_deleteAssignmentWithNoStudentNumber_failure() {
        Student hughCopy = new StudentBuilder(HUGH)
                .withStudentNumber("A1234567J")
                .build();
        model.addStudent(hughCopy);

        assertCommandFailure(new DeleteAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                        new StudentNumber(VALID_STUDENT_NUMBER_DIDDY)),
                model,
                DeleteAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);

    }

    @Test
    public void execute_deleteAssignmentWithNoAssignment_failure() {
        Student hughCopy = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy);

        assertCommandFailure(new DeleteAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                        new StudentNumber(VALID_STUDENT_NUMBER_HUGH)),
                model,
                DeleteAssignmentCommand.MESSAGE_NO_ASSIGNMENT_FOUND);

    }
}
