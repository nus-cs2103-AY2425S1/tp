package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GradeTest {
    @Test
    public void constructor_validGrade_success() {
        // Valid grade
        Grade grade1 = new Grade("85.5");
        assertTrue(grade1.grade.isPresent());
        assertEquals(85.5, grade1.grade.get());

        // Valid grade as an integer
        Grade grade2 = new Grade("90");
        assertTrue(grade2.grade.isPresent());
        assertEquals(90.0, grade2.grade.get());

        // NULL grade
        Grade grade3 = new Grade("NULL");
        assertFalse(grade3.grade.isPresent());
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        // Invalid grades
        String[] invalidGrades = {"101", "-1", "100.1", "null", "abc", "90,5"};
        for (String invalidGrade : invalidGrades) {
            try {
                new Grade(invalidGrade);
                assertFalse(true, "Expected IllegalArgumentException for: " + invalidGrade);
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }
    }
}
