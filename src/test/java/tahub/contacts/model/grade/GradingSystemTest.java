package tahub.contacts.model.grade;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(85.0, gradingSystem.getGrade("Midterm"), 0.001,
                                 "Grade should be exactly 85.0");
        Assertions.assertTrue (
                gradingSystem.getGrade("Midterm") > 80.0 ,
                "Grade should be above 80");
        Assertions.assertNotEquals(-1.0, gradingSystem.getGrade("Midterm"),
                                    "Grade should not be -1.0");
        Assertions.assertEquals(-1.0, gradingSystem.getGrade("Final"), 0.001,
                                 "Non-existent grade should return -1.0");

        // Test invalid inputs
        Assertions.assertAll(
                "Invalid grade inputs",
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.addGrade(
                                null ,
                                85.0) ,
                        "Should reject null assessment name"),
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.addGrade(
                                "",
                                85.0),
                        "Should reject empty assessment name") ,
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.addGrade(
                                "Test",
                                -1.0),
                        "Should reject negative grade"),
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.addGrade(
                                "Test",
                                101.0),
                        "Should reject grade above 100")
                             );
    }

    /**
     * Tests setting assessment weights, including invalid inputs.
     */
    @Test
    void testSetAssessmentWeight() {
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        Map<String, Double> weights = gradingSystem.getAllWeights();

        Assertions.assertAll(
                "Weight validations" ,
                () -> Assertions.assertEquals(0.4, weights.get("Midterm"), 0.001,
                                               "Weight should be exactly 0.4") ,
                () -> Assertions.assertTrue(
                        weights.containsKey("Midterm"),
                        "Weights map should contain Midterm"),
                () -> Assertions.assertFalse(
                        weights.isEmpty(),
                        "Weights map should not be empty")
                             );

        // Test invalid weights
        Assertions.assertAll(
                "Invalid weight inputs",
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.setAssessmentWeight(
                                null, 0.5),
                        "Should reject null assessment") ,
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.setAssessmentWeight(
                                "Test", -0.1),
                        "Should reject negative weight") ,
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> gradingSystem.setAssessmentWeight(
                                "Test", 1.1),
                        "Should reject weight above 1.0")
                             );
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

        double expectedScore = 91.0;
        Assertions.assertAll(
                "Overall score calculations" ,
                () -> Assertions.assertEquals(expectedScore, gradingSystem.getOverallScore(),
                                               0.001,
                                               "Overall score should be exactly 91.0"),
                () -> Assertions.assertTrue(
                        gradingSystem.getOverallScore() > 90.0,
                        "Overall score should be above 90") ,
                () -> Assertions.assertTrue(
                        gradingSystem.getOverallScore() < 95.0,
                        "Overall score should be below 95")
                             );
    }

    /**
     * Tests retrieving all grades.
     */
    @Test
    void testGetAllGrades() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        Map<String, Double> allGrades = gradingSystem.getAllGrades();
        Assertions.assertEquals(2 , allGrades.size());
        Assertions.assertEquals(85.0, allGrades.get("Midterm"), 0.001);
        Assertions.assertEquals(95.0, allGrades.get("Final"), 0.001);
    }

    /**
     * Tests behavior when no grades are recorded.
     */
    @Test
    void testNoGradesRecorded() {
        Assertions.assertEquals(-1.0, gradingSystem.getOverallScore(), 0.001);
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

        Assertions.assertEquals(85.0, gradingSystem.getOverallScore(), 0.001);
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

        Assertions.assertThrows(IllegalStateException.class, () -> gradingSystem.getOverallScore());
    }

    /**
     * Tests updating existing grades.
     */
    @Test
    void testUpdateExistingGrade() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        gradingSystem.addGrade("Midterm", 90.0); // Update score
        Assertions.assertEquals(90.0, gradingSystem.getGrade("Midterm"), 0.001);
        Assertions.assertEquals(0.4, gradingSystem.getAllWeights().get("Midterm"), 0.001);
    }
}
