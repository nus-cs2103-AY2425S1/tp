package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

class JsonAdaptedAssignmentTest {

    private static final String VALID_ASSIGNMENT_NAME = "Homework";
    private static final int VALID_MAX_SCORE = 100;
    private static final int VALID_SCORE = 85;
    private static final boolean VALID_HAS_SUBMITTED = true;
    private static final boolean VALID_IS_GRADED = true;

    private static final String INVALID_ASSIGNMENT_NAME = "";
    private static final int INVALID_MAX_SCORE = -10;

    @Test
    void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        JsonAdaptedAssignment jsonAdaptedAssignment = new JsonAdaptedAssignment(
                VALID_ASSIGNMENT_NAME, VALID_MAX_SCORE, VALID_SCORE, VALID_HAS_SUBMITTED, VALID_IS_GRADED);

        Student dummyStudent = ALICE; // Replace with actual instantiation if Student has parameters
        Assignment assignment = jsonAdaptedAssignment.toModelType(dummyStudent);

        assertEquals(new Assignment(new AssignmentName(VALID_ASSIGNMENT_NAME), VALID_MAX_SCORE)
                .getName(), assignment.getName());
        assertEquals(VALID_SCORE, assignment.getScore());
        assertEquals(VALID_HAS_SUBMITTED, assignment.getHasSubmitted());
        assertEquals(VALID_IS_GRADED, assignment.getIsGraded());
    }

    @Test
    void toModelType_nullAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment jsonAdaptedAssignment = new JsonAdaptedAssignment(
                null, VALID_MAX_SCORE, VALID_SCORE, VALID_HAS_SUBMITTED, VALID_IS_GRADED);

        Student dummyStudent = ALICE; // Replace with actual instantiation if Student has parameters
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAssignment.toModelType(dummyStudent));
    }

    @Test
    void toModelType_invalidAssignmentName_throwsIllegalValueException() {
        JsonAdaptedAssignment jsonAdaptedAssignment = new JsonAdaptedAssignment(
                INVALID_ASSIGNMENT_NAME, VALID_MAX_SCORE, VALID_SCORE, VALID_HAS_SUBMITTED, VALID_IS_GRADED);

        Student dummyStudent = ALICE; // Replace with actual instantiation if Student has parameters
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAssignment.toModelType(dummyStudent));
    }

    @Test
    void toModelType_invalidMaxScore_throwsIllegalValueException() {
        JsonAdaptedAssignment jsonAdaptedAssignment = new JsonAdaptedAssignment(
                VALID_ASSIGNMENT_NAME, INVALID_MAX_SCORE, VALID_SCORE, VALID_HAS_SUBMITTED, VALID_IS_GRADED);

        Student dummyStudent = ALICE; // Replace with actual instantiation if Student has parameters
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAssignment.toModelType(dummyStudent));
    }
}
