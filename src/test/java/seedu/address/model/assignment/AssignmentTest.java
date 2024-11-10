package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
    }

    @Test
    public void toStringMethod() {
        String expected = Assignment.class.getCanonicalName() + "{name=" + MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()
                + ", deadline=" + MATH_ASSIGNMENT_SUBMITTED.getDeadline() + ", submission status="
                + MATH_ASSIGNMENT_SUBMITTED.getSubmissionStatus()
                + ", grade=" + MATH_ASSIGNMENT_SUBMITTED.getGrade() + "}";
        assertEquals(expected, MATH_ASSIGNMENT_SUBMITTED.toString());
    }

    @Test
    void edit_editDeadline_updatesDeadline() {
        AssignmentQuery query = new AssignmentQuery(null, NEW_DEADLINE, null, null);
        Assignment newAssignment = assignment.edit(query);

        assertEquals(NEW_DEADLINE, newAssignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, newAssignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADE, newAssignment.getGrade());
    }

    @Test
    void edit_editSubmissionStatus_updatesSubmissionStatus() {
        AssignmentQuery query = new AssignmentQuery(null, null, NEW_SUBMISSION_STATUS, null);
        Assignment newAssignment = assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, newAssignment.getDeadline());
        assertEquals(NEW_SUBMISSION_STATUS, newAssignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADE, newAssignment.getGrade());
    }

    @Test
    void edit_editGrade_updatesGrade() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, NEW_GRADE);
        Assignment newAssignment = assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, newAssignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, newAssignment.getSubmissionStatus());
        assertEquals(NEW_GRADE, newAssignment.getGrade());
    }

    @Test
    void edit_editMultipleFields_updatesFields() {
        AssignmentQuery query = new AssignmentQuery(null, NEW_DEADLINE, NEW_SUBMISSION_STATUS, NEW_GRADE);
        Assignment newAssignment = assignment.edit(query);

        assertEquals(NEW_DEADLINE, newAssignment.getDeadline());
        assertEquals(NEW_SUBMISSION_STATUS, newAssignment.getSubmissionStatus());
        assertEquals(NEW_GRADE, newAssignment.getGrade());
    }

    @Test
    void edit_noChanges_originalFieldsRemainUnchanged() {
        AssignmentQuery query = new AssignmentQuery(null, null, null, null);
        Assignment newAssignment = assignment.edit(query);

        assertEquals(ORIGINAL_DEADLINE, newAssignment.getDeadline());
        assertEquals(ORIGINAL_SUBMISSION_STATUS, newAssignment.getSubmissionStatus());
        assertEquals(ORIGINAL_GRADE, newAssignment.getGrade());
    }

    @Test
    void edit_changes_doesNotChangeOriginalFields() {
        AssignmentQuery query = new AssignmentQuery(null, NEW_DEADLINE, NEW_SUBMISSION_STATUS, NEW_GRADE);
        assignment.edit(query);
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());

        // Test with an empty optional
        AssignmentQuery queryWithEmptyGrade = new AssignmentQuery(null, null, null, null);
        assignment.edit(queryWithEmptyGrade);
        assertEquals(ORIGINAL_GRADE, assignment.getGrade());
    }


    @Test
    void getState_returnsCorrectState() {
        Deadline validDateline = new Deadline("2025-12-01");
        // Test GRADED state
        Assignment gradedAssignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, NEW_SUBMISSION_STATUS, NEW_GRADE);
        assertEquals(Assignment.State.GRADED, gradedAssignment.getState());

        // Test SUBMITTED state
        Assignment submittedAssignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, NEW_SUBMISSION_STATUS, ORIGINAL_GRADE);
        assertEquals(Assignment.State.SUBMITTED, submittedAssignment.getState());

        // Test PENDING state
        Assignment pendingAssignment = new Assignment(ORIGINAL_NAME, validDateline, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        assertEquals(Assignment.State.PENDING, pendingAssignment.getState());

        // Test LATE state
        Assignment lateAssignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        assertEquals(Assignment.State.LATE, lateAssignment.getState());
    }

    @Test
    void getLabelName_returnsCorrectLabelName() {
        // Test label name with grade
        Assignment gradedAssignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, NEW_SUBMISSION_STATUS, NEW_GRADE);
        assertEquals("Homework 1: 90.00", gradedAssignment.getLabelName());

        // Test label name without grade
        Assignment ungradedAssignment = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, NEW_SUBMISSION_STATUS, ORIGINAL_GRADE);
        assertEquals("Homework 1", ungradedAssignment.getLabelName());
    }

    @Test
    public void equalsMethod() {
        AssignmentName newName = new AssignmentName("Homework 2");
        Assignment assignment1 = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        Assignment assignment2 = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        Assignment assignment3 = new Assignment(newName, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        Assignment assignment4 = new Assignment(ORIGINAL_NAME, NEW_DEADLINE, ORIGINAL_SUBMISSION_STATUS, ORIGINAL_GRADE);
        Assignment assignment5 = new Assignment(ORIGINAL_NAME, NEW_DEADLINE, NEW_SUBMISSION_STATUS, ORIGINAL_GRADE);
        Assignment assignment6 = new Assignment(ORIGINAL_NAME, ORIGINAL_DEADLINE, ORIGINAL_SUBMISSION_STATUS, NEW_GRADE);

        // Same object
        assertEquals(assignment1, assignment1);

        // Different objects, same values
        assertEquals(assignment1, assignment2);

        // Different names
        assertNotEquals(assignment1, assignment3);

        // Different deadlines
        assertNotEquals(assignment1, assignment4);

        // Different statuses
        assertNotEquals(assignment1, assignment5);

        // Different grades
        assertNotEquals(assignment1, assignment6);

        // Null
        assertNotEquals(assignment1, null);

        // Different types
        assertNotEquals(assignment1, new Object());
    }
}
