package seedu.address.model.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_emptyCourseCode_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidTagName));
    }

    @Test
    public void constructor_invalidCourseCode_throwsIllegalArgumentException() {
        String invalidTagName = "CS1K";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidTagName));
    }

    @Test
    public void constructor_isValidCourseCode1_success() {
        assertDoesNotThrow(() -> new Course("MA1100"));
    }

    @Test
    public void constructor_isValidCourseCode2_success() {
        assertDoesNotThrow(() -> new Course("CS1231S"));
    }

    @Test
    public void constructor_isValidCourseCode3_success() {
        assertDoesNotThrow(() -> new Course("GEA1000N"));
    }

    @Test
    public void constructor_lowercaseCourseCode_success() {
        assertDoesNotThrow(() -> new Course("cs2109s"));
    }

    @Test
    public void equals_caseInsensitive() {
        Course c1 = new Course("CS1101S");
        Course c2 = new Course("cs1101s");
        assertTrue(() -> c1.equals(c2));
    }

}
