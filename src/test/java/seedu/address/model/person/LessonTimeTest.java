package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonTime(null));
    }

    @Test
    public void constructor_invalidLessonTime_throwsIllegalArgumentException() {
        String invalidLessonTime = "";
        assertThrows(IllegalArgumentException.class, () -> new LessonTime(invalidLessonTime));
    }

    @Test
    public void constructor_validLessonTime() {
        String validLessonTime = "mon:12:00";
        assertEquals(LessonTime.class, new LessonTime(validLessonTime).getClass());
    }

    @Test
    public void isValidLessonTime() {
        // null education level
        assertThrows(NullPointerException.class, () -> LessonTime.isValidLessonTime(null));
    }

    @Test
    public void equals() {
        LessonTime lessonTime = new LessonTime("tue:23:59");

        // same lessonTime -> returns true
        assertTrue(lessonTime.equals(new LessonTime("tue:23:59")));

        // same object -> returns true
        assertTrue(lessonTime.equals(lessonTime));

        // null -> returns false
        assertFalse(lessonTime.equals(null));

        // different types -> returns false
        assertFalse(lessonTime.equals(5.0f));

        // different lessonTime -> returns false
        assertFalse(lessonTime.equals(new LessonTime("tue:13:59")));
    }

    @Test
    public void testToString() {
        assertEquals("TUESDAY 13:59", new LessonTime("tue:13:59").toString());
    }
}
