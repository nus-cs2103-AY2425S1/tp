package keycontacts.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class LessonTest {
    // functionality of constructor and isValid is already tested in RegularLesson
    // this is to test default methods which RegularLesson overwrites

    @Test
    public void equals() {
        Lesson lesson1 = new LessonStub(new Time("10:00"), new Time("11:00"));
        Lesson lesson1Duplicate = new LessonStub(new Time("10:00"), new Time("11:00"));
        // different start time
        Lesson lesson2 = new LessonStub(new Time("09:00"), new Time("11:00"));
        // different end time
        Lesson lesson3 = new LessonStub(new Time("10:00"), new Time("12:00"));

        assertFalse(lesson1.equals(lesson2));
        assertFalse(lesson2.equals(lesson1));

        assertFalse(lesson1.equals(lesson3));
        assertFalse(lesson3.equals(lesson1));

        // same object
        assertTrue(lesson1.equals(lesson1));

        // same values
        assertTrue(lesson1Duplicate.equals(lesson1Duplicate));
        assertTrue(lesson1Duplicate.equals(lesson1));

        // null check
        assertFalse(lesson1.equals(null));

        // not a lesson
        assertFalse(lesson1.equals(""));
    }

    @Test
    public void toStringMethod() {
        Lesson lesson = new LessonStub(new Time("10:00"), new Time("11:00"));
        String expected = LessonStub.class.getCanonicalName() + "{startTime=" + lesson.getStartTime() + ", endTime="
                + lesson.getEndTime() + "}";
        assertEquals(expected, lesson.toString());
    }

    @Test
    public void hashCodeMethod() {
        Lesson lesson = new LessonStub(new Time("10:00"), new Time("11:00"));
        assertEquals(Objects.hash(lesson.getStartTime(), lesson.getEndTime()), lesson.hashCode());
    }


    private static class LessonStub extends Lesson {
        private LessonStub(Time startTime, Time endTime) {
            super(startTime, endTime);
        }

        @Override
        public boolean isClashing(RegularLesson other) {
            throw new AssertionError("This method should not be called!");
        }

        @Override
        public boolean isClashing(MakeupLesson other) {
            throw new AssertionError("This method should not be called!");
        }
    }
}
