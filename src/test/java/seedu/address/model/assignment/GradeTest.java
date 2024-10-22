package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class GradeTest {
    @Test
    public void constructor_validGrade_success() {
        // valid grades
        Grade grade1 = new Grade("75");
        Grade grade2 = new Grade("85.5");

        assertEquals(Optional.of(75.0), grade1.grade);
        assertEquals(Optional.of(85.5), grade2.grade);
    }

    @Test
    public void constructor_nullGrade_success() {
        // null grade
        Grade grade = new Grade("NULL");
        assertEquals(Optional.empty(), grade.grade);
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        // invalid grades
        assertThrows(IllegalArgumentException.class, () -> new Grade("110"));
        assertThrows(IllegalArgumentException.class, () -> new Grade("-5"));
        assertThrows(IllegalArgumentException.class, () -> new Grade("notANumber"));
    }

    @Test
    public void isValidGrade() {
        // valid grades
        assertTrue(Grade.isValidGrade("0")); // minimum boundary
        assertTrue(Grade.isValidGrade("100")); // maximum boundary
        assertTrue(Grade.isValidGrade("85.5")); // decimal
        assertTrue(Grade.isValidGrade("NULL")); // null value

        // invalid grades
        assertFalse(Grade.isValidGrade("105")); // out of upper bound
        assertFalse(Grade.isValidGrade("-5")); // negative number
        assertFalse(Grade.isValidGrade("abc")); // non-numeric string
        assertFalse(Grade.isValidGrade("")); // empty string
    }

    @Test
    public void getDefault_returnsNullGrade() {
        Grade defaultGrade = Grade.getDefault();
        assertEquals(Optional.empty(), defaultGrade.grade);
    }

    @Test
    public void testToString() {
        Grade grade1 = new Grade("85");
        Grade grade2 = new Grade("85.5678");
        Grade nullGrade = new Grade("NULL");

        assertEquals("85.00", grade1.toString());
        assertEquals("85.57", grade2.toString());
        assertEquals("NULL", nullGrade.toString());
    }

    @Test
    public void testEquals() {
        Grade grade1 = new Grade("85");
        Grade grade2 = new Grade("85");
        Grade grade3 = new Grade("85.0");
        Grade grade4 = new Grade("NULL");
        Grade grade5 = new Grade("90");

        assertTrue(grade1.equals(grade2)); // same value
        assertTrue(grade1.equals(grade3)); // equivalent value
        assertFalse(grade1.equals(grade4)); // different value
        assertFalse(grade1.equals(grade5)); // different value
        assertFalse(grade1.equals(null)); // null
        assertFalse(grade1.equals("85")); // different type
    }
}
