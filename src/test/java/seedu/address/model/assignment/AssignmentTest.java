package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAssignments.TUTORIAL_HOMEWORK;

import org.junit.jupiter.api.Test;

public class AssignmentTest {
    @Test
    public void toStringMethod() {
        String expected = Assignment.class.getCanonicalName() + "{name=" + TUTORIAL_HOMEWORK.getAssignmentName()
                + ", deadline=" + TUTORIAL_HOMEWORK.getDeadline() + ", submission status="
                + TUTORIAL_HOMEWORK.getSubmissionStatus() + ", grading status=" + TUTORIAL_HOMEWORK.getGradingStatus()
                + ", grade=" + TUTORIAL_HOMEWORK.getGrade() + "}";
        assertEquals(expected, TUTORIAL_HOMEWORK.toString());
    }
}
