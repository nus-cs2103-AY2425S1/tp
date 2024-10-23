package tahub.contacts.model.grade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for {@link Grade}.
 * These tests verify the correctness of the Grade class's constructor,
 * getter methods, equals method, and hashCode method.
 */
public class GradeTest {

    private static final String VALID_ASSESSMENT_NAME = "Midterm";
    private static final double VALID_SCORE_PERCENTAGE = 85.0;
    private static final double VALID_WEIGHT = 0.4;

    /**
     * Tests if the Grade constructor correctly initializes a Grade object with valid inputs.
     */
    @Test
    public void constructor_validInputs_success() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(VALID_ASSESSMENT_NAME, grade.getAssessmentName());
        assertEquals(VALID_SCORE_PERCENTAGE, grade.getScorePercentage());
        assertEquals(VALID_WEIGHT, grade.getWeight());
    }

    /**
     * Tests if the equals method correctly identifies two Grade objects with the same values as equal.
     */
    @Test
    public void equals_sameValues_returnsTrue() {
        Grade grade1 = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        Grade grade2 = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(grade1, grade2);
    }

    /**
     * Tests if the equals method correctly identifies two Grade objects with different values as not equal.
     */
    @Test
    public void equals_differentValues_returnsFalse() {
        Grade grade1 = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        Grade grade2 = new Grade("Final", 90.0, 0.6);
        assertNotEquals(grade1, grade2);
    }

    /**
     * Tests if the equals method correctly identifies a Grade object as equal to itself.
     */
    @Test
    public void equals_sameObject_returnsTrue() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(grade, grade);
    }

    /**
     * Tests if the equals method correctly handles comparison with null.
     */
    @Test
    public void equals_nullObject_returnsFalse() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertNotEquals(null, grade);
    }

    /**
     * Tests if the hashCode method returns the same value for two Grade objects with the same values.
     */
    @Test
    public void hashCode_sameValues_sameHashCode() {
        Grade grade1 = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        Grade grade2 = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(grade1.hashCode(), grade2.hashCode());
    }

    /**
     * Tests if the getAssessmentName method returns the correct assessment name.
     */
    @Test
    public void getAssessmentName_returnsCorrectName() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(VALID_ASSESSMENT_NAME, grade.getAssessmentName());
    }

    /**
     * Tests if the getScorePercentage method returns the correct score percentage.
     */
    @Test
    public void getScorePercentage_returnsCorrectScore() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(VALID_SCORE_PERCENTAGE, grade.getScorePercentage());
    }

    /**
     * Tests if the getWeight method returns the correct weight.
     */
    @Test
    public void getWeight_returnsCorrectWeight() {
        Grade grade = new Grade(VALID_ASSESSMENT_NAME, VALID_SCORE_PERCENTAGE, VALID_WEIGHT);
        assertEquals(VALID_WEIGHT, grade.getWeight());
    }
}
