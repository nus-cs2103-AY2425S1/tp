package keycontacts.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CancelledLessonTest {
    private Date date = new Date("06-07-2002");
    private CancelledLesson cancelledLesson = new CancelledLesson(date);

    @Test
    public void getLessonDateTest() {
        assertEquals(date, cancelledLesson.getLessonDate());
    }

    @Test
    public void equals() {
        // duplicate cancelled lesson
        CancelledLesson cancelledLessonDuplicate = new CancelledLesson(new Date("06-07-2002"));
        // cancelled lesson with different date
        CancelledLesson differentCancelledLesson = new CancelledLesson(new Date("15-12-2002"));

        assertTrue(cancelledLesson.equals(cancelledLesson)); // same object
        assertTrue(cancelledLesson.equals(cancelledLessonDuplicate)); // same values
        assertFalse(cancelledLesson.equals(null)); // null
        assertFalse(cancelledLesson.equals(differentCancelledLesson)); // different object
    }

    @Test
    public void getCancelledLessonSetTest() {
        String string1 = "14-02-2002";
        String string2 = "06-07-2002";
        CancelledLesson cancelledLesson1 = new CancelledLesson(new Date(string1));
        CancelledLesson cancelledLesson2 = new CancelledLesson(new Date(string2));
        Set<CancelledLesson> cancelledLessons = new HashSet<>();
        cancelledLessons.add(cancelledLesson1);
        cancelledLessons.add(cancelledLesson2);

        assertTrue(cancelledLessons.equals(CancelledLesson.getCancelledLessonSet(string1, string2)));
    }
}
