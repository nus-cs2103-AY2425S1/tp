package seedu.edulog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edulog.storage.lesson.JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalLessons.SEC_2_MATH;

import org.junit.jupiter.api.Test;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.storage.lesson.JsonAdaptedLesson;

public class JsonAdaptedLessonTest {
    private static final String EMPTY_DESCRIPTION = "";
    private static final String INVALID_DESCRIPTION = "d".repeat(Lesson.MAX_CHARACTER_LIMIT + 1);
    private static final String INVALID_DAY_OF_WEEK = "qwrqwrjqwrpwqjorqwjpor";
    private static final String INVALID_TIME_BOUNDS_MINUTE = "2061";

    private static final String INVALID_TIME_BOUNDS_HOUR = "3900";

    private static final String INVALID_TIME_LENGTH = "20:61";

    private static final String VALID_DESCRIPTION = SEC_2_MATH.getDescription();

    private static final String VALID_DAY_OF_WEEK = SEC_2_MATH.getStartDay().toString();

    private static final String VALID_START_TIME = SEC_2_MATH.getFormattedStartTime();
    private static final String VALID_END_TIME = SEC_2_MATH.getFormattedEndTime();

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(SEC_2_MATH);
        assertEquals(SEC_2_MATH, lesson.toModelType());
    }

    @Test
    public void toModelType_invalidDescriptionEmpty_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(EMPTY_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Lesson.DESCRIPTION_EMPTY;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDescriptionTooLong_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(INVALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Lesson.DESCRIPTION_TOO_LONG;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_DAY_OF_WEEK, VALID_START_TIME, VALID_END_TIME);
        // TODO: Use "Description.class.getSimpleName()" once OOP-ized.
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDayOfWeek_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_DESCRIPTION, INVALID_DAY_OF_WEEK, VALID_START_TIME, VALID_END_TIME);
        String expectedMessage = Lesson.INVALID_DAY_OF_WEEK;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTimeHour_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, INVALID_TIME_BOUNDS_HOUR, VALID_END_TIME);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTimeMinute_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, INVALID_TIME_BOUNDS_MINUTE, VALID_END_TIME);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTimeLength_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, INVALID_TIME_LENGTH, VALID_END_TIME);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, null, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "start time");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTimeHour_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, INVALID_TIME_BOUNDS_HOUR);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTimeMinute_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, INVALID_TIME_BOUNDS_MINUTE);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTimeLength_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, INVALID_TIME_LENGTH);
        String expectedMessage = Lesson.NOT_24H_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "end time");
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_sameStartAndEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
            new JsonAdaptedLesson(VALID_DESCRIPTION, VALID_DAY_OF_WEEK, VALID_START_TIME, VALID_START_TIME);
        String expectedMessage = Lesson.NO_SAME_TIME;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }
}
