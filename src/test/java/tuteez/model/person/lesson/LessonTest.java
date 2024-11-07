package tuteez.model.person.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.model.person.lesson.Lesson.isLessonEndAtValidTime;
import static tuteez.model.person.lesson.Lesson.isLessonStartAtValidTime;
import static tuteez.model.person.lesson.Lesson.isValidLesson;
import static tuteez.model.person.lesson.Lesson.isValidTimeOrder;
import static tuteez.model.person.lesson.Lesson.isValidTimeRange;
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

        String endTimeMidnight = "friday 2300-0000";
        assertThrows(IllegalArgumentException.class, () -> new Lesson(endTimeMidnight)); // boundary value
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        String empty = "";
        assertThrows(IllegalArgumentException.class, () -> new Lesson(empty));
    }

    @Test
    public void isLessonStartAtValidTime_invalidStart() {
        LocalTime inValidStartTime = LocalTime.of(23, 59);
        assertFalse(isLessonStartAtValidTime(inValidStartTime));
    }

    @Test
    public void isLessonEndAtValidTime_invalidEnd() {
        LocalTime inValidEndTime = LocalTime.of(0, 0);
        assertFalse(isLessonEndAtValidTime(inValidEndTime));
    }

    @Test
    public void isValidTimeOrder_validTimeOrder() {
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(19, 0);

        // EP: Start time before end time
        assertTrue(isValidTimeOrder(startTime, endTime));
    }

    @Test
    public void isValidTimeOrder_invalidTimeOrder() {
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(19, 0);

        // EP : Start time after end time
        assertFalse(isValidTimeOrder(endTime, startTime));

        // EP: Start time same as end time
        assertFalse(isValidTimeOrder(startTime, startTime));
    }

    @Test
    public void isValidTimeRange_validTimeRange() {
        String validTimeRange = "1800-1900";

        // EP: Valid time
        assertTrue(isValidTimeRange(validTimeRange));
    }

    @Test
    public void isValidTimeRange_invalidTimeRange() {
        String validTimeRange = "1800-1900";
        String invalidTimeRange = "2525-2780";
        String startValidEndInvalid = "2300-2500";
        String startInvalidEndValid = "1870-2300";

        // EP: Both start, end invalid
        assertFalse(isValidTimeRange(invalidTimeRange));

        // EP: Start valid, end invalid
        assertFalse(isValidTimeRange(startValidEndInvalid));

        // EP: Start invalid, end valid
        assertFalse(isValidTimeRange(startInvalidEndValid));
    }

    @Test
    public void isClashingWithOtherLesson_noClash() {
        Lesson l1 = new Lesson("Friday 1300-1400");
        Lesson l2 = new Lesson("Friday 1400-1500");
        Lesson l3 = new Lesson("Thursday 1300-1400");
        Lesson l4 = new Lesson("Thursday 1600-1700");

        // EP: Start time and end time of both lessons coincide
        assertFalse(Lesson.isClashingWithOtherLesson(l1, l2));
        assertFalse(Lesson.isClashingWithOtherLesson(l2, l1));

        // EP: Both lessons not on same day
        assertFalse(Lesson.isClashingWithOtherLesson(l1, l3));
        assertFalse(Lesson.isClashingWithOtherLesson(l4, l2));

        // EP: Lessons fall on same day, no overlap between lesson times
        assertFalse(Lesson.isClashingWithOtherLesson(l3, l4));
    }

    @Test
    public void isClashingWithOtherLesson_clashOccur() {
        Lesson l1 = new Lesson("Friday 1300-1400");
        Lesson l2 = new Lesson("Friday 1330-1430");

        Lesson l3 = new Lesson("Thursday 1200-1300");
        Lesson l4 = new Lesson("Thursday 1100-1400");

        Lesson l5 = new Lesson("Wednesday 1100-1200");
        Lesson l6 = new Lesson("Wednesday 1100-1200");

        // EP: Partial overlap between lesson timings
        assertTrue(Lesson.isClashingWithOtherLesson(l1, l2));
        assertTrue(Lesson.isClashingWithOtherLesson(l2, l1));

        // EP: Complete overlap between lessons
        assertTrue(Lesson.isClashingWithOtherLesson(l3, l4));

        // EP: Lessons have identical timings
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
        Lesson l4 = new Lesson("Saturday 1300-1400");
        Lesson l5 = new Lesson("Friday 1300-1430");

        Assertions.assertNotEquals(l1, new Object());
        assertEquals(l1, l1);
        assertEquals(l1, l2);
        assertEquals(l2, l3);
        assertFalse(l1.equals(l4));
        assertFalse(l1.equals(l5));
    }

    @Test
    public void lessonComparatorTest() {
        Lesson.LessonComparator lessonComparator = new Lesson.LessonComparator();
        Lesson l1 = new Lesson("Monday 1300-1400");
        Lesson l2 = new Lesson("Tuesday 1300-1400");
        Lesson l3 = new Lesson("Tuesday 1400-1500");
        Lesson l4 = new Lesson("Tuesday 1600-1800");

        // EP: Lessons fall on different days
        assertEquals(-1, lessonComparator.compare(l1, l2));

        // EP: Same day, start and end time coincide
        assertEquals(-1, lessonComparator.compare(l2, l3));

        // EP: Same day, timings do not overlap
        assertEquals(-1, lessonComparator.compare(l3, l4));
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
    public void durationTillLesson_currentlyOngoing() {
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
