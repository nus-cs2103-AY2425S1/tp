package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssignmentTest {
    private static final AssignmentName ORIGINAL_NAME = new AssignmentName("Homework 1");
    private static final Deadline ORIGINAL_DEADLINE = new Deadline("2023-12-01");
    private static final Status ORIGINAL_SUBMISSION_STATUS = new Status("N");
    private static final Status ORIGINAL_GRADING_STATUS = new Status("N");
    private static final Grade ORIGINAL_GRADE = new Grade("NULL");

    private static final Deadline NEW_DEADLINE = new Deadline("2023-12-15");
    private static final Status NEW_SUBMISSION_STATUS = new Status("Y");
    private static final Status NEW_GRADING_STATUS = new Status("Y");
    private static final Grade NEW_GRADE = new Grade("90");

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        assignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS,
                ORIGINAL_GRADING_STATUS, ORIGINAL_GRADE);
    }

    @Test
    public void toStringMethod() {
        String expected = Assignment.class.getCanonicalName() + "{name=" + MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()
                + ", deadline=" + MATH_ASSIGNMENT_SUBMITTED.getDeadline() + ", submission status="
                + MATH_ASSIGNMENT_SUBMITTED.getSubmissionStatus()
                + ", grading status=" + MATH_ASSIGNMENT_SUBMITTED.getGradingStatus()
                + ", grade=" + MATH_ASSIGNMENT_SUBMITTED.getGrade() + "}";
        assertEquals(expected, MATH_ASSIGNMENT_SUBMITTED.toString());
    }

    @Test
    void edit_editDeadline_updatesDeadline() {
        AssignmentQuery query = new AssignmentQuery(null, NEW_DEADLINE, null, null, null);
        assignment.edit(query);

        assertEquals(NEW_DEADLINE, assignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }

    @Test
    void edit_editSubmissionStatus_updatesSubmissionStatus() {
        AssignmentQuery query = new AssignmentQuery(null, null, NEW_SUBMISSION_STATUS, null, null);
        assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, assignment.getDeadline());
        assertEquals(NEW_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }

    @Test
    void edit_editGradingStatus_updatesGradingStatus() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, NEW_GRADING_STATUS, null);
        assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, assignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(NEW_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }

    @Test
    void edit_editGrade_updatesGrade() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, null, NEW_GRADE);
        assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, assignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(NEW_GRADE, assignment.getGrade());
    }

    @Test
    void edit_editMultipleFields_updatesFields() {
        AssignmentQuery query = new AssignmentQuery(null, NEW_DEADLINE, NEW_SUBMISSION_STATUS,
                NEW_GRADING_STATUS, NEW_GRADE);
        assignment.edit(query);

        assertEquals(NEW_DEADLINE, assignment.getDeadline());
        assertEquals(NEW_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(NEW_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(NEW_GRADE, assignment.getGrade());
    }

    @Test
    void edit_noChanges_originalFieldsRemainUnchanged() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, null, null);
        assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, assignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, assignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADING_STATUS, assignment.getGradingStatus());
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }

    @Test
    void edit_nullGrade_doesNotChangeGrade() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, null, null);
        assignment.edit(query);
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());

        // Test with an empty optional
        AssignmentQuery queryWithEmptyGrade = new AssignmentQuery(null, null, null, null, null);
        assignment.edit(queryWithEmptyGrade);
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }
}
