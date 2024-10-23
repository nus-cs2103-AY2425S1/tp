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
        assertThrows(IllegalArgumentException.class, () -> new LessonTime(""));
    }

    @Test
    public void isValidLessonTime() {
        // null lesson time
        assertThrows(NullPointerException.class, () -> LessonTime.isValidLessonTime(null));

        // wrong number of segments
        assertFalse(LessonTime.isValidLessonTime(""));
        assertFalse(LessonTime.isValidLessonTime(" "));
        assertFalse(LessonTime.isValidLessonTime("11:00-13:30"));

        // wrong day
        assertFalse(LessonTime.isValidLessonTime("TMR-11:00-13:30"));

        // wrong time formats
        assertFalse(LessonTime.isValidLessonTime("SUN-11.00-1.30"));
        assertFalse(LessonTime.isValidLessonTime("SUN-11:00-1:30"));
        assertFalse(LessonTime.isValidLessonTime("SUN-11:00-13:70"));

        // end time before start time
        assertFalse(LessonTime.isValidLessonTime("SUN-11:00-11:00"));
        assertFalse(LessonTime.isValidLessonTime("SUN-13:30-11:00"));

        // valid lesson times
        assertTrue(LessonTime.isValidLessonTime("SUN-11:00-13:30"));
    }

    @Test
    public void equals() {
        LessonTime lt = new LessonTime("SUN-11:00-13:30");

        // same values -> returns true
        assertTrue(lt.equals(new LessonTime("SUN-11:00-13:30")));

        // same object -> returns true
        assertTrue(lt.equals(lt));

        // null -> returns false
        assertFalse(lt.equals(null));

        // different types -> returns false
        assertFalse(lt.equals(5.0f));

        // different values -> returns false
        assertFalse(lt.equals(new LessonTime("MON-11:00-13:30")));
        assertFalse(lt.equals(new LessonTime("SUN-12:00-13:30")));
        assertFalse(lt.equals(new LessonTime("SUN-11:00-14:30")));
        assertFalse(lt.equals(new LessonTime("MON-12:00-14:30")));
    }

    @Test
    public void canMerge() {
        LessonTime lt = new LessonTime("SUN-11:00-13:30");

        // same values -> returns true
        assertTrue(lt.canMerge(new LessonTime("SUN-11:00-13:30")));

        // overlap times -> returns true
        assertTrue(lt.canMerge(new LessonTime("SUN-13:30-14:30")));
        assertTrue(lt.canMerge(new LessonTime("SUN-13:00-14:30")));
        assertTrue(lt.canMerge(new LessonTime("SUN-12:30-13:30")));
        assertTrue(lt.canMerge(new LessonTime("SUN-11:30-12:30")));
        assertTrue(lt.canMerge(new LessonTime("SUN-11:00-12:00")));
        assertTrue(lt.canMerge(new LessonTime("SUN-10:30-11:30")));
        assertTrue(lt.canMerge(new LessonTime("SUN-10:00-11:00")));

        // different day -> returns false
        assertFalse(lt.canMerge(new LessonTime("SAT-11:00-13:30")));

        // separate timings -> returns false
        assertFalse(lt.canMerge(new LessonTime("SUN-09:00-10:30")));
        assertFalse(lt.canMerge(new LessonTime("SUN-14:00-15:30")));
    }

    @Test
    public void toStringTest() {
        LessonTime lt = new LessonTime("SUN-11:00-13:30");
        String expected = "SUN-11:00-13:30";
        assertEquals(expected, lt.toString());
    }
}
