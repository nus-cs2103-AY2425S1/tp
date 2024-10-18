package tahub.contacts.model.grade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

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
    void testGetLetterGrade() {
        gradingSystem.addGrade("Midterm", 85.0);
        assertEquals("B", gradingSystem.getLetterGrade("Student Name"));

        gradingSystem.addGrade("Final", 95.0);
        assertEquals("A", gradingSystem.getLetterGrade("Student Name"));
    }

    @Test
    void testGetOverallScore() {
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.addGrade("Final", 95.0);
        assertEquals(90.0, gradingSystem.getOverallScore(), 0.001);
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
        assertEquals("No grade recorded", gradingSystem.getLetterGrade("Student Name"));
    }

    @Test
    void testLetterGradeScale() {
        gradingSystem.addGrade("Test", 95.0);
        assertEquals("A", gradingSystem.getLetterGrade("Student Name"));

        gradingSystem = new GradingSystem(); // Reset
        gradingSystem.addGrade("Test", 85.0);
        assertEquals("B", gradingSystem.getLetterGrade("Student Name"));

        gradingSystem = new GradingSystem(); // Reset
        gradingSystem.addGrade("Test", 75.0);
        assertEquals("C", gradingSystem.getLetterGrade("Student Name"));

        gradingSystem = new GradingSystem(); // Reset
        gradingSystem.addGrade("Test", 65.0);
        assertEquals("D", gradingSystem.getLetterGrade("Student Name"));

        gradingSystem = new GradingSystem(); // Reset
        gradingSystem.addGrade("Test", 55.0);
        assertEquals("F", gradingSystem.getLetterGrade("Student Name"));
    }
}