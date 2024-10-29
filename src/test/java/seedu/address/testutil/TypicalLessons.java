package seedu.address.testutil;

import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson LESSON_1 = new LessonBuilder()
            .withDate("2024-11-01")
            .withTime("09:00")
            .withStudent(ALICE)
            .build();

    public static final Lesson LESSON_2 = new LessonBuilder()
            .withDate("2024-11-03")
            .withTime("10:00")
            .withStudent(BENSON)
            .build();

    public static final Lesson LESSON_3 = new LessonBuilder()
            .withDate("2024-11-05")
            .withTime("11:00")
            .withStudent(CARL)
            .build();

    public static final Lesson LESSON_4 = new LessonBuilder()
            .withDate("2024-11-07")
            .withTime("14:00")
            .withStudent(ALICE)
            .withStudent(BENSON)
            .build();

    public static final Lesson LESSON_5 = new LessonBuilder()
            .withDate("2024-11-09")
            .withTime("16:00")
            .build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns a list of typical lessons for testing.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(LESSON_1, LESSON_2, LESSON_3, LESSON_4, LESSON_5));
    }
}
