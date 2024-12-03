package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_NAME_A;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_NAME_B;
import static seedu.address.testutil.TypicalAssignments.DEADLINE_A;
import static seedu.address.testutil.TypicalAssignments.DEADLINE_B;
import static seedu.address.testutil.TypicalAssignments.GRADE_80;
import static seedu.address.testutil.TypicalAssignments.GRADE_90;
import static seedu.address.testutil.TypicalAssignments.STATUS_N;
import static seedu.address.testutil.TypicalAssignments.STATUS_Y;

import org.junit.jupiter.api.Test;

public class AssignmentQueryTest {

    @Test
    public void equals_sameAttributes_returnsTrue() {
        AssignmentQuery queryA = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        AssignmentQuery queryB = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        assertTrue(queryA.equals(queryB));
        assertTrue(queryA.equals(queryA));
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        AssignmentQuery queryA = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        AssignmentQuery queryB = new AssignmentQuery(ASSIGNMENT_NAME_B, DEADLINE_B, STATUS_N, GRADE_90);
        assertFalse(queryA.equals(queryB));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        AssignmentQuery queryA = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        assertFalse(queryA.equals(1));
        assertFalse(queryA.equals("OMEGA"));
    }

    @Test
    public void matchQuery_queryWithMatchingAssignment_returnsTrue() {
        AssignmentQuery query = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        Assignment matchingAssignment = new Assignment(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        assertTrue(query.match(matchingAssignment));
    }

    @Test
    public void matchQuery_queryWithNonMatchingAssignment_returnsFalse() {
        AssignmentQuery query = new AssignmentQuery(ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_80);
        Assignment nonMatchingAssignment = new Assignment(ASSIGNMENT_NAME_B, DEADLINE_B, STATUS_N, GRADE_90);
        assertFalse(query.match(nonMatchingAssignment));
    }

    @Test
    public void matchQuery_queryWithEmptyFields_returnsTrue() {
        // Empty fields should match any corresponding field in assignment
        AssignmentQuery query = new AssignmentQuery(null, null, null, null);
        Assignment assignment = new Assignment(ASSIGNMENT_NAME_B, DEADLINE_B, STATUS_N, GRADE_90);
        assertTrue(query.match(assignment));
    }
}
