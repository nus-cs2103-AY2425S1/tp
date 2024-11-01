package seedu.edulog.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.testutil.TypicalLessons.SEC_2_MATH;
import static seedu.edulog.testutil.TypicalLessons.SEC_3_MATH;
import static seedu.edulog.testutil.TypicalLessons.SEC_4_MATH;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.testutil.LessonBuilder;

public class LessonTest {
    @Test
    public void isSameLesson() {
        // same object -> returns true
        assertTrue(SEC_4_MATH.isSameLesson(SEC_4_MATH));

        // null -> returns false
        assertFalse(SEC_4_MATH.isSameLesson(null));

        // same description, all other attributes different -> returns true
        Lesson editedLesson = new LessonBuilder(SEC_4_MATH)
            .withDescription(SEC_4_MATH.getDescription().toString())
            .withDayOfWeek(SEC_2_MATH.getStartDay().toString())
            .withStartTime(SEC_3_MATH.getFormattedStartTime())
            .withEndTime(SEC_3_MATH.getFormattedEndTime())
            .build();
        assertTrue(SEC_4_MATH.isSameLesson(editedLesson));

        // different description, all other attributes same -> returns false
        editedLesson = new LessonBuilder(SEC_4_MATH).withDescription(SEC_3_MATH.getDescription().toString()).build();
        assertFalse(SEC_4_MATH.isSameLesson(editedLesson));

        // Description differs in case, all other attributes same -> returns true
        editedLesson = new LessonBuilder(SEC_3_MATH)
            .withDescription(SEC_3_MATH.getDescription().toString().toUpperCase()).build();

        assertTrue(SEC_3_MATH.isSameLesson(editedLesson));

        // description has trailing spaces, all other attributes same -> returns false
        editedLesson = new LessonBuilder(SEC_2_MATH)
            .withDescription(SEC_2_MATH.getDescription().toString() + " ").build();
        assertFalse(SEC_2_MATH.isSameLesson(editedLesson));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Lesson lessonCopy = new LessonBuilder(SEC_4_MATH).build();
        assertTrue(SEC_4_MATH.equals(lessonCopy));

        // same object -> returns true
        assertTrue(SEC_4_MATH.equals(SEC_4_MATH));

        // null -> returns false
        assertFalse(SEC_4_MATH.equals(null));

        // different type -> returns false
        assertFalse(SEC_4_MATH.equals(5));

        // different Lesson -> returns false
        assertFalse(SEC_4_MATH.equals(SEC_3_MATH));

        // different Description -> returns false
        Lesson editedLesson = new LessonBuilder(SEC_4_MATH)
            .withDescription(SEC_3_MATH.getDescription().toString()).build();
        assertFalse(SEC_4_MATH.equals(editedLesson));

        // different start day -> returns false
        editedLesson = new LessonBuilder(SEC_4_MATH).withDescription(SEC_3_MATH.getStartDay().toString()).build();
        assertFalse(SEC_4_MATH.equals(editedLesson));

        // different start time -> returns false
        editedLesson = new LessonBuilder(SEC_4_MATH).withStartTime(SEC_2_MATH.getFormattedStartTime()).build();
        assertFalse(SEC_4_MATH.equals(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(SEC_4_MATH).withEndTime(SEC_3_MATH.getFormattedEndTime()).build();
        assertFalse(SEC_4_MATH.equals(editedLesson));

    }

    @Test
    public void toStringMethod() {
        // Tests for lessons which start and end at the same day
        String expected = Lesson.class.getCanonicalName()
            + "{description=" + SEC_4_MATH.getDescription()
            + ", From=" + SEC_4_MATH.getStartDay() + " " + SEC_4_MATH.getFormattedStartTime()
            + ", To=" + SEC_4_MATH.getStartDay() + " " + SEC_4_MATH.getFormattedEndTime() + "}";
        assertEquals(expected, SEC_4_MATH.toString());

        // Tests for lessons which start and end at different days
        expected = Lesson.class.getCanonicalName()
            + "{description=" + SEC_2_MATH.getDescription()
            + ", From=" + SEC_2_MATH.getStartDay() + " " + SEC_2_MATH.getFormattedStartTime()
            + ", To=" + SEC_2_MATH.getStartDay().plus(1) + " " + SEC_2_MATH.getFormattedEndTime() + "}";
        assertEquals(expected, SEC_2_MATH.toString());
    }
}
