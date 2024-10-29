package tuteez.model.person.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.model.person.lesson.Lesson.isValidLesson;
import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LessonTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null));
    }
    @Test
    public void constructor_startTimeAfterEndTime_throwsIllegalArgumentException() {
        String invalidTimeStr = "friday 1800-1700";
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidTimeStr));
    }

    @Test
    public void isClashingWithOtherLessonTest_noClash() {
        Lesson l1 = new Lesson("Friday 1300-1400");
        Lesson l2 = new Lesson("Friday 1400-1500");
        Lesson l3 = new Lesson("Thursday 1300-1400");
        Lesson l4 = new Lesson("Thursday 1600-1700");

        assertFalse(Lesson.isClashingWithOtherLesson(l1, l2));
        assertFalse(Lesson.isClashingWithOtherLesson(l2, l1));
        assertFalse(Lesson.isClashingWithOtherLesson(l1, l3));
        assertFalse(Lesson.isClashingWithOtherLesson(l3, l4));
    }

    @Test
    public void isClashingWithOtherLessonTest_clashOccur() {
        Lesson l1 = new Lesson("Friday 1300-1400");
        Lesson l2 = new Lesson("Friday 1330-1430");

        Lesson l3 = new Lesson("Thursday 1200-1300");
        Lesson l4 = new Lesson("Thursday 1100-1400");


        Lesson l5 = new Lesson("Wednesday 1100-1200");
        Lesson l6 = new Lesson("Wednesday 1100-1200");

        assertTrue(Lesson.isClashingWithOtherLesson(l1, l2));
        assertTrue(Lesson.isClashingWithOtherLesson(l2, l1));
        assertTrue(Lesson.isClashingWithOtherLesson(l3, l4));
        assertTrue(Lesson.isClashingWithOtherLesson(l5, l6));
    }

    @Test
    public void constructor_acceptsShortcutDayNames() {
        String lessonWithShortCut = "mon 1200-1400";
        Lesson l1 = new Lesson("monday 1200-1400");
        Lesson l2 = new Lesson(lessonWithShortCut);

        assertTrue(isValidLesson(lessonWithShortCut));
        assertEquals(l1, l2);
    }

    @Test
    public void equals() {
        Lesson l1 = new Lesson("Friday 1300-1400");
        Lesson l2 = new Lesson("Friday 1300-1400");
        Lesson l3 = new Lesson("friday 1300-1400");

        Assertions.assertNotEquals(l1, new Object());
        assertEquals(l1, l1);
        assertEquals(l1, l2);
        assertEquals(l2, l3);
    }

    @Test
    public void lessonComparatorTest() {
        Lesson.LessonComparator lessonComparator = new Lesson.LessonComparator();
        Lesson l1 = new Lesson("Monday 1300-1400");
        Lesson l2 = new Lesson("Tuesday 1300-1400");
        Lesson l3 = new Lesson("Tuesday 1400-1500");
        assertEquals(-1, lessonComparator.compare(l1, l2));
        assertEquals(-1, lessonComparator.compare(l2, l3));
    }
}
