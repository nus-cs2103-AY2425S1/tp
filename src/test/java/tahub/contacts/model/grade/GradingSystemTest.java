package tahub.contacts.model.grade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GradingSystem}.
 * Tests the functionality of grade management, weight distribution, and score calculations.
 */
class GradingSystemTest {

    private GradingSystem gradingSystem;

    @BeforeEach
    void setUp() {
        gradingSystem = new GradingSystem();
    }

    /**
     * Tests adding and retrieving grades, including invalid inputs.
     */
    @Test
    void testAddAndGetGrade() {
        gradingSystem.addGrade("Midterm", 85.0);
        assertEquals(85.0, gradingSystem.getGrade("Midterm"), 0.001);
        assertEquals(-1.0, gradingSystem.getGrade("Final"), 0.001);

        assertThrows(IllegalArgumentException.class, () -> gradingSystem.addGrade(null, 85.0));
        assertThrows(IllegalArgumentException.class, () -> gradingSystem.addGrade("", 85.0));
        assertThrows(IllegalArgumentException.class, () -> gradingSystem.addGrade("Test", -1.0));
        assertThrows(IllegalArgumentException.class, () -> gradingSystem.addGrade("Test", 101.0));
    }

    /**
     * Tests setting assessment weights, including invalid inputs.
     */
    @Test
    void testSetAssessmentWeight() {
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        Map<String, Double> weights = gradingSystem.getAllWeights();
        assertEquals(0.4, weights.get("Midterm"), 0.001);

        assertThrows(IllegalArgumentException.class, () -> gradingSystem.setAssessmentWeight(null,
                                                                                             0.5));
        assertThrows(IllegalArgumentException.class, () -> gradingSystem.setAssessmentWeight("Test",
                                                                                             -0.1));
        assertThrows(IllegalArgumentException.class, () -> gradingSystem.setAssessmentWeight("Test",
                                                                                             1.1));
    }

    /**
     * Tests calculation of overall score with weighted grades.
     */
    @Test
    void testGetOverallScore() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        gradingSystem.setAssessmentWeight("Final", 0.6);
        assertEquals(91.0, gradingSystem.getOverallScore(), 0.001);
    }

    /**
     * Tests retrieving all grades.
     */
    @Test
    void testGetAllGrades() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        Map<String, Double> allGrades = gradingSystem.getAllGrades();
        assertEquals(2, allGrades.size());
        assertEquals(85.0, allGrades.get("Midterm"), 0.001);
        assertEquals(95.0, allGrades.get("Final"), 0.001);
    }

    /**
     * Tests behavior when no grades are recorded.
     */
    @Test
    void testNoGradesRecorded() {
        assertEquals(-1.0, gradingSystem.getOverallScore(), 0.001);
    }

    /**
     * Tests calculations with fully weighted grades.
     */
    @Test
    void testWeightedGrades() {
        gradingSystem.addGrade("Assignment1", 90.0);
        gradingSystem.addGrade("Assignment2", 80.0);
        gradingSystem.addGrade("FinalExam", 85.0);

        gradingSystem.setAssessmentWeight("Assignment1", 0.2);
        gradingSystem.setAssessmentWeight("Assignment2", 0.2);
        gradingSystem.setAssessmentWeight("FinalExam", 0.6);

        assertEquals(85.0, gradingSystem.getOverallScore(), 0.001);
    }

    /**
     * Tests that an exception is thrown when weights exceed 1.0.
     */
    @Test
    void testExcessiveWeights() {
        gradingSystem.addGrade("Assignment1", 90.0);
        gradingSystem.setAssessmentWeight("Assignment1", 0.6);
        gradingSystem.addGrade("Assignment2", 80.0);
        gradingSystem.setAssessmentWeight("Assignment2", 0.6);

        assertThrows(IllegalStateException.class, () -> gradingSystem.getOverallScore());
    }

    /**
     * Tests updating existing grades.
     */
    @Test
    void testUpdateExistingGrade() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.setAssessmentWeight("Midterm", 0.4);

        gradingSystem.addGrade("Midterm", 90.0); // Update score
        assertEquals(90.0, gradingSystem.getGrade("Midterm"), 0.001);
        assertEquals(0.4, gradingSystem.getAllWeights().get("Midterm"), 0.001);
    }
}
