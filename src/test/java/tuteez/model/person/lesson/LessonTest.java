package tuteez.model.person.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.model.person.lesson.Lesson.isValidLesson;
import static tuteez.testutil.Assert.assertThrows;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    public void isValidLesson_acceptsMultipleSpacesBetweenDayAndTime() {
        String lessonWithOneSpace = "monday 1200-1400";
        String lessonWithTwoSpaces = "monday  1200-1400";

        assertTrue(isValidLesson(lessonWithOneSpace));
        assertTrue(isValidLesson(lessonWithTwoSpaces));
    }

    @Test
    public void constructor_lessonStringWithMultipleSpaces_parsesSuccessfully() {
        String lessonWithTwoSpaces = "monday  1200-1400";
        String lessonWithManySpaces = "monday    1200-1400";
        String lessonWithTabAndSpaces = "monday \t  1200-1400";

        Lesson lessonTwo = new Lesson(lessonWithTwoSpaces);
        Lesson lessonMany = new Lesson(lessonWithManySpaces);
        Lesson lessonTab = new Lesson(lessonWithTabAndSpaces);

        assertEquals(Day.MONDAY, lessonTwo.getLessonDay());
        assertEquals("MONDAY 1200-1400", lessonTwo.getDayAndTime());
        assertTrue(isValidLesson(lessonWithTwoSpaces));

        assertEquals(Day.MONDAY, lessonMany.getLessonDay());
        assertEquals("MONDAY 1200-1400", lessonMany.getDayAndTime());
        assertTrue(isValidLesson(lessonWithManySpaces));

        assertEquals(Day.MONDAY, lessonTab.getLessonDay());
        assertEquals("MONDAY 1200-1400", lessonTab.getDayAndTime());
        assertTrue(isValidLesson(lessonWithTabAndSpaces));
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

    @Test
    public void durationTillLessonTest() {
        // 30th October 2024 is a Wednesday
        LocalDateTime currentTimePlaceholder = LocalDateTime.of(2024, 10, 30, 11, 0);

        LessonStub lesson = new LessonStub("wednesday 1000-1030");
        Duration correctDuration = Duration.ofDays(6)
                .plusHours(23)
                .plusMinutes(0);
        Duration durationTillLesson = lesson.durationTillLesson(currentTimePlaceholder);
        assertEquals(correctDuration, durationTillLesson);
    }

    @Test
    public void durationTillLessonTest_currentlyOngoing() {
        LocalDateTime currentTimePlaceholder = LocalDateTime.of(2024, 10, 30, 11, 0);

        LessonStub lesson1 = new LessonStub("wednesday 1100-1130");
        LessonStub lesson2 = new LessonStub("wednesday 1030-1100");
        Duration correctDuration = Duration.ofDays(0).plusMinutes(0).plusSeconds(0);
        Duration durationTillFirstLesson = lesson1.durationTillLesson(currentTimePlaceholder);
        assertEquals(durationTillFirstLesson, correctDuration);

        Duration durationTillSecondLesson = lesson2.durationTillLesson(currentTimePlaceholder);
        assertEquals(durationTillSecondLesson, correctDuration);
    }

    public static class LessonStub extends Lesson {

        /**
         * Constructs a {@code Lesson}.
         *
         * @param lesson A valid lesson string containing day and time range.
         */
        public LessonStub(String lesson) {
            super(lesson);
        }

        public Duration durationTillLesson(LocalDateTime currentTimePlaceholder) {
            if (this.isCurrentlyOngoing(currentTimePlaceholder)) {
                return Duration.ofDays(0).plusHours(0).plusMinutes(0);
            }

            Day currentDay = Day.convertDayToEnum(currentTimePlaceholder.getDayOfWeek().toString().toLowerCase());
            LocalTime currentTime = currentTimePlaceholder.toLocalTime();

            int daysUntilLesson = (this.getLessonDay().getValue() - currentDay.getValue() + 7) % 7;
            if (currentDay == this.getLessonDay() && currentTime.isAfter(this.getEndTime())) {
                daysUntilLesson = 7;
            }

            LocalDateTime nextLessonDateTime = currentTimePlaceholder
                    .plusDays(daysUntilLesson)
                    .withHour(this.getStartTime().getHour())
                    .withMinute(this.getStartTime().getMinute());
            return Duration.between(currentTimePlaceholder, nextLessonDateTime);
        }

        public boolean isCurrentlyOngoing(LocalDateTime currentTimePlaceholder) {
            Day currentDay = Day.convertDayToEnum(currentTimePlaceholder.getDayOfWeek().toString().toLowerCase());
            LocalTime currentTime = currentTimePlaceholder.toLocalTime();
            return currentDay.equals(this.getLessonDay())
                    && currentTime.isAfter(this.getStartTime())
                    && currentTime.isBefore(this.getEndTime().plusMinutes(1));
        }
    }
}
