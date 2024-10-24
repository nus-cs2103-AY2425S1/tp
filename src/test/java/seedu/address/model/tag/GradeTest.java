package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void constructor_invalidGradeIndex_throwsIllegalArgumentException() {
        String invalidGradeIndex = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGradeIndex));
    }

    @Test
    public void constructor_validGradeIndex() {
        String validGradeIndex = "1";
        assertEquals(Grade.class, new Grade(validGradeIndex).getClass());
    }

    @Test
    public void isValidGradeName() {
        // null grade index
        assertThrows(NullPointerException.class, () -> Grade.isValidGradeIndex(null));

        // grade index too small
        assertFalse(Grade.isValidGradeIndex("-1"));

        // grade index too big
        assertFalse(Grade.isValidGradeIndex("5"));
    }

    @Test
    public void gradeIndexToName() {
        Grade unknownGrade = new Grade("0");
        Grade failingGrade = new Grade("1");
        Grade satisfactoryGrade = new Grade("2");
        Grade goodGrade = new Grade("3");
        Grade excellentGrade = new Grade("4");

        assertEquals("Unknown (0)", unknownGrade.gradeIndexToName());
        assertEquals("Failing (1)", failingGrade.gradeIndexToName());
        assertEquals("Satisfactory (2)", satisfactoryGrade.gradeIndexToName());
        assertEquals("Good (3)", goodGrade.gradeIndexToName());
        assertEquals("Excellent (4)", excellentGrade.gradeIndexToName());
    }

    @Test
    public void equals() {
        Grade grade = new Grade("2");

        // same grade -> returns true
        assertTrue(grade.equals(new Grade("2")));

        // same object -> returns true
        assertTrue(grade.equals(grade));

        // null -> returns false
        assertFalse(grade.equals(null));

        // different types -> returns false
        assertFalse(grade.equals(5.0f));

        // different grade -> returns false
        assertFalse(grade.equals(new Grade("3")));
    }

    @Test
    public void testToString() {
        assertEquals("4", new Grade("4").toString());
    }
}
