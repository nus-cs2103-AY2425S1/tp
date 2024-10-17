package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;

public class JsonAdaptedAssignmentTest {
    private static final String INVALID_ASSIGNMENT_NAME = "Math Homework@123";
    private static final String INVALID_DEADLINE = "invalid-date";
    private static final String INVALID_STATUS = "InvalidStatus";
    private static final String INVALID_GRADE = "120"; // Outside typical grading range

    private static final String VALID_ASSIGNMENT_NAME = MATH_ASSIGNMENT_SUBMITTED.getAssignmentName().fullName;
    private static final String VALID_DEADLINE = MATH_ASSIGNMENT_SUBMITTED.getDeadline().deadline.toString();
    private static final String VALID_SUBMISSION_STATUS = MATH_ASSIGNMENT_SUBMITTED
            .getSubmissionStatus().status.toString();
    private static final String VALID_GRADING_STATUS = MATH_ASSIGNMENT_SUBMITTED.getGradingStatus().status.toString();
    private static final String VALID_GRADE = MATH_ASSIGNMENT_SUBMITTED.getGrade().grade
            .map(x -> x.toString()).orElse("NULL");

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(MATH_ASSIGNMENT_SUBMITTED);
        assertEquals(MATH_ASSIGNMENT_SUBMITTED, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(INVALID_ASSIGNMENT_NAME, VALID_DEADLINE, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = AssignmentName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(null, VALID_DEADLINE, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, INVALID_DEADLINE, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, null, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidSubmissionStatus_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_DEADLINE, INVALID_STATUS,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullSubmissionStatus_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_DEADLINE, null,
                        VALID_GRADING_STATUS, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_DEADLINE, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, INVALID_GRADE);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment =
                new JsonAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_DEADLINE, VALID_SUBMISSION_STATUS,
                        VALID_GRADING_STATUS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }
}
