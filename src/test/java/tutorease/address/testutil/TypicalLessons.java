package tutorease.address.testutil;

import static tutorease.address.testutil.TypicalStudents.ALICE;
import static tutorease.address.testutil.TypicalStudents.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {
    public static final Lesson MATH_LESSON;
    public static final Lesson SCIENCE_LESSON;
    public static final Lesson ENGLISH_LESSON;
    public static final Lesson HISTORY_LESSON;
    public static final Lesson GEOGRAPHY_LESSON;
    public static final Lesson ART_LESSON;
    public static final Lesson MUSIC_LESSON;

    static {
        try {
            MATH_LESSON = new LessonBuilder()
                    .withName(ALICE)
                    .withStartDateTime("20-10-2024 10:00")
                    .withEndDateTime("20-10-2024 10:00")
                    .build();

            SCIENCE_LESSON = new LessonBuilder()
                    .withName(ALICE)
                    .withStartDateTime("20-10-2024 12:00")
                    .withEndDateTime("20-10-2024 13:00")
                    .build();

            ENGLISH_LESSON = new LessonBuilder()
                    .withName(BOB)
                    .withStartDateTime("21-10-2024 09:00")
                    .withEndDateTime("21-10-2024 09:00")
                    .build();

            HISTORY_LESSON = new LessonBuilder()
                    .withName(ALICE)
                    .withStartDateTime("21-10-2024 11:00")
                    .withEndDateTime("21-10-2024 12:00")
                    .build();

            GEOGRAPHY_LESSON = new LessonBuilder()
                    .withName(ALICE)
                    .withStartDateTime("15-10-2024 10:00")
                    .withEndDateTime("15-10-2024 11:00")
                    .build();

            ART_LESSON = new LessonBuilder()
                    .withName(BOB)
                    .withStartDateTime("22-10-2024 16:00")
                    .withEndDateTime("22-10-2024 17:00")
                    .build();

            MUSIC_LESSON = new LessonBuilder()
                    .withName(BOB)
                    .withStartDateTime("23-10-2024 15:00")
                    .withEndDateTime("23-10-2024 16:00")
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException("Failed to create typical lessons.", e);
        }
    }

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code LessonSchedule} with all the typical lessons.
     */
    public static LessonSchedule getTypicalLessons() {
        LessonSchedule ls = new LessonSchedule();
        for (Lesson lesson : getTypicalLessonsList()) {
            ls.addLesson(lesson);
        }
        return ls;
    }

    public static List<Lesson> getTypicalLessonsList() {
        return new ArrayList<>(Arrays.asList(
                MATH_LESSON,
                SCIENCE_LESSON,
                ENGLISH_LESSON,
                HISTORY_LESSON,
                GEOGRAPHY_LESSON,
                ART_LESSON,
                MUSIC_LESSON
        ));
    }
}
