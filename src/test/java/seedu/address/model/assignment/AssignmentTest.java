package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;

import org.junit.jupiter.api.Test;

public class AssignmentTest {
    @Test
    public void toStringMethod() {
        String expected = Assignment.class.getCanonicalName() + "{name=" + MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()
                + ", deadline=" + MATH_ASSIGNMENT_SUBMITTED.getDeadline() + ", submission status="
                + MATH_ASSIGNMENT_SUBMITTED.getSubmissionStatus()
                + ", grading status=" + MATH_ASSIGNMENT_SUBMITTED.getGradingStatus()
                + ", grade=" + MATH_ASSIGNMENT_SUBMITTED.getGrade() + "}";
        assertEquals(expected, MATH_ASSIGNMENT_SUBMITTED.toString());
    }
}
