package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAssignments.MATH_HOMEWORK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.exceptions.ScoreExceedsMaxScoreException;

class AssignmentTest {

    private Assignment assignment;

    @BeforeEach
    void setUp() {
        assignment = new Assignment(MATH_HOMEWORK);
    }

    @Test
    void constructor_nullAssignmentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Assignment(null, 100));
    }

    @Test
    void constructor_invalidMaxScore_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Assignment(new AssignmentName("Math Homework"),
                -10));
    }

    @Test
    void isValidMaxScore() {
        assertTrue(Assignment.isValidMaxScore(0));
        assertTrue(Assignment.isValidMaxScore(100));
        assertFalse(Assignment.isValidMaxScore(-1));
    }

    @Test
    void getAssignmentName() {
        assertEquals("Math Homework", assignment.getAssignmentName().assignmentName);
    }

    @Test
    void getMaxScore() {
        assertEquals(100, assignment.getMaxScore());
    }

    @Test
    void setScore_withinMaxScore_updatesScore() {
        assignment.setScore(50);
        assertEquals(50, assignment.getScore());
    }

    @Test
    void setScore_exceedsMaxScore_throwsScoreExceedsMaxScoreException() {
        assertThrows(ScoreExceedsMaxScoreException.class, () -> assignment.setScore(101));
    }

    @Test
    void getHasSubmitted_defaultValue_false() {
        assertFalse(assignment.getHasSubmitted());
    }

    @Test
    void setHasSubmitted_updatesSubmissionStatus() {
        assignment.setHasSubmitted(true);
        assertTrue(assignment.getHasSubmitted());
    }

    @Test
    void getIsGraded_defaultValue_false() {
        assertFalse(assignment.getIsGraded());
    }

    @Test
    void setIsGraded_updatesGradedStatus() {
        assignment.setIsGraded(true);
        assertTrue(assignment.getIsGraded());
    }

    @Test
    void isSameAssignment_sameAssignment_returnsTrue() {
        Assignment anotherAssignment = new Assignment(new AssignmentName("Math Homework"), 100);
        assertTrue(assignment.isSameAssignment(anotherAssignment));
    }

    @Test
    void isSameAssignment_differentAssignment_returnsFalse() {
        Assignment differentAssignment = new Assignment(new AssignmentName("Science Homework"), 100);
        assertFalse(assignment.isSameAssignment(differentAssignment));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        assertEquals(assignment, assignment);
    }

    @Test
    void equals_differentObjectSameValues_returnsTrue() {
        Assignment sameAssignment = new Assignment(new AssignmentName("Math Homework"), 100);
        sameAssignment.setScore(assignment.getScore());
        sameAssignment.setHasSubmitted(assignment.getHasSubmitted());
        sameAssignment.setIsGraded(assignment.getIsGraded());
        assertEquals(assignment, sameAssignment);
    }

    @Test
    void equals_differentValues_returnsFalse() {
        Assignment differentAssignment = new Assignment(new AssignmentName("Math Homework"), 100);
        differentAssignment.setScore(10);
        assertNotEquals(assignment, differentAssignment);
    }

    @Test
    void hashCode_sameObject_returnsSameHashCode() {
        assertEquals(assignment.hashCode(), assignment.hashCode());
    }

    @Test
    void toString_containsAllFields() {
        String toString = assignment.toString();
        assertTrue(toString.contains("assignmentName=Math Homework"));
        assertTrue(toString.contains("MIN_SCORE=0"));
        assertTrue(toString.contains("maxScore=100"));
        assertTrue(toString.contains("score=0"));
        assertTrue(toString.contains("hasSubmitted=false"));
        assertTrue(toString.contains("isGraded=false"));
    }
}
