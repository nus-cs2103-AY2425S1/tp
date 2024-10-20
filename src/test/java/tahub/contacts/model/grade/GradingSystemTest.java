package tahub.contacts.model.grade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GradingSystemTest {

    private GradingSystem gradingSystem;

    @BeforeEach
    void setUp() {
        gradingSystem = new GradingSystem();
    }

    @Test
    void testAddAndGetGrade() {
        gradingSystem.addGrade("Midterm", 85.0);
        assertEquals(85.0, gradingSystem.getGrade("Midterm"), 0.001);
        assertEquals(-1.0, gradingSystem.getGrade("Final"), 0.001);
    }

    @Test
    void testGetOverallScore() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        gradingSystem.setAssessmentWeight("Final", 0.6);
        assertEquals(91.0, gradingSystem.getOverallScore(), 0.001);
    }

    @Test
    void testGetAllGrades() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        Map<String, Double> allGrades = gradingSystem.getAllGrades();
        assertEquals(2, allGrades.size());
        assertTrue(allGrades.containsKey("Midterm"));
        assertTrue(allGrades.containsKey("Final"));
        assertEquals(85.0, allGrades.get("Midterm"), 0.001);
        assertEquals(95.0, allGrades.get("Final"), 0.001);
    }

    @Test
    void testNoGradesRecorded() {
        assertEquals(-1.0, gradingSystem.getOverallScore(), 0.001);
    }

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

    @Test
    void testUnweightedGrades() {
        gradingSystem.addGrade("Quiz1", 80.0);
        gradingSystem.addGrade("Quiz2", 90.0);

        assertEquals(85.0, gradingSystem.getOverallScore(), 0.001);
    }

    @Test
    void testPartialWeights() {
        gradingSystem.addGrade("Midterm", 80.0);
        gradingSystem.addGrade("Final", 90.0);
        gradingSystem.addGrade("Project", 85.0);

        gradingSystem.setAssessmentWeight("Midterm", 0.3);
        gradingSystem.setAssessmentWeight("Final", 0.5);

        assertEquals(86.0, gradingSystem.getOverallScore(), 0.001);
    }
}
