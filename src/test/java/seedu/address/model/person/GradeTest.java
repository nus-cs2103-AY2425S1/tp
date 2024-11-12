package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null Grade
        assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid Grade
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("A++")); // contains invalid grade format
        assertFalse(Grade.isValidGrade("G")); // not a valid grade

        // valid Grade
        assertTrue(Grade.isValidGrade("A+")); // valid grade
        assertTrue(Grade.isValidGrade("a")); // case insensitive, valid grade
        assertTrue(Grade.isValidGrade("b-")); // case insensitive, valid grade
    }

    @Test
    public void equals() {
        Grade grade = new Grade("A+");

        // same values -> returns true
        assertTrue(grade.equals(new Grade("A+")));

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different types -> returns false
        assertFalse(grade.equals(5.0f));

        // different values -> returns false
        assertFalse(grade.equals(new Grade("B")));
    }
}
