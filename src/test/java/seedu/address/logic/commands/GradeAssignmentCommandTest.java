package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GradeAssignmentCommand.MESSAGE_GRADE_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBookWithAssignments;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

public class GradeAssignmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithAssignments(), new UserPrefs());

    @Test
    public void execute_validIndexScore_success() {
        System.out.println(model.getFilteredStudentList());
        Student studentToGrade = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignmentToGrade = studentToGrade.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        int score = assignmentToGrade.getMaxScore();
        GradeAssignmentCommand gradeAssignmentCommand = new GradeAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, score);

        String expectedMessage = String.format(MESSAGE_GRADE_SUCCESS, assignmentToGrade.getName(),
                studentToGrade.getName().fullName, assignmentToGrade.getMaxScore());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);;

        assertCommandSuccess(gradeAssignmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        GradeAssignmentCommand gradeAssignmentCommand = new GradeAssignmentCommand(outOfBoundIndex,
                INDEX_FIRST_ASSIGNMENT, VALID_SCORE);

        assertCommandFailure(gradeAssignmentCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAssignmentIndex_throwsCommandException() {
        Student studentToGrade = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(studentToGrade.getAssignmentList().size() + 1);
        GradeAssignmentCommand gradeAssignmentCommand = new GradeAssignmentCommand(INDEX_FIRST_STUDENT,
                outOfBoundIndex, VALID_SCORE);

        assertCommandFailure(gradeAssignmentCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_INDEX);
    }

    @Test
    public void execute_invalidScore_throwsCommandException() {
        Student studentToGrade = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Assignment assignmentToGrade = studentToGrade.getAssignmentList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        int outOfBoundScore = assignmentToGrade.getMaxScore() + 1;
        GradeAssignmentCommand gradeAssignmentCommand = new GradeAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, outOfBoundScore);

        assertCommandFailure(gradeAssignmentCommand, model, Messages.MESSAGE_SCORE_EXCEEDS_MAX_SCORE);
    }

    @Test
    public void equals() {
        GradeAssignmentCommand gradeFirstCommand = new GradeAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, VALID_SCORE);
        GradeAssignmentCommand gradeSecondCommand = new GradeAssignmentCommand(INDEX_SECOND_STUDENT,
                INDEX_FIRST_ASSIGNMENT, VALID_SCORE);

        // same object -> returns true
        assertTrue(gradeFirstCommand.equals(gradeFirstCommand));

        // same values -> returns true
        GradeAssignmentCommand gradeFirstCommandCopy = new GradeAssignmentCommand(INDEX_FIRST_STUDENT,
                INDEX_FIRST_ASSIGNMENT, VALID_SCORE);
        assertTrue(gradeFirstCommand.equals(gradeFirstCommandCopy));

        // different types -> returns false
        assertFalse(gradeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gradeFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(gradeFirstCommand.equals(gradeSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index studentIndex = Index.fromOneBased(1);
        Index assignmentIndex = Index.fromOneBased(1);
        int score = VALID_SCORE;
        GradeAssignmentCommand gradeAssignmentCommand = new GradeAssignmentCommand(studentIndex,
                assignmentIndex,
                score);
        String expected = GradeAssignmentCommand.class.getCanonicalName() + "{studentIndex=" + studentIndex
                + ", assignmentIndex=" + assignmentIndex + ", score=" + score + "}";
        assertEquals(expected, gradeAssignmentCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
